package org.androidtown.mytap.org.androidtown.login;

/**
 * Created by MSPark on 2015-07-31.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidtown.mytap.R;



import android.app.Activity;
import android.app.Fragment;
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
public class Login_View extends Activity {

    private Button login;
    private Button back;

    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.login_view);

        login = (Button) findViewById(R.id.done_Button);
        back = (Button) findViewById(R.id.back_button);

        final EditText id_edit = (EditText) findViewById(R.id.email);
        final EditText password_edit = (EditText) findViewById(R.id.password);


        login.setOnClickListener(new View.OnClickListener()

        {       @Override
                public void onClick(View v)

            {
                String id = id_edit.getText().toString();
                String password = password_edit.getText().toString();
                if (id.equals("user") && password.equals("users"))

                 {  Intent i = new Intent(Login_View.this, MainActivity.class);
                     startActivity(i);


                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "정보가 맞지 않습니다", Toast.LENGTH_SHORT);

                }


            }

        });

        back.setOnClickListener(new View.OnClickListener()

        {@Override

         public void onClick(View v)

            { finish();


            };





                            /*    doneButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        String comment = null; */

    });



 }
}





