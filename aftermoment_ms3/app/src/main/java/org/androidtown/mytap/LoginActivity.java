package org.androidtown.mytap;

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
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by constant on 15. 7. 29..
 */
public class LoginActivity extends Activity {

    EditText idEdit;
    EditText pwdEdit;
    Button doneButton;
    Button backButton;

    String[] myCookie = new String[2];
    String mySessionId;


    private String sendUrl = "http://52.69.116.105:8000/accounts/login/";

    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        idEdit = (EditText)findViewById(R.id.idEdit);
        pwdEdit = (EditText)findViewById(R.id.pwdEdit);
        doneButton = (Button)findViewById(R.id.doneButton);
        backButton = (Button)findViewById(R.id.backButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectCheck();
            }
        });

       backButton.setOnClickListener(new View.OnClickListener() {
           @Override

           public void onClick(View v) {
               Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
               startActivity(intent);

               finish();


           }

       });



    }
             @Override
              /*이 메소도는 이 화면에서 뒤로가기 버튼 즉 back버튼이 아니라 휴대폰의 뒤로가기 버튼을 눌렀을때 바로 종료 되는 것을 막는 메소드야*/
             public void onBackPressed(){
             Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
              startActivity(intent);

             finish();
              super.onBackPressed();
             }


    public void connectCheck(){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            //Toast.makeText(this,"네트워크 연결중입니다.", Toast.LENGTH_SHORT).show();
            new SendMyId().execute(sendUrl);
        } else {
            // display error
            Toast.makeText(this, "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
        }
    }


    class SendMyId extends AsyncTask<String, Void, Void>
    {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            String urlStr = params[0];
            try {
                URL url = new URL(urlStr);
                Log.d(MainActivity.TAG, "(화면 0) URL IS " + urlStr);
                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                List<NameValuePair> nameValue = new ArrayList<NameValuePair>();

                nameValue.add(new BasicNameValuePair("username", idEdit.getText().toString()));
                nameValue.add(new BasicNameValuePair("password", pwdEdit.getText().toString()));

                Log.d(MainActivity.TAG, "(화면 0) id, pwd is : " + idEdit.getText().toString() + "  " + pwdEdit.getText().toString());

                OutputStream os = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(getQuery(nameValue));
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
                int code = conn.getResponseCode();
                Log.v(MainActivity.TAG, "(화면 0) process code : " + code);
                switch (code) {
                    case 200:
                        List<String> cookies = conn.getHeaderFields().get("set-cookie");

                        if (cookies != null) {

                            for (String cookie : cookies) {
                                Log.d(MainActivity.TAG, "갯수 : " + cookies.size());
                                Log.d(MainActivity.TAG, "원래 화면0 " + cookie.split(";\\s*")[0]);
                                myCookie=cookie.split(";\\s*");
                            }Log.d(MainActivity.TAG, "response  화면0 " + myCookie[0]);
                            mySessionId=myCookie[0];
                        }
                        Log.d(MainActivity.TAG, "response  (화면 0)  Success data transmit");

                        Map<String,List<String>> headers = conn.getHeaderFields();
                        Iterator<String> it = headers.keySet().iterator();
                        while(it.hasNext()) {
                            String key = it.next();
                            List<String> values = headers.get(key);
                            StringBuffer sb = new StringBuffer();
                            for(int i=0; i<values.size(); i++) {
                                sb.append(";" + values.get(i));
                            }
                            Log.d(MainActivity.TAG, "response  (화면0) " + key + "=" + sb.toString().substring(1));
                        }
                        break;
                    default:
                        Log.d(MainActivity.TAG, "(화면 0) Fail data transmit");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            status="success";
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            if(status.equals("success"))
            {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("mySessionId", mySessionId);
                startActivity(intent);

            }
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
