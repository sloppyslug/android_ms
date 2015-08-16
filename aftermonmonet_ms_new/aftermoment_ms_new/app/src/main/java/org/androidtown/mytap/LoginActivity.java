package org.androidtown.mytap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by MSPark.08.15
 * */
public class LoginActivity extends Activity {

    EditText idEdit;
    EditText pwdEdit;
    Button doneButton;
    Button backButton;

    String mySessionId;

    MySharedPreference mySharedPreference;


    private String sendUrl = "http://52.69.116.105:8000/accounts/login/";

    String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        idEdit = (EditText)findViewById(R.id.idEdit);
        pwdEdit = (EditText)findViewById(R.id.pwdEdit);
        doneButton = (Button)findViewById(R.id.doneButton);
        idEdit.setFilters(new InputFilter[]{filter});
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectCheck();
            }
        });
        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });







    }
            public void connectCheck(){

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {

                new SendMyId().execute(sendUrl);
            } else {
                // display error
                Toast.makeText(this, "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
            }
        }

               protected InputFilter filter = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                   Pattern ps = Pattern.compile("^[a-zA-Z0-9@_+.-]*$");
                    if (!ps.matcher(source).matches())

                 {    Toast.makeText(LoginActivity.this, "'ID는 영문자, 숫자, _, @, ., +, =로만 구성되어야 합니다.'", Toast.LENGTH_SHORT).show();
                  return "";


                   }

                  return null;
                  }


    };


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
                    Log.d(MainActivity.TAG, "(login) URL IS " + urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    conn.setRequestMethod("POST");

                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    List<NameValuePair> nameValue = new ArrayList<NameValuePair>();

                    nameValue.add(new BasicNameValuePair("username", idEdit.getText().toString()));
                    nameValue.add(new BasicNameValuePair("password", pwdEdit.getText().toString()));

                    Log.d(MainActivity.TAG, "(login) id, pwd is : " + idEdit.getText().toString() + "  " + pwdEdit.getText().toString());

                    OutputStream os = conn.getOutputStream();

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    writer.write(getQuery(nameValue));
                    writer.flush();
                    writer.close();
                    os.close();
                    conn.connect();
                    int code = conn.getResponseCode();
                    Log.v(MainActivity.TAG, "(login) process code : " + code);
                    switch (code) {
                        case 200:
                            List<String> cookies = conn.getHeaderFields().get("set-cookie");

                            if (cookies != null) {
                                Log.d(MainActivity.TAG, "(login): " + cookies);


                                for (String cookie : cookies) {


                                    Log.d(MainActivity.TAG, "(login)갯수 : " + cookies.size());
                                    Log.d(MainActivity.TAG, "(login)원래 화면0 " + cookie.split(";\\s*")[0]);
                                    if (cookie.split(";\\s*")[0].charAt(0) == 's') {
                                        Log.d(MainActivity.TAG, "(login)세션2 : " + mySessionId);

                                        mySessionId = cookie.split(";\\s*")[0];
                                        Log.d(MainActivity.TAG, "(login)세션3 : " + mySessionId);
                                    }
                                }

                            }
                            Log.d(MainActivity.TAG, "(login)  Success data transmit");

                           break;
                        default:
                            Log.d(MainActivity.TAG, "(login) Fail data transmit");
                    }

                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line= reader.readLine();
                    Log.d("박민수","line 나와라 : " + line);

                    if (line.equals("1"))

                    {
                        status = "success";
                        Log.d("박민수" , "로그인 완료 : " +  status );
                    }
                         else {


                        status="fail";

                        Log.d("박민수" , "로그인 실패 : " + status);
                    }

                    }   catch (IOException e) {
                        Log.d("ASDF", "ERROR IS " + e);
                        e.printStackTrace();


                }       catch (Exception e) {
                    Log.d("ASDF", "ERROR IS " + e);

                    e.printStackTrace();
                }


              return null;


            }






            @Override
            protected void onPostExecute(Void aVoid) {
                dialog.dismiss();
                if(status.equals("success"))
                {
                    Toast.makeText(LoginActivity.this, "로그인 되었습니다.", Toast.LENGTH_LONG).show();
                    mySharedPreference = new MySharedPreference(LoginActivity.this);
                    mySharedPreference.saveIDPreferences(idEdit.getText().toString());
                    mySharedPreference.savePasswordPreferences(pwdEdit.getText().toString());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("mySessionId", mySessionId);
                    startActivity(intent);
                    finish();
                }

                if (status.equals("fail"))

                {   Toast.makeText(LoginActivity.this, "ID와 비밀번호를 다시 한번 확인하세요", Toast.LENGTH_LONG).show();
                    return;


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
