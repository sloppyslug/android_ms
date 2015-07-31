package org.androidtown.mytap.org.androidtown.friend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidtown.mytap.R;

/**
 * Created by constant on 15. 7. 25..
 */
public class Friend_item extends RelativeLayout {

    Context mContext;

    TextView friendName;
    ImageView imageView;

    public Friend_item(Context context) {
        super(context);
        mContext=context;
        init(context);
    }

    public Friend_item(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init(context);
    }
    public void init(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gridview_item, this, true);
        friendName = (TextView)findViewById(R.id.friendName);
        imageView = (ImageView)findViewById(R.id.imageView);
    }
    public void setInfo(String name, int imagePosition, String comment)
    {
        friendName.setText(name);
        imageView.setImageResource(R.drawable.sohappy);
    }
}
