package org.androidtown.mytap.org.androidtown.myStatus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.androidtown.mytap.R;

import java.util.ArrayList;

/**
 * Created by sangsoo on 2015-06-27.
 */
public class MyStatus extends Fragment {

    PickerView pickerView;
    EditText editText;
    String TAG = "emoment";
    int myposition;


    private ArrayList<Bitmap> items= new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.my_status, container, false);
        pickerView = (PickerView) vi.findViewById(R.id.pickerView);
        editText = (EditText) vi.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            String previousString = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousString = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getLineCount() >= 4) {
                    editText.setText(previousString);
                    editText.setSelection(editText.length());
                }
            }
        });



        Bitmap emoticon1 = BitmapFactory.decodeResource(vi.getResources(), R.drawable.emoticon1);
        Bitmap emoticon2 = BitmapFactory.decodeResource(vi.getResources(),R.drawable.emoticon2);
        Bitmap emoticon3 = BitmapFactory.decodeResource(vi.getResources(),R.drawable.emoticon3);
        Bitmap emoticon4 = BitmapFactory.decodeResource(vi.getResources(),R.drawable.icon);

        for(int i=0; i<5; i++)
        {
            items.add(emoticon1);
            items.add(emoticon2);
            items.add(emoticon3);
            items.add(emoticon4);
        }
        pickerView.setList(items);

        pickerView.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                setImage(position);
                Log.d(TAG, "SELECT ITEM IS : " + getImage());
            }
        });



        return vi;
    }

    public void setImage(int position)
    {
        myposition=position;
    }

    public int getImage() { return  myposition;}


}
