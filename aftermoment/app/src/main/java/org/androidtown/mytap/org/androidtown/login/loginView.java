package org.androidtown.mytap.org.androidtown.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.R;

import java.util.ArrayList;

/**
 * Created by minsu on 2015-07-25.
 *
 */
        public class loginView extends Activity {

        protected void onCreat(Bundle saveInstanceState) {

                super.onCreate(saveInstanceState);
                setContentView(R.layout.login_view);

                final EditText id_edit = (EditText) findViewById(R.id.id);
                final EditText password_edit = (EditText) findViewById(R.id.password);

                Button login_in = (Button) findViewById(R.id.login);
                Button setOnClickListener;
                View.OnClickListener onClickListener = new View.OnClickListener() {

                        @Override
                        public void onClick(View v)

                        {
                                String id = id_edit.getText().toString();
                                String password = password_edit.getText().toString();
                                if (id.equals("user") && password.equals("users"))

                                {
                                        Intent main = new Intent(loginView.this, MainActivity.class);
                                        startActivities(main);
                                }

                                else {
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                        "정보가 맞지 않습니다", Toast.LENGTH_SHORT);

                                }


                        }

                        private void startActivities(Intent main) {

                        }








                        }

                        ;
                };


        }

