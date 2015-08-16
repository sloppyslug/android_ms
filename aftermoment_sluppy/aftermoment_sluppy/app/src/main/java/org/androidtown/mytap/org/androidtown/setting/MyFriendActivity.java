package org.androidtown.mytap.org.androidtown.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.androidtown.mytap.R;

/**
 * Created by constant on 15. 8. 12..
 */
public class MyFriendActivity extends Activity {
    Button backButton;
    Button searchFriendButton;

    ListView listView;
    FriendAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist_setting);
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
        searchFriendButton = (Button) findViewById(R.id.searchFriendButton);
        searchFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyFriendActivity.this, SearchFriendActivity.class);
                startActivity(intent);
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        adapter = new FriendAdapter(MyFriendActivity.this);
        listView.setAdapter(adapter);

    }
}
