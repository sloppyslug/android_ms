package org.androidtown.mytap;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by sangsoo on 2015-06-25.
 */
public class ListView_item extends LinearLayout {
    ImageView item_image;
    public ListView_item(Context context) {
        super(context);
        init(context);
    }

    public ListView_item(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void init(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item, this, true);
        item_image= (ImageView)findViewById(R.id.item_image);
    }
    public void setImage(Bitmap bitmap)
    {
        item_image.setImageBitmap(bitmap);
    }
}
