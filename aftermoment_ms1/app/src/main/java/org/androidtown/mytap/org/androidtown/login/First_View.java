package org.androidtown.mytap.org.androidtown.login;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.R;

/**
 * Created by MSPark on 2015-07-31.
 */
public class First_View extends Activity


{
    public Button login_pg;
    public Button make_admin;

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
                Intent intent = new Intent(First_View.this,Login_View.class);
                startActivity(intent);

                }


            });

        make_admin.setOnClickListener(new View.OnClickListener()

        {@Override

        public void onClick(View v)

            { Intent intent = new Intent(First_View.this, Make_admin.class);
                startActivity(intent);


             };



         });



    };

}
