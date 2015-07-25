package org.androidtown.mytap.org.androidtown.myStatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidtown.mytap.NetManager;
import org.androidtown.mytap.R;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by constant on 15. 7. 13..
 */
public class CommentActivity extends Activity {

    Button backButton;
    Button doneButton;
    MyEditText commentEdit;
    TextView currentText;
    String comment = null;

    Intent getIntent;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss", Locale.KOREA);
    Date current = new Date();
    String time = simpleDateFormat.format(current);

    String myImage;
    String myComment;
    String TAG = "MyStatus";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_view);
        backButton = (Button) findViewById(R.id.backButton);
        doneButton = (Button) findViewById(R.id.doneButton);
        currentText = (TextView)findViewById(R.id.currentText);
        commentEdit = (MyEditText) findViewById(R.id.commentEdit);

        //이미 저장 되어 있는 값 있으면
        getIntent= new Intent(getIntent());

        commentEdit.setText(getIntent.getStringExtra("comment"));
        currentText.setText(String.valueOf(getIntent.getIntExtra("length",0)) + "자");

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
                Log.d(TAG, "cancel clicked");
                finish();
            }

        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int myImage = getIntent.getIntExtra("image", 1);
                if (commentEdit.getText() != null) {
                    comment = commentEdit.getText().toString();
                } else {
                    comment = "";
                }

                storeStatus(myImage, comment);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("comment", comment);
                resultIntent.putExtra("length", comment.length());

                Log.d(TAG, "done clicked");

                setResult(RESULT_OK, resultIntent);


                finish();

            }
        });


    }

    public void storeStatus(int image, String comment) {
        //서버로 업로드
        if(comment==null)
            comment="";
        myImage=String.valueOf(image);
        myComment=comment;
        new NetworkThread().run();

        Log.d("ExtraActivity","myImage is : " + myImage +  ", myComment is : " + myComment + ", current time is : " + time);

    }

    class NetworkThread extends Thread{
        @Override
        public void run() {
            super.run();
            String urlStr = "http://google.co.kr";
            String line = "";
            BufferedReader br = null;


            try {
                //서버에 전송해야 함.
                List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                nameValue.add(new BasicNameValuePair("myImage", myImage));
                nameValue.add(new BasicNameValuePair("myComment", myComment));
                nameValue.add(new BasicNameValuePair("time", time));

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValue);

                HttpClient client = NetManager.getHttpClient();
                HttpPost post = NetManager.getPost(urlStr);

                HttpResponse response = null;
                StringBuffer sb = null;


                post.setEntity(entity);
                response = client.execute(post);
                int code = response.getStatusLine().getStatusCode();
                Log.v(TAG, "process code : " + code);
                switch(code){
                    case 200 :
                        Log.d(TAG, "Success data transmit");
                        break;
                    default :
                        Log.d(TAG, "Fail data transmit");
                }
            }catch(Exception e){
                Log.v(TAG, "Fail Server Connect : " + e);
            }

        }
    }

}
