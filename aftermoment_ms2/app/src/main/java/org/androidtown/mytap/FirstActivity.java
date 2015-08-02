package org.androidtown.mytap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by constant on 15. 8. 1..
 */
public class FirstActivity extends Activity implements View.OnClickListener{
    Button loginButton;
    Button signinButton;
    private BackPressButton backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fist_activity);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        signinButton = (Button)findViewById(R.id.signinButton);
        signinButton.setOnClickListener(this);
        backPressCloseHandler = new BackPressButton(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.loginButton:
                Log.d(MainActivity.TAG, "LOGINBUTTON CLICKED");
                Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.signinButton:
                Log.d(MainActivity.TAG, "SIGNINBUTTON CLICKED");
                Intent intent2 = new Intent(FirstActivity.this, MakeAdminActivity.class);
                startActivity(intent2);
                finish();
                break;

        }
    }
                @Override /*이 메소도는 이 화면에서 뒤로가기 버튼 즉 back버튼이 아니라 휴대폰의 뒤로가기 버튼을 눌렀을때 바로 종료 되는 것을 막는 메소드야*/
             public void onBackPressed() {
             backPressCloseHandler.onBackPressed();
             }
    
}
