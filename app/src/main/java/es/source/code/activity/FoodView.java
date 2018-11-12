package es.source.code.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.source.code.adapter.ViewPagerAdpter;
import es.source.code.fragment.MyFragment;
import es.source.code.model.FoodInfo;
import es.source.code.service.ServerObserverService;
import es.source.code.service.UpdateService;

public class FoodView extends AppCompatActivity {

    static String[] titles = {"冷菜","热菜","海鲜","酒水"};
    ArrayList<String> datas;
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<MyFragment> fragments;
    ViewPagerAdpter viewPagerAdpter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        viewPager = findViewById(R.id.FoodViewPager);
        tabLayout = findViewById(R.id.FoodTabLayout);

        initFragments();
        initViewPager();
    }

    /**
     * ActionBar定义
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    Messenger cMessenger;
    Messenger sMessenger;
    SMessageHandler sMessageHandler=new SMessageHandler();
    private FoodInfo food;
    private List<FoodInfo> foodlist=new ArrayList<>();
    private Handler handler;
    private Handler newhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==30) {
                // Intent intent=new Intent(FoodView.this, UpdateService.class);
                //startService(intent);
                sendNotification();
            }
        }
    };
    private List<FoodInfo> refreshlist=new ArrayList<>();

    /**
     * ActionBar 点击事件
     * @param item 包括已点菜品
     * @return      未知
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int what;
        switch (item.getItemId()) {
            case R.id.ordered:
                try {
                    intent = new Intent();
                    intent.setClass(FoodView.this, FoodOrderView.class);
                    FoodView.this.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(FoodView.this, e.getMessage(), Toast.LENGTH_LONG);
                }
                return true;
            case R.id.updateInformation:
                String title=item.getTitle().toString();
                if(title.equals("实时更新")){
                    title="停止更新";
                    item.setTitle(title);
                    what=1;
                }else{
                    title="实时更新";
                    item.setTitle(title);
                    what=0;
                }
                Message message=Message.obtain();
                message.what=what;
                message.replyTo=cMessenger;//将自己的信使设置到消息中,这样服务端接收到消息的同时也得到了客户端的信使对象了
                try {
                    sMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            case R.id.updateServiceTest:
                Intent service = new Intent(FoodView.this, UpdateService.class);
                startService(service);
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /**
     * FoodList TextView datas 初始化
     * @param foodType          0-3
     * @param foodTypeLentth    每种菜的长度
     * @return
     */
    private static ArrayList<String> foodDataInit(int foodType, int foodTypeLentth){
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < foodTypeLentth; i++){
            datas.add(titles[foodType]+i+"\t"+"价格："+10*(i+1));
        }
        return datas;
    }

    private void  initFragments(){
        fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            datas = foodDataInit(i,3);
            fragments.add(new MyFragment(titles[i], datas));
        }
    }

    private void initViewPager(){
        viewPagerAdpter = new ViewPagerAdpter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdpter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class SMessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==10){
                Bundle bundle=msg.getData();
                Log.i("接收菜品","客户端接收到数据");
                food =(FoodInfo) bundle.getSerializable("food");
//                for(int i=0;i<foodlist.size();i++) {
                Log.i("接收菜品",  food.getFoodName());
                foodlist.add(food);
                if(foodlist.size() > 0) {
                    refreshStore(foodlist);
                    Message m=new Message();
                    m.what=30;
                    newhandler.sendMessage(m);
                }
//                }
                //Toast.makeText(FoodView.this,"菜品"+food.getFoodName(),Toast.LENGTH_SHORT);
            }
        }
    }

//    public void  refresh(){
//        Message message=new Message();
//        Bundle bundle=new Bundle();
//        bundle.putString("info","update");
//        message.setData(bundle);
//        message.what=30;
//        handler.sendMessage(message);
//    }


    public void refreshStore(List<FoodInfo> foodlist){
        for (int i = 0; i < 4; i ++){
            fragments.get(i).refreshStore(foodlist);
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void sendNotification(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//获取一个NotificationManager管理对象
        Notification.Builder builder = new Notification.Builder(this);//获取一个具体的状态栏通知对象Notification，设置图片，文字、提示声音、震动参数等
        builder.setSmallIcon(R.mipmap.ic_launcher); // 这里使用的系统默认图标，可自行更换
        builder.setTicker("新品上架！！");
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.liangcai));
        builder.setContentTitle(foodlist.get(0).getFoodName());
        builder.setContentText(foodlist.get(0).getPrice()+""+"冷菜");
        builder.setAutoCancel(true);

        // 点击后要执行的操作，打开MainActivity
        for(int i=0;i<4;i++){
            refreshlist.add(foodlist.get(i));
        }
        Intent intent = new Intent(this, FoodDetailed.class);
        intent.putExtra("list",(Serializable) refreshlist);
        intent.putExtra("pos","0");
        PendingIntent pendingIntents = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntents);

        // 启动Notification，getNotification()方法已经过时了，不推荐使用，使用build()方法替代
        notificationManager.notify(1, builder.build());

    }

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("tag","连接建立");
            sMessenger=new Messenger(service);
        }
        /*
        客户端使用远程返回的binder得到一个信使（即得到远程信使）
        虽然这里new了一个Messenger,但实际上还是远程创建的那一个
        */

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("tag","连接断开");
        }
    };

    public void initBinder(){
        cMessenger=new Messenger(sMessageHandler);
        Intent service = new Intent(getApplicationContext(), ServerObserverService.class);
        bindService(service ,connection,Context.BIND_AUTO_CREATE);//客户端请求远程连接
    }

    @Override
    protected void onStart() {
        super.onStart();
        initBinder();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }
}
