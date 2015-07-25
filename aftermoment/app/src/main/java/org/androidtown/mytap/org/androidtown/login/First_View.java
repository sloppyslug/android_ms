package org.androidtown.mytap.org.androidtown.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.R;

/**
 * Created by MSPark on 2015-07-20.
 */


public class First_View extends Activity {

    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstview);



            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                 public void run() {
                 Intent intent = new Intent(First_View.this, loginView.class);
                 startActivity(intent);
                     // 뒤로가기 했을경우 안나오도록 하기//
                finish();
                 }
            }, 1500);



      }


    }