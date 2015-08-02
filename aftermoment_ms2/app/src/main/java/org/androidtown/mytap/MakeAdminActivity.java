package org.androidtown.mytap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by constant on 15. 8. 1..
 */

public class MakeAdminActivity extends Activity {


    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);

        backButton = (Button)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(MakeAdminActivity.this, FirstActivity.class);
                startActivity(intent);

                finish();
            }

             });
        }

         @Override
            public void onBackPressed(){
            Intent intent = new Intent(MakeAdminActivity.this, FirstActivity.class);
            startActivity(intent);

            finish();
            super.onBackPressed();
    }
}
