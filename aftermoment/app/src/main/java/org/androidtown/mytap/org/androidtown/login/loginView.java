package org.androidtown.mytap.org.androidtown.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.R;

/**
 * Created by Çý¸° on 2015-07-25.
 */
public class loginView extends Activity

{
    protected void onCreat(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.login_view);

        final EditText id_edit = (EditText) findViewById(R.id.id);
        final EditText password_edi = (EditText) findViewById(R.id.password);


        Button button = (Button) findViewById(R.id.login);
        Button setOnClickListener ; new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {
                String id = id_edit.getText().toString();
                String password = password_edi.getText().toString();


                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("pass", password);

                setResult(RESULT_OK, intent);



                finish();


                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);

            }
        };
    }



    }






