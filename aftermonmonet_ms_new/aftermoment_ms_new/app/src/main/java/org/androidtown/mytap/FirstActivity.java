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
import java.util.List;

/**
 * Created by constant on 15. 8. 1..
 */
public class FirstActivity extends Activity implements View.OnClickListener{
    Button loginButton;
    Button signinButton;

    MySharedPreference mySharedPreference;

    private String sendUrl = "http://52.69.116.105:8000/accounts/login/";

    String[] myCookie = new String[2];
    String mySessionId;

    String status;

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = FirstActivity.this;
        mySharedPreference = new MySharedPreference(FirstActivity.this);
        if(!mySharedPreference.getIDPreferences().equals(""))
        {
            setContentView(R.layout.background);
            Log.d(MainActivity.TAG, "로그인 되어있네");
            Log.d(MainActivity.TAG, "ㅅㅂ"+ mySharedPreference.getIDPreferences());

            connectCheck();
        }else {
            setContentView(R.layout.fist_activity);
            loginButton = (Button) findViewById(R.id.loginButton);
            loginButton.setOnClickListener(this);
            signinButton = (Button) findViewById(R.id.signinButton);
            signinButton.setOnClickListener(this);
        }
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
            dialog = new ProgressDialog(FirstActivity.this);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            String urlStr = params[0];
            try {
                URL url = new URL(urlStr);
                Log.d(MainActivity.TAG, "(처음) URL IS " + urlStr);
                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                List<NameValuePair> nameValue = new ArrayList<NameValuePair>();

                nameValue.add(new BasicNameValuePair("username", mySharedPreference.getIDPreferences()));
                nameValue.add(new BasicNameValuePair("password", mySharedPreference.getPasswordPreferences()));

                Log.d(MainActivity.TAG, "(처음) id, pwd is : " + mySharedPreference.getIDPreferences() + "  " + mySharedPreference.getPasswordPreferences());

                OutputStream os = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(getQuery(nameValue));
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
                int code = conn.getResponseCode();
                Log.v(MainActivity.TAG, "(처음) process code : " + code);
                switch (code) {
                    case 200:
                        List<String> cookies = conn.getHeaderFields().get("set-cookie");

                        if (cookies != null) {
                            Log.d(MainActivity.TAG, "(login): " + cookies);



                            for (String cookie : cookies) {


                                Log.d(MainActivity.TAG, "(login)갯수 : " + cookies.size());
                                Log.d(MainActivity.TAG, "(login)원래 화면0 " + cookie.split(";\\s*")[0]);
                                if(cookie.split(";\\s*")[0].charAt(0)=='s')
                                {
                                    Log.d(MainActivity.TAG, "(login)세션2 : " + mySessionId);

                                    mySessionId = cookie.split(";\\s*")[0];
                                    Log.d(MainActivity.TAG, "(login)세션3 : " + mySessionId);
                                }
                            }

                        }
                        Log.d(MainActivity.TAG, "first response  (처음)  Success data transmit");
                        break;
                    default:
                        Log.d(MainActivity.TAG, "first (처음) Fail data transmit");
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
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                intent.putExtra("mySessionId", mySessionId);
                startActivity(intent);
                finish();
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


        @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.loginButton:
                Log.d(MainActivity.TAG, "LOGINBUTTON CLICKED");
                Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.signinButton:
                Log.d(MainActivity.TAG, "SIGNINBUTTON CLICKED");
                Intent intent2 = new Intent(FirstActivity.this, MakeAdminActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
