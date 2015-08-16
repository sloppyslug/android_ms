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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
 * Created by MSPARK  08.15*/

public class MakeAdminActivity extends Activity {


    EditText idEdit;
    EditText pwdEdit;
    EditText pwdEdit_re;
    Button doneButton;
    Button backButton;
    Activity act = this;
    String pwd_edit;
    String id_edit;
    String pwd_edit_re;
    boolean is;
    String sendUrl = "http://52.69.116.105:8000/accounts/register/";
    String status;
    String[] myCookie = new String[2];
    String mySessionId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);



        idEdit = (EditText) findViewById(R.id.idEdit);
        pwdEdit = (EditText) findViewById(R.id.pwdEdit);
        pwdEdit_re = (EditText) findViewById(R.id.pwdEdit_re);
        doneButton = (Button) findViewById(R.id.doneButton);
        backButton = (Button) findViewById(R.id.backButton);
        idEdit.setFilters(new InputFilter[]{filter});



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    finish();
                }

        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_edit = idEdit.getText().toString();
                pwd_edit = pwdEdit.getText().toString();
                pwd_edit_re = pwdEdit_re.getText().toString();


                if (checked())


                    connectCheck();



            }



        });

    }


    private boolean checked() {


        boolean state = false;



        if (TextUtils.isEmpty(((EditText) (findViewById(R.id.idEdit))).getText().toString())) {
            Toast.makeText(MakeAdminActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }



        if (TextUtils.isEmpty(((EditText) (findViewById(R.id.pwdEdit))).getText().toString())) {
            Toast.makeText(MakeAdminActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pwdEdit.length() < 6) {
            Toast.makeText(act, "비밀번호를 6글자 이상 입력하세요", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!pwd_edit.equals(pwd_edit_re))

        { Toast.makeText(act, "비밀번호가 맞지 않습니다",Toast.LENGTH_LONG).show();
            return false;



    }
        if (sendUrl == null) {
            Toast.makeText(act, "서버와의 연결이 원활하지 않습니다.", Toast.LENGTH_LONG).show();
            return  false;
        }

        return true;
    }

    protected InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Pattern ps = Pattern.compile("^[a-zA-Z0-9@_+.-]*$");
            if (!ps.matcher(source).matches())

            {    Toast.makeText(act, "'ID는 영문자, 숫자, _, @, ., +, =로만 구성되어야 합니다.'", Toast.LENGTH_SHORT).show();
                return "";


            }

            return null;
        }


    };



    public void connectCheck() {


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            new send_admin().execute();
        }

        else

        {

            Toast.makeText(this, "네트워크 상태를 확인 해주세요", Toast.LENGTH_SHORT).show();
        }

    }

    private class send_admin extends AsyncTask<String, Void, Void>

    {  ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MakeAdminActivity.this);
            dialog.show();


    }
        @Override
        protected Void doInBackground(String... params) {


            try {

                URL url = new URL(sendUrl);
                Log.d("error bu", "url is " + sendUrl);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setConnectTimeout(10000);
                http.setReadTimeout(10000);
                http.setDoInput(true); // 서버에서 읽기 모드 지정
                http.setDoOutput(true); // 서버로 쓰기 모드 지정
                http.setRequestMethod("POST"); // 전송 방식은 POST


                List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                nameValue.add(new BasicNameValuePair("username", id_edit));
                Log.d("박민수", "username : " + id_edit);

                nameValue.add(new BasicNameValuePair("password", pwd_edit));
                Log.d("박민수", "password : " + pwd_edit);


                OutputStream os = http.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(nameValue));
                writer.flush();
                writer.close();
                os.close();
                int code = http.getResponseCode();
                Log.v(MainActivity.TAG, "(login) process code : " + code);
                switch (code) {
                    case 200:
                        List<String> cookies = http.getHeaderFields().get("set-cookie");

                        if (cookies != null) {
                            Log.d(MainActivity.TAG, "(make): " + cookies);



                            for (String cookie : cookies) {

                                Log.d(MainActivity.TAG, "(make)갯수 : " + cookies.size());
                                Log.d(MainActivity.TAG, "(make)원래 화면0 " + cookie.split(";\\s*")[0]);

                                myCookie = cookie.split(";\\s*");
                                mySessionId = cookie.split(";\\s*")[0];
                            }
                            for(int i=0; i<2; i++)
                            {
                                if(myCookie[i].charAt(0)=='s')
                                {
                                    mySessionId = myCookie[i];
                                    Log.d(MainActivity.TAG, "(login)세션 : " + mySessionId);



                                }


                            }
                        }
                        Log.d(MainActivity.TAG, "(make)  Success data transmit");

                        break;
                    default:
                        Log.d(MainActivity.TAG, "(make) Fail data transmit");
                }

                InputStream is = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line= reader.readLine();
                Log.d("박민수","line 회원가입 나와라 : " + line);

                if (line.equals("1"))
                {

                    status = "success";
                    Log.d("박민수" , "회원가입 완료 : " +  status );
                }
                else {


                    status="fail";
                    Log.d("박민수" , "회원가입 실패 : " + status);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            if (status.equals("success")) {
                Intent intent = new Intent(MakeAdminActivity.this, LoginActivity.class);
                is = true;
                Toast.makeText(MakeAdminActivity.this, "회원가입에 성공하였습니다.",Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MakeAdminActivity.this, "이미 가입된 ID 입니다.", Toast.LENGTH_LONG).show();
                return;

            }


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






