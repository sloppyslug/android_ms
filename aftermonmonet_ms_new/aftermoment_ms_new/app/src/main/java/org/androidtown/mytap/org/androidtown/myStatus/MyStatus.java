package org.androidtown.mytap.org.androidtown.myStatus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.MySharedPreference;
import org.androidtown.mytap.ProgressDialog;
import org.androidtown.mytap.R;
import org.androidtown.mytap.org.androidtown.myStatus.newPicker.AbstractWheel;
import org.androidtown.mytap.org.androidtown.myStatus.newPicker.AbstractWheelTextAdapter;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sangsoo on 2015-06-27.
 */
public class MyStatus extends Fragment {

    public static final int myRequestCode=5001;

    View vi;
    AbstractWheel pickerView;
    TextView commentText;
    String comment="오늘 기분은 어떠세요?";
    boolean commentNull=false;
    int myImage=0;
    boolean parsingOK=false;
    MySharedPreference mySharedPreference;
    String mySession;
    public static final int[] emoticon =
            new int[]{R.drawable.soso, R.drawable.happy, R.drawable.sohappy, R.drawable.sad, R.drawable.angry};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        vi= inflater.inflate(R.layout.my_status, container, false);
        pickerView = (AbstractWheel) vi.findViewById(R.id.pickerView);
        pickerView.setVisibleItems(3);
        pickerView.setViewAdapter(new EmoticonAdapter(getActivity(), emoticon));

        commentText= (TextView) vi.findViewById(R.id.commentText);
        mySharedPreference = new MySharedPreference(getActivity());
        mySession=mySharedPreference.getPreferences();
        Log.d(MainActivity.TAG, "(화면 1) MySession is : " + mySession);

        //저장된게 있으면 불러와.
        loadMyStatus();

        pickerView.setOnSelectListener(new AbstractWheel.OnSelectListener() {
            @Override
            public void onSelect(int position) {

                myImage=position;
                if(comment.equals("오늘 기분은 어떠세요?")) {
                    Log.d(MainActivity.TAG, "(화면 1) 코멘트 없음");
                    comment = null;
                }else{

                        Log.d(MainActivity.TAG, "(화면 1) ff");

                        if(parsingOK==true)
                        {
                            Log.d(MainActivity.TAG, "(화면 1) dd");

                            Log.d(MainActivity.TAG, "(화면 1) 코멘트 있음");
                            comment=commentText.getText().toString();
                            Log.d(MainActivity.TAG, "(화면 1) 코멘트 1: " + comment);
                            Log.d(MainActivity.TAG, "(화면 1) 코멘트 2: " + commentText.getText().toString());
                        }else {
                            Log.d(MainActivity.TAG, "(화면 1) ss");

                            //break;
                        }

                }
                parsingOK=false;
                connectCheck(position, comment, mySession);
            }
        });

        //코멘트 창 클릭하면
        commentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("image", myImage);
                //입력 된게 있으면
                if (!commentNull) {

                    //null값 처리 다시 해줘라.

                    Log.d(MainActivity.TAG, "(화면 1) intent 추가");
                    intent.putExtra("comment", comment);
                    intent.putExtra("length", comment.length());
                }else{
                    Log.d(MainActivity.TAG, "(화면 1) intent ㄴㄴ");
                    intent.putExtra("comment", "");
                    intent.putExtra("length", 0);
                }
                commentNull=false;
                startActivityForResult(intent, myRequestCode);
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
            comment = data.getStringExtra("comment");
            if(comment.equals(""))
            {
                commentText.setText("오늘 기분은 어떠세요?");

            }else{
                commentText.setText(comment);
            }
        }else if(requestCode==myRequestCode && resultCode==getActivity().RESULT_CANCELED)
        {
            Log.d(MainActivity.TAG, "(화면 1) cancel intent");
        }

    }


    public void loadMyStatus()
    {
        connectCheck();
    }
    public void connectCheck(int position, String comment, String mySession){
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            //Toast.makeText(this,"네트워크 연결중입니다.", Toast.LENGTH_SHORT).show();
            new CommentActivity().storeStatus(position, comment, mySession,2);
            Log.d(MainActivity.TAG, "리스너에서 저장 position is : " + position + "  comment is :" + comment);
        } else {
            // display error
            Toast.makeText(getActivity(), "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
        }
    }
    public void connectCheck()
    {
        ConnectivityManager connMgr = (ConnectivityManager)
        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        // fetch data
        //Toast.makeText(this,"네트워크 연결중입니다.", Toast.LENGTH_SHORT).show();
        String urlStr = "http://52.69.116.105:8000/load_current_moment/";
        Log.d(MainActivity.TAG, "(화면 1) URL IS " + urlStr);
        Log.d(MainActivity.TAG, "(화면 1) 오늘날");

        new NetworkThread2().execute(urlStr);
        } else {
        // display error
        Toast.makeText(getActivity(), "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
        }
    }


    class NetworkThread2 extends AsyncTask<String, Void, String[]> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... params)//...은 가변
        {
            String urlStr = params[0];
            StringBuffer buffer = new StringBuffer();
            String[] arr = new String[2];
            try {
                URL url = new URL(urlStr);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Cookie", mySession);
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d(MainActivity.TAG, "(화면 1)  전체 메세지 " + line);
                    buffer.append(line);
                }
                JSONObject root = new JSONObject(buffer.toString());
                String emotion = root.getString("emotion");
                Log.d(MainActivity.TAG, "(화면 1) emotion : " + emotion);
                String comment = root.getString("comment");
                Log.d(MainActivity.TAG, "(화면 1) comment : " + comment);
                arr[0] = emotion;
                arr[1] = comment;
            } catch (Exception e) {
                Log.d(MainActivity.TAG, "(화면 1) 오늘날짜 없음");
                e.printStackTrace();
                arr[0]="0";
                arr[1]="";
            }
            comment=arr[1];

            return arr;
        }

        @Override
        protected void onPostExecute(String[] s) {

            int position=Integer.parseInt(s[0]);
            position-=2;

            pickerView.setCurrentItem(position);

            if(s[1].equals("")) {
                Log.d(MainActivity.TAG, "(화면 1) text is null");
                commentNull = true;
                commentText.setText("오늘 기분은 어떠세요?");
            }else{
                comment=s[1];
                commentText.setText(s[1]);
                Log.d(MainActivity.TAG, "(화면 1) 넣었어   comment is : " + comment);
                parsingOK=true;
            }

            dialog.dismiss();
        }

    }
    private class EmoticonAdapter extends AbstractWheelTextAdapter
    {
        int[] emoticon;
        /**
         * Constructor
         */
        protected EmoticonAdapter(Context context, int[] emoticon) {
            super(context, R.layout.picker_item, NO_RESOURCE);
            this.emoticon=emoticon;

        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);

            ImageView img = (ImageView) view.findViewById(R.id.emotion);
            img.setImageResource(emoticon[index]);

            return view;

        }
        @Override
        public int getItemsCount() {
            return emoticon.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return null;
        }
    }


}
