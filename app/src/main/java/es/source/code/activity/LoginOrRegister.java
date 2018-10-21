package es.source.code.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import es.source.code.model.User;

public class LoginOrRegister extends AppCompatActivity {

    public static Button login;
    public static Button returnBack;
    public static Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        login = findViewById(R.id.login_btn);
        returnBack = findViewById(R.id.return_btn);
        register = findViewById(R.id.register_btn);

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
            String account = editText4.getText().toString();
            String passWord = editText5.getText().toString();
            switch (v.getId()){
                case R.id.return_btn:
                    resultIntent.putExtra("msg", "Return");
                    setResult(13, resultIntent);
                    finish();
                    break;
                case R.id.register_btn :
                    if (!test(account, passWord)) break;
                    user = new User();
                    user.setUserName(account);
                    user.setPassword(passWord);
                    user.setOldUser(false);

                    resultIntent.putExtra("msg", "RegisterSuccess");
                    resultIntent.putExtra("loginUser", user);

                    setResult(11,resultIntent);
                    finish();
                    break;
                case R.id.login_btn:
                    if (!test(account, passWord)) break;
                    user = new User();
                    user.setUserName(account);
                    user.setPassword(passWord);
                    user.setOldUser(true);

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
