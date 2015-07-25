package org.androidtown.mytap.org.androidtown.calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.androidtown.mytap.R;

/**
 * Created by sangsoo on 2015-07-06.
 */
public class DayItem extends RelativeLayout {



    ImageView dayImage;
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
        dayImage = (ImageView)findViewById(R.id.dayImage);
    }
    public void setImage(Bitmap bitmap)
    {
        dayImage.setImageBitmap(bitmap);
    }
}
