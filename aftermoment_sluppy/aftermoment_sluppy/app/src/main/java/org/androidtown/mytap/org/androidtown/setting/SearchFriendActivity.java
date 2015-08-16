package org.androidtown.mytap.org.androidtown.setting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.R;

/**
 * Created by constant on 15. 8. 13..
 */
public class SearchFriendActivity extends Activity {

    Button backButton;
    ImageButton searchButton;
    EditText searchText;
    TextView searchLabel;
    ListView friendListView;
    FriendListAdapter rAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchfriend_setting);
        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchText = (EditText)findViewById(R.id.searchText);

        searchButton = (ImageButton)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.TAG, "(화면 검색 ) 검색 이름 : " + searchText.getText().toString());
                if (searchText.getText().toString().equals("")) {
                    rAdapter.connectCheck("DFbQ8SKCyC5DT6qCXFNqStGu6ven3xV7ys6ag7CCaQ4JbusXXdfafN64J5Bm29vcL2FWWyBF62AyRgwTGwxgPmmv5AcfELwMtzm54ke542rWXz7Jcs6FFedc6uPeUVuk");
                    Log.d(MainActivity.TAG, "(화면 검색) 검색 ㄴㄴ");
                }else{
                    rAdapter.connectCheck(searchText.getText().toString());
                }
            }
        });
        searchLabel = (TextView)findViewById(R.id.searchLabel);



        friendListView = (ListView)findViewById(R.id.friendListView);
        rAdapter = new FriendListAdapter(SearchFriendActivity.this);
        friendListView.setAdapter(rAdapter);
        rAdapter.setOnLabel(new FriendListAdapter.OnLabel() {
            @Override
            public void setOnLabel() {
                searchLabel.setVisibility(View.VISIBLE);
            }
        });
    }
}
