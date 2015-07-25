package org.androidtown.mytap.org.androidtown.myStatus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.mytap.MySharedPreference;
import org.androidtown.mytap.R;

import java.util.ArrayList;

/**
 * Created by sangsoo on 2015-06-27.
 */
public class MyStatus extends Fragment {

    public static final int myRequestCode=5001;

    View vi;
    PickerView pickerView;
    TextView commentText;
    String comment=null;
    int commentLength;
    String TAG = "MyStatus";
    int myPosition;
    MySharedPreference mySharedPreference;
    private ArrayList<Bitmap> items= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        vi= inflater.inflate(R.layout.my_status, container, false);
        pickerView = (PickerView) vi.findViewById(R.id.pickerView);
        commentText= (TextView) vi.findViewById(R.id.commentText);

        //이모티콘 로드
        loadEmoticon();
        //저장된게 있으면 불러와.
        loadMyStatus();

        //코멘트 창 클릭하면
        commentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("image", myPosition);
                //입력 된게 있으면
                if (comment != null) {
                    intent.putExtra("comment", comment);
                    intent.putExtra("length", comment.length());
                }
                startActivityForResult(intent, myRequestCode);
            }
        });

        //pickerView 선택 될 때 마다
        pickerView.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                comment=mySharedPreference.getPreferences();
                mySharedPreference.savePreferencesImage(position);
                setImage(position);
                Log.d(TAG, "setImage is " + position);
                new CommentActivity().storeStatus(position, comment);
            }
        });

        return vi;
    }

    //intent 넘어온 결과 처리
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==myRequestCode && resultCode==getActivity().RESULT_OK)
        {
            Log.d(TAG, "get intent");
            comment = data.getStringExtra("comment");
            commentLength = data.getIntExtra("length",1);
            if(comment==null)
            {
                commentText.setText("오늘 기분은 어떠세요?");
            }else{
                commentText.setText(comment);
                mySharedPreference.savePreferences(comment);
            }
        }else if(requestCode==myRequestCode && resultCode==getActivity().RESULT_CANCELED)
        {
            Log.d(TAG, "cancel intent");
        }

    }

    public void setImage(int position)
    {
        myPosition=position;
    }

    public void loadEmoticon()
    {

        Bitmap emoticon1 = BitmapFactory.decodeResource(vi.getResources(), R.drawable.emoticon1);
        Bitmap emoticon2 = BitmapFactory.decodeResource(vi.getResources(),R.drawable.emoticon2);
        Bitmap emoticon3 = BitmapFactory.decodeResource(vi.getResources(),R.drawable.emoticon3);
        Bitmap emoticon4 = BitmapFactory.decodeResource(vi.getResources(), R.drawable.icon);

        items.add(emoticon1);
        items.add(emoticon2);
        items.add(emoticon3);
        items.add(emoticon4);
        pickerView.setList(items);
    }
    public void loadMyStatus()
    {
        mySharedPreference = new MySharedPreference(this.getActivity());
        commentText.setText(mySharedPreference.getPreferences());
        pickerView.getImagePostion(mySharedPreference.getPreferencesImage());
        Log.d(TAG, "position is : " + mySharedPreference.getPreferencesImage());
    }
}
