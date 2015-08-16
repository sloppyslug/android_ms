package org.androidtown.mytap.org.androidtown.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.R;

import java.util.ArrayList;

/**
 * Created by constant on 15. 8. 13..
 */
public class List_item2 extends RelativeLayout {

    Context mContext;

    TextView nameText;
    Button admitButton;
    Button rejectButton;

    ArrayList<Integer> idList = new ArrayList<Integer>();


    public List_item2(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public List_item2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    public void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.friendname_setting2, this, true);
        nameText = (TextView) findViewById(R.id.nameText);
        admitButton = (Button) findViewById(R.id.admitButton);
        rejectButton = (Button) findViewById(R.id.rejectButton);


    }

    public void setInfo(String name) {
        nameText.setText(name);
    }

    public void setFriendId(int id2) {
        idList.add(id2);
        Log.d(MainActivity.TAG, "(화면 4) 안녕 " + idList.get(0));
    }
}

