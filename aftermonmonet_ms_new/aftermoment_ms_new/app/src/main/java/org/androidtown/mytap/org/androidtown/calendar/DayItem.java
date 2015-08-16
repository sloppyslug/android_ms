package org.androidtown.mytap.org.androidtown.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidtown.mytap.R;

/**
 * Created by sangsoo on 2015-07-06.
 */
public class DayItem extends RelativeLayout {



    TextView day;
    public DayItem(Context context) {
        super(context);
        init(context);
    }

    public DayItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void init(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.day_view, this, true);
        day = (TextView)findViewById(R.id.day);
    }
    public void setText(String today)
    {
        if("SUN".equals(today) || "SAT".equals(today))
        {
            day.setTextColor(Color.parseColor("#85aa1d"));
        }else{
            day.setTextColor(Color.parseColor("#c9c900"));

        }
        day.setText(today);
    }
}
