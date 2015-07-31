package org.androidtown.mytap.org.androidtown.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.androidtown.mytap.R;

/**
 * Created by MSPark on 2015-07-31.
 */
public class First_View extends Activity


{
    protected Button login_pg;
    protected Button make_admin;
    Intent intent;

    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.first_view);


        login_pg = (Button) findViewById(R.id.login_pg);
        make_admin = (Button) findViewById(R.id.make_admin);


        login_pg.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)

            {

                switch (v.getId()) { // 스위치문으로 버튼을 누른 아이디 값을 받아 그 아이디 값에 따른 결과를 처리
                    case R.id.login_pg:
                        intent = new Intent(org.androidtown.mytap.org.androidtown.login.First_View.this, org.androidtown.mytap.org.androidtown.login.Login_View.class); // 로그인 페이지 이동
                        break; //

                    case R.id. make_admin:
                        intent = new Intent(org.androidtown.mytap.org.androidtown.login.First_View.this,org.androidtown.mytap.org.androidtown.login.Make_admin.class);
                        break;

                }
                startActivity(intent);// 그리고 여기서 실행


            }


          });
    }
}
