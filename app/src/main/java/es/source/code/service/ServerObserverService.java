package es.source.code.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import es.source.code.model.FoodInfo;

public class ServerObserverService extends Service {

    private  cMessageHandler handler;
    public Messenger cMessenger;
    public Messenger sMessenger;//创建一个远程的信使对象
    private static boolean isrunning;
    Context mContext;
    private List<FoodInfo> foodlist;
    public FoodInfo food;
    public static boolean isIsrunning() {
        return isrunning;
    }

    public static void setIsrunning(boolean isrunning) {
        ServerObserverService.isrunning = isrunning;
    }

    public ServerObserverService() {
        handler=new  cMessageHandler();
        sMessenger=new Messenger(handler);
        foodlist=new ArrayList<>();
        food=new FoodInfo(0,0,"冷菜1",10, 10);
        foodlist.add(food);
        foodlist.add(new FoodInfo(0,1,"冷菜2",20,10));
        foodlist.add(new FoodInfo(1,0,"热菜1",10,10));
        foodlist.add(new FoodInfo(1,1,"热菜2",20,10));
    }
    private class  cMessageHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("ServerService", "收到Message");
            if(msg.what==1){
                Log.d("ServerService", "向客户端发送消息");
                setIsrunning(true);
                if(msg.replyTo!=null){
                    cMessenger=msg.replyTo;//得到客户端的信使对象，并向它发送消息
                    notityActivity();
                }
            }else if(msg.what==0){
                setIsrunning(false);
            }

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return sMessenger.getBinder();
    }//远程方法返回一个bindler

    public void notityActivity(){
        isrunning=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isrunning){
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        for (FoodInfo food:foodlist) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("food", food);
                            Message message = Message.obtain(null,10);
                            message.setData(bundle);
                            cMessenger.send(message);
                        }
                        Log.i("tag","传送数据成功");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void onDestroy() {
        Log.i("tag","onDestroy");
        super.onDestroy();
        isrunning=false;
    }
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Log.i("tag","onCreate");
    }
}
