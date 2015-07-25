package org.androidtown.mytap.org.androidtown.calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidtown.mytap.R;

import java.util.ArrayList;

/**
 * Created by sangsoo on 2015-07-06.
 */
public class DayGridViewAdapter extends BaseAdapter {

    DayItem dayItem;
    Context mContext;
    ArrayList<Bitmap> day = new ArrayList<Bitmap>();
    public DayGridViewAdapter(Context context)
    {
        mContext=context;
        day.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sunday));
        day.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.monday));
        day.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.tuesday));
        day.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.wednesday));
        day.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.thursday));
        day.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.friday));
        day.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.saturday));
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
        dayItem.setImage(day.get(position));
        return dayItem;
    }
}
