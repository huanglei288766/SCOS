package es.source.code.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.model.User;

public class MainScreen extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private SimpleAdapter adapter;

    private Intent intent;
    private User user;
    private int flagLogin;
    private List<Map<String, Object>>dataList;

    private int[] icon = {R.drawable.order, R.drawable.vieworder, R.drawable.account, R.drawable.help};
    private String[] iconName = {"点菜", "查看订单", "登录/注册", "系统帮助"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        user = new User();
        flagLogin = 2;

        /** 判断入口 **/
        intent = getIntent();
        String msg = intent.getStringExtra("msg");
        if(msg.equals("FromEntry")){
            flagLogin = 2;
            user = null;
        }

        initGridView();

    }

    public void initGridView(){
        gridView = findViewById(R.id.gridView);
        dataList = new ArrayList<>();
        adapter = new SimpleAdapter(this, getData(), R.layout.item, new String[] {"image", "text"}, new int[] {R.id.image, R.id.text});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    /**
     * item相关
     * @return
     */
    private List<Map<String, Object>> getData(){
        for (int i = flagLogin; i < icon.length; i++){
            Map<String, Object>map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }

    /**
     * item点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 2 - flagLogin){
            Intent intentLoginOrRegister = new Intent();
            intentLoginOrRegister.setClass(MainScreen.this, LoginOrRegister.class);
            MainScreen.this.startActivityForResult(intentLoginOrRegister,1);
        }else if(position == 0 - flagLogin){
            Intent order = new Intent();
            order.setClass(MainScreen.this,FoodView.class);
            MainScreen.this.startActivity(order);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                switch (resultCode){
                    case 11:
                        this.user = (User) getIntent().getSerializableExtra("LoginUser");
                        flagLogin = 0;
                        Toast.makeText(this, "欢迎您成为SCOS新用户", Toast.LENGTH_SHORT).show();
                        break;
                    case 12:
                        this.user = (User) getIntent().getSerializableExtra("LoginUser");
                        flagLogin = 0;
                        break;
                    case 13:
                        if (user == null){
                            flagLogin = 2;
                        }
                }
                if (flagLogin == 0){
                    initGridView();
                }
        }
    }
}
