package es.source.code.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.LoginOrRegister;
import es.source.code.activity.R;
import es.source.code.model.FoodInfo;
import es.source.code.utils.HttpUtilsHttpClient;

public class UpdateService extends IntentService {
    private List<FoodInfo> foodlist=new ArrayList<>();

    public UpdateService() {
        super("updateService");
        foodlist.add(new FoodInfo(0,3,"冷菜3", 40,15));
        foodlist.add(new FoodInfo(1,3,"热菜3", 40,15));
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        sendNotification();


                Log.d("HttpServer", "threadStart");
                String url = HttpUtilsHttpClient.BASE_URL + "/FoodUpdateService";

                //请求，返回json
                String result = HttpUtilsHttpClient.getRequest(url);
                FoodInfo foodInfo = new FoodInfo();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Log.d("httpServer", jsonObject.toString());
                    foodInfo.setStore((int)jsonObject.get("store"));
                    Log.d("httpServer", "num" + foodInfo.getStore());
                } catch (JSONException e) {
                    Log.d("httpServer", "json - food - error");
                }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("tag","oncreate");
        sendNotification();
    }

    private void sendNotification(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//获取一个NotificationManager管理对象
        Notification.Builder builder = new Notification.Builder(this);//获取一个具体的状态栏通知对象Notification，设置图片，文字、提示声音、震动参数等
        builder.setSmallIcon(R.mipmap.ic_launcher); // 这里使用的系统默认图标，可自行更换
        builder.setTicker("新品上架！！");
        //builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.liangcai));
        builder.setContentTitle(foodlist.get(0).getFoodName());
        builder.setContentText(foodlist.get(0).getPrice()+""+"冷菜");
        builder.setAutoCancel(true);
        Intent intent = new Intent(this, FoodDetailed.class);
        intent.putExtra("list",(Serializable)foodlist);
        intent.putExtra("pos","0");
        PendingIntent pendingIntents = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntents);

        // 启动Notification，getNotification()方法已经过时了，不推荐使用，使用build()方法替代
        notificationManager.notify(1, builder.build());

    }
}
