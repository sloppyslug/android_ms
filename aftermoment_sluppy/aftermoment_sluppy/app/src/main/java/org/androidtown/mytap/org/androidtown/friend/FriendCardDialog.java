package org.androidtown.mytap.org.androidtown.friend;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by constant on 15. 7. 17..
 */
public class FriendCardDialog extends Dialog {

    TextView nameT;
    ImageView imageView;
    TextView textView;
    TextView day;

    Context mContext;
    String name;
    String emotion;
    String comment;
    String titleComment;
    SimpleDateFormat dateFormat;
    Date date;
    String dateToday;

    public FriendCardDialog(Context context, String name, String emotion, String comment, String title) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        date = new Date();
        dateToday=dateFormat.format(date);
        Log.d(MainActivity.TAG, "(화면 3 날짜) " + date);
        mContext=context;
        this.name = name;
        this.emotion = emotion;
        this.comment = comment;
        this.titleComment=title;
        Log.d(MainActivity.TAG, "(화면3) emotion is " + emotion + " comment is " + comment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_dialog2);
        day = (TextView)findViewById(R.id.day);

        nameT = (TextView)findViewById(R.id.name);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        int tempInt = Integer.parseInt(emotion);
        Log.d(MainActivity.TAG, "tempInt is" + tempInt);

        switch (tempInt-2) {
            case 0:
                imageView.setImageResource(R.drawable.soso);
                break;
            case 1:
                imageView.setImageResource(R.drawable.happy);
                break;
            case 2:
                imageView.setImageResource(R.drawable.sohappy);
                break;
            case 3:
                imageView.setImageResource(R.drawable.sad);
                break;
            case 4:
                imageView.setImageResource(R.drawable.angry);
                break;
        }
        Log.d(MainActivity.TAG, "card comment is " + comment);
        textView.setText(comment);
        nameT.setText(name);
        day.setText(dateToday);
    }
}
