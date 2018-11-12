package es.source.code.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;

import es.source.code.utils.EmailSender;

public class SCOSHelper extends AppCompatActivity  implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> dataList;
    private String[] iconName = {"用户协议", "关于系统", "电话帮助", "短信帮助", "邮件帮助"};


     @SuppressLint("HandlerLeak")
     private Handler mhandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoshelper);

        mhandler = new  Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        Toast.makeText(SCOSHelper.this,"求助邮件已发送成功",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(SCOSHelper.this, "发送出现问题", Toast.LENGTH_SHORT).show();
                }
            }
        };
        initGridView();
    }

    public void initGridView(){
        gridView = findViewById(R.id.gridViewHelper);
        adapter = new SimpleAdapter(this, getData(), R.layout.item, new String[] {"image", "text"}, new int[] {R.id.image, R.id.text});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private ArrayList<Map<String, Object>> getData(){
        dataList = new ArrayList<>();
        for (int i = 0; i < iconName.length; i++){
            Map<String, Object>map = new HashMap<>();
            map.put("image", null);
            map.put("text", iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 2:
                if(ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CALL_PHONE") != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(SCOSHelper.this,new String[]{"android.permission.CALL_PHONE"},200);
                }else{
                    Intent call = new Intent();
                    Uri uri = Uri.parse("tel:" + 5554);
                    call.setAction(Intent.ACTION_CALL);
                    call.setData(uri);
                    startActivity(call);
                }
                break;
            case 3:
                if(ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.SEND_SMS") != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(SCOSHelper.this,new String[]{"android.permission.SEND_SMS"},200);
                }else{
                    SmsManager smsManager;
                   // Log.i("tag","00087");
                    smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("5554",null,"hello",null,null);
                }
                break;
            case 4:

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            EmailSender sender = new EmailSender();
                            //设置服务器地址和端口，可以查询网络
                            sender.setProperties("smtp.qq.com", "465");
                            //分别设置发件人，邮件标题和文本内容
                            sender.setMessage("843739798@qq.com", "title", "content");
                            //设置收件人
                            sender.setReceiver(new String[]{"hithuanglei@163.com"});
                            //添加附件换成你手机里正确的路径
                            // sender.addAttachment("/sdcard/emil/emil.txt");
                            //发送邮件
                            //sender.setMessage("你的163邮箱账号", "EmailS//ender", "Java Mail ！");这里面两个邮箱账号要一致
                            sender.sendEmail("smtp.qq.com", "843739798@qq.com", "vvpzjamgdqmybeeg");
                        } catch (MessagingException e) {
                            mhandler.sendEmptyMessage(1);
                        }
                        mhandler.sendEmptyMessage(0);
                    }
                }).start();

                break;

        }

    }


}
