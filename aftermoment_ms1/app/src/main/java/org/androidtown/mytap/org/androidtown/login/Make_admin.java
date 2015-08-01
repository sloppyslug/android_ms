package org.androidtown.mytap.org.androidtown.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.androidtown.mytap.R;

/**
 * Created by MSPark on 2015-07-31.
 */
public class Make_admin extends Activity {

    public Button back_button_1;


    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.make_admin);

        back_button_1 = (Button) findViewById(R.id.back_button);




        back_button_1.setOnClickListener(new View.OnClickListener()

        {
            @Override

            public void onClick(View v)
            {
                finish();
            }


        });

    }
}
