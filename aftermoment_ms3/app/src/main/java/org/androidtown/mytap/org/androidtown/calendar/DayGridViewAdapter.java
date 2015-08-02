package org.androidtown.mytap.org.androidtown.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by sangsoo on 2015-07-06.
 */
public class DayGridViewAdapter extends BaseAdapter {

    DayItem dayItem;
    Context mContext;
    ArrayList<String> day = new ArrayList<String>();
    public DayGridViewAdapter(Context context)
    {
        mContext=context;
        day.add("SUN");
        day.add("MON");
        day.add("TUE");
        day.add("WED");
        day.add("THU");
        day.add("FRI");
        day.add("SAT");
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            dayItem = new DayItem(mContext);
        }else{
            dayItem=(DayItem)convertView;
        }
        dayItem.setText(day.get(position));
        return dayItem;
    }
}
