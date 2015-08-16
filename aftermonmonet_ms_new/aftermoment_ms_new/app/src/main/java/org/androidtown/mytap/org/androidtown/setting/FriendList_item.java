package org.androidtown.mytap.org.androidtown.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.MySharedPreference;
import org.androidtown.mytap.R;

import java.util.ArrayList;

/**
 * Created by constant on 15. 8. 13..
 */
public class FriendList_item extends RelativeLayout {

    Context mContext;

    TextView nameText;
    Button deleteButton;

    MySharedPreference mySharedPreference;
    String mySession;

    ArrayList<Integer> idList = new ArrayList<Integer>();


    public FriendList_item(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public FriendList_item(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    public void init(Context context) {

        mySharedPreference = new MySharedPreference(mContext);
        mySession = mySharedPreference.getPreferences();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.friendname_setting, this, true);
        nameText = (TextView) findViewById(R.id.nameText);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //new FriendAdapter(mContext).connectCheck2(idList.get(position));
            }
        });

    }

    public void setInfo(String name) {
        nameText.setText(name);
    }

    public void setFriendId(int id2) {
        idList.add(id2);
        Log.d(MainActivity.TAG, "(화면 4) 안녕 " + idList.get(0));
    }
}

