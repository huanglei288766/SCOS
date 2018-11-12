package es.source.code.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import es.source.code.model.User;
import es.source.code.utils.HttpUtilsHttpClient;

public class LoginOrRegister extends AppCompatActivity {

    public static Button login;
    public static Button returnBack;
    public static Button register;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        login = findViewById(R.id.login_btn);
        returnBack = findViewById(R.id.return_btn);
        register = findViewById(R.id.register_btn);


        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (!sharedPreferences.contains("userName")){
            login.setVisibility(View.INVISIBLE);
        }else{
            register.setVisibility(View.INVISIBLE);
            EditText editText4 = findViewById(R.id.editText4);
            editText4.setText(sharedPreferences.getString("userName", null));
        }

        login.setOnClickListener(new LoginOrRegisterOrReturnOnClickListener());
        returnBack.setOnClickListener(new LoginOrRegisterOrReturnOnClickListener());
        register.setOnClickListener(new LoginOrRegisterOrReturnOnClickListener());

    }


    public class LoginOrRegisterOrReturnOnClickListener implements View.OnClickListener {

        Intent resultIntent = new Intent();
        EditText editText4 = findViewById(R.id.editText4);
        EditText editText5 = findViewById(R.id.editText5);
        ProgressBar progressBar = findViewById(R.id.progressBar2);

        User user;

        @Override
        public void onClick(View v) {
            final String account = editText4.getText().toString();
            final String passWord = editText5.getText().toString();
            switch (v.getId()){
                case R.id.return_btn:
                    resultIntent.putExtra("msg", "Return");
                    setResult(13, resultIntent);

                    if (!sharedPreferences.contains("userName")){
                        editor.putInt("loginState", 0);
                        editor.commit();
                    }

                    finish();
                    break;
                case R.id.register_btn :
                    if (!test(account, passWord)) break;
                    user = new User();
                    user.setUserName(account);
                    user.setPassword(passWord);
                    user.setOldUser(false);

                    editor.putString("userName", account);
                    editor.putInt("loginState", 1);
                    editor.commit();

                    resultIntent.putExtra("msg", "RegisterSuccess");
                    resultIntent.putExtra("loginUser", user);

                    setResult(11,resultIntent);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = HttpUtilsHttpClient.BASE_URL + "/servlet/LoginServlet";
                            Map<String, String> params = new HashMap<String, String>();
                            String name = account;
                            String password = passWord;
                            params.put("name", name);
                            params.put("password", password);
                            //请求，返回json
                            String result = HttpUtilsHttpClient.postRequest(url, params);
                            Message msg = new Message();
                            msg.what = 0x11;
                            Bundle data = new Bundle();
                            data.putString("result", result);
                            msg.setData(data);


                            hander.sendMessage(msg);
                        }

                        @SuppressLint("HandlerLeak")
                        Handler hander = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.what == 0x11) {
                                    Bundle data = msg.getData();
                                    String key = data.getString("result");//得到json返回的json数据
//                                   Toast.makeText(MainActivity.this,key,Toast.LENGTH_LONG).show();
                                    try {
                                        JSONObject json = new JSONObject(key);
                                        String result = (String) json.get("result");
                                        if ("success".equals(result)) {
                                            Toast.makeText(LoginOrRegister.this, "登录成功", Toast.LENGTH_LONG).show();
                                        } else if ("error".equals(result)) {
                                            Toast.makeText(LoginOrRegister.this, "登录失败", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                    }).start();

                    finish();
                    break;
                case R.id.login_btn:
                    if (!test(account, passWord)) break;
                    user = new User();
                    user.setUserName(account);
                    user.setPassword(passWord);
                    user.setOldUser(true);

                    editor.putString("userName", account);
                    editor.putInt("loginState", 1);
                    editor.commit();

                    resultIntent.putExtra("msg", "LoginSuccess");
                    resultIntent.putExtra("loginUser", user);
                    setResult(12, resultIntent);

                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },2000);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("HttpServer", "threadStart");
                            String url = HttpUtilsHttpClient.BASE_URL + "/LoginValidator";
                            Map<String, String> params = new HashMap<String, String>();
                            String name = "yyc";
                            String password = "123456";
                            params.put("name", name);
                            params.put("password", password);
                            //请求，返回json
                            String result = HttpUtilsHttpClient.postRequest(url, params);
                            Message msg = new Message();
                            msg.what = 0x11;
                            Bundle data = new Bundle();
                            data.putString("result", result);
                            msg.setData(data);


                            hander.sendMessage(msg);
                        }

                        @SuppressLint("HandlerLeak")
                        Handler hander = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.what == 0x11) {
                                    Log.d("httpServer", "GetResponse");
                                    Bundle data = msg.getData();
                                    String key = data.getString("result");//得到json返回的json数据
                                    Log.d("httpServer", "getKey");
                                    try {
                                        JSONObject json = new JSONObject(key);
                                        Log.d("httpServer", "newJson");
                                        String result = (String) json.get("result");
                                        Log.d("httpServer", "getResult");
                                        if ("success".equals(result)) {
                                            Log.d("login", "loginSuccess");
                                            Toast.makeText(LoginOrRegister.this, "登录成功", Toast.LENGTH_LONG).show();
                                        } else if ("error".equals(result)) {
                                            Log.d("login", "loginFailed");
                                            Toast.makeText(LoginOrRegister.this, "登录失败", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                    }).start();

                    break;
            }
        }

        public boolean isLegal(String string){
            String regex = "^[a-zA-Z0-9]+$";
            if (string.matches(regex)){
                return true;
            }
            return false;
        }

        public boolean test(String account, String passWord){
            boolean accountIsLegal = isLegal(account);
            boolean passwordIsLegal = isLegal(passWord);
            boolean legal = accountIsLegal && passwordIsLegal;
            if(!accountIsLegal){
                editText4.setHint("输入内容不符合规则");
            }
            if(!passwordIsLegal){
                editText5.setHint("输入内容不符合规则");
            }
            if (!legal) return false;
            return true;
        }
    }



}
