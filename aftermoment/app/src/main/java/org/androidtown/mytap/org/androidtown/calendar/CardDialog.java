package org.androidtown.mytap.org.androidtown.calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.mytap.R;

/**
 * Created by constant on 15. 7. 17..
 */
public class CardDialog extends Dialog {

    ImageView imageView;
    TextView textView;

    public CardDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_dialog);
        imageView=(ImageView)findViewById(R.id.imageView);
        textView=(TextView)findViewById(R.id.textView);
    }
}
