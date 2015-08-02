package org.androidtown.mytap.org.androidtown.myStatus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.MySharedPreference;
import org.androidtown.mytap.R;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by constant on 15. 7. 13..
 */
public class CommentActivity extends Activity {

    private Button backButton;
    private Button doneButton;
    private MyEditText commentEdit;
    private TextView currentText;

    private Intent getIntent;

    private String myImage;
    private String myComment;

    MySharedPreference mySharedPreference;
    String mySession;

    private String sendUrl = "http://52.69.116.105:8000/save_current_moment/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_view);
        backButton = (Button) findViewById(R.id.backButton);
        doneButton = (Button) findViewById(R.id.doneButton);
        currentText = (TextView) findViewById(R.id.currentText);
        commentEdit = (MyEditText) findViewById(R.id.commentEdit);

        mySharedPreference = new MySharedPreference(this);
        mySession=mySharedPreference.getPreferences();
        Log.d(MainActivity.TAG, "(화면 1-1) MySession is : " + mySession);
        getIntent = new Intent(getIntent());
        if(!getIntent.getStringExtra("comment").equals(""))
            commentEdit.setText(getIntent.getStringExtra("comment"));
        currentText.setText(String.valueOf(getIntent.getIntExtra("length", 0)) + "자");

        commentEdit.setSelection(commentEdit.length());

        commentEdit.setOnTextLengthListener(new MyEditText.OnTextLengthListener() {
            @Override
            public void onTextLength(int length) {
                currentText.setText(String.valueOf(length) + "자");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }

        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = null;

                int myImage = getIntent.getIntExtra("image", 0);
                if (commentEdit.getText() != null) {
                    comment = commentEdit.getText().toString();
                } else {
                    comment = "";
                }

                storeStatus(myImage, comment, mySession,1);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("comment", comment);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });


    }
    public void connectCheck(int image, String comment, String mySession){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            //Toast.makeText(this,"네트워크 연결중입니다.", Toast.LENGTH_SHORT).show();
            new SendMyComment().execute(sendUrl, mySession);
        } else {
            // display error
            Toast.makeText(this, "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
        }
    }


    public void storeStatus(int image, String comment, String mySession,int i) {
        image+=2;
        if(i==1)
        {
            //서버로 업로드
            if (comment == null)
                comment = "";
            myImage = String.valueOf(image);
            myComment = comment;
            Log.d(MainActivity.TAG, "(화면 1-1) image is : " + myImage + " comment is " + myComment);
            connectCheck(image, comment, mySession);
        }else if(i==2)
        {
            //서버로 업로드
            if (comment == null)
                comment = "";
            myImage = String.valueOf(image);
            myComment = comment;
            Log.d(MainActivity.TAG, "(화면 1-1) image is : " + myImage + " comment is " + myComment);
            new SendMyComment().execute(sendUrl, mySession);
        }

    }

    class SendMyComment extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... params) {
            String urlStr = params[0];
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Cookie", params[1]);
                Log.d(MainActivity.TAG, "(화면 1-1) mySession is : " + params[1]);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                List<NameValuePair> nameValue = new ArrayList<NameValuePair>();

                nameValue.add(new BasicNameValuePair("emotion", myImage));
                nameValue.add(new BasicNameValuePair("comment", myComment));

                OutputStream os = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(getQuery(nameValue));
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
                int code = conn.getResponseCode();
                Log.v(MainActivity.TAG, "(화면 1-1)  process code : " + code);
                switch(code){
                    case 200 :
                        Log.d(MainActivity.TAG, "(화면 1-1) Success data transmit");
                        break;
                    default :
                        Log.d(MainActivity.TAG, "(화면 1-1) Fail data transmit");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params)
            {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }
            return result.toString();
        }
    }
}
