package org.androidtown.mytap.org.androidtown.setting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.MySharedPreference;
import org.androidtown.mytap.ProgressDialog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by constant on 15. 8. 13..
 */
public class FriendAdapter extends BaseAdapter {

    Context mContext;


    boolean parsingOk=false;

    MySharedPreference mySharedPreference;
    String mySession;

    String[] friends;
    int[] friendsId;

    int length;

    public FriendAdapter(Context context) {
        mContext = context;

        mySharedPreference = new MySharedPreference(mContext);
        mySession = mySharedPreference.getPreferences();

        loadFriend();

    }

    @Override
    public int getCount() {
        return length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendList_item item;
        if(convertView==null) {
            item= new FriendList_item(mContext);
        } else {
            item=(FriendList_item)convertView;
        }
        while (true) {

            if (parsingOk){

                Log.d(MainActivity.TAG, "(화면 3) ㅅㄱ" + position);
                item.setInfo(friends[position]);
                item.setFriendId(friendsId[position]);
            }


            return item;
        }
    }

    public void loadFriend()
    {
        connectCheck();
    }

    public void connectCheck()
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            String urlStr = "http://52.69.116.105:8000/friends/list/";

            Log.d(MainActivity.TAG, "(화면 4) URL IS " + urlStr);

            new FriendNetwork().execute(urlStr);
        } else {
            // display error
            Toast.makeText(mContext, "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
        }
    }
    class FriendNetwork extends AsyncTask<String, Void, String[]> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog= new ProgressDialog(mContext);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String[] params) {

            String urlStr = params[0];
            StringBuffer buffer = new StringBuffer();
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Cookie", mySession);
                InputStream is=conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d(MainActivity.TAG, " (화면 4) 전체 메세지 " + line);
                    buffer.append(line);
                }
                Log.d(MainActivity.TAG, "(화면 4) 배열 파싱 시작준비");

                JSONObject root = new JSONObject(buffer.toString());
                JSONArray array = root.getJSONArray("friends");
                friends = new String[array.length()];
                friendsId = new int[array.length()];
                length = array.length();
                for(int i=0; i<array.length(); i++)
                {
                    JSONObject d = array.getJSONObject(i);
                    Log.d(MainActivity.TAG, "(화면 4) i : " + i);

                    friends[i] = d.getString("username");
                    Log.d(MainActivity.TAG, "(화면 4) 친구 이름 : " + friends[i]);
                    friendsId[i] = d.getInt("id");
                    Log.d(MainActivity.TAG, "(화면 4) 친구 아이디 : " + friendsId[i]);

                }
                Log.d(MainActivity.TAG, "(화면 4) 친구 목록 " + friends);

            }catch(Exception e){
                Log.d(MainActivity.TAG, "(화면 3) error2 is " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            Log.d(MainActivity.TAG, "(화면 4) 파싱 끝났따");

            parsingOk=true;

            notifyDataSetChanged();
            dialog.dismiss();

        }
    }

    public void connectCheck2(int position) {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            String urlStr = "http://52.69.116.105:8000/friends/delete/" + String.valueOf(position);

            Log.d(MainActivity.TAG, "(화면 4) URL IS " + urlStr);

            new DeleteFriendNetwork().execute(urlStr);
        } else {
            // display error
            Toast.makeText(mContext, "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
        }
    }

    class DeleteFriendNetwork extends AsyncTask<String, Void, String[]> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(mContext);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String[] params) {

            String urlStr = params[0];
            StringBuffer buffer = new StringBuffer();
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Cookie", mySession);


            } catch (Exception e) {
                Log.d(MainActivity.TAG, "(화면 4) error2 is " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            Log.d(MainActivity.TAG, "(화면 4) 파싱 끝났따");

            parsingOk = true;
            notifyDataSetChanged();
            dialog.dismiss();

        }
    }
}





