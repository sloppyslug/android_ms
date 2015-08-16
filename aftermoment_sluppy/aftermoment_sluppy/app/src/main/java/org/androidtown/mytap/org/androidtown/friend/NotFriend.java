package org.androidtown.mytap.org.androidtown.friend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import org.androidtown.mytap.R;

/**
 * Created by constant on 15. 8. 16..
 */
public class NotFriend extends RelativeLayout {

    public NotFriend(Context context) {
        super(context);
        init(context);

    }

    public NotFriend(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public void init(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gridview_item, this, true);
    }
}
