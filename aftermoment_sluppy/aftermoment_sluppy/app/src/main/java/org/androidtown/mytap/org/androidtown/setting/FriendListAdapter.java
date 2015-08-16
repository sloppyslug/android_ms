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
import java.util.ArrayList;

/**
 * Created by constant on 15. 8. 15..
 */
public class FriendListAdapter extends BaseAdapter {

    Context mContext;


    MySharedPreference mySharedPreference;
    String mySession;


    ArrayList<String> searchFriend;
    ArrayList<Integer> friendsId;

    int length;

    boolean searchEnd=false;
    boolean searchStart=false;

    OnLabel mLabel;

    int searchNumber=0;
    int searchNumber2=0;

    public FriendListAdapter(Context context) {
        mContext = context;
        searchFriend = new ArrayList<String>();
        friendsId = new ArrayList<Integer>();

        mySharedPreference = new MySharedPreference(mContext);
        mySession = mySharedPreference.getPreferences();
        connectCheck("DFbQ8SKCyC5DT6qCXFNqStGu6ven3xV7ys6ag7CCaQ4JbusXXdfafN64J5Bm29vcL2FWWyBF62AyRgwTGwxgPmmv5AcfELwMtzm54ke542rWXz7Jcs6FFedc6uPeUVuk");
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
    public View getView(final int position, View convertView, ViewGroup parent) {

            if(position<searchNumber)
            {
                Log.d(MainActivity.TAG, "(화면 검색) 검색 1 position is : " + position + "searchNumber is : " + searchNumber);
                List_item3 item;

                item = new List_item3(mContext);

                item.setInfo(searchFriend.get(position));
                item.setFriendId(friendsId.get(position));
                item.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectCheck2(friendsId.get(position), 1);
                    }
                });
                return item;
            }else{
                Log.d(MainActivity.TAG, "(화면 검색) 친구요청 3  position is : " + position + "searchNumber is : " + searchNumber);

                List_item2 item;

                item = new List_item2(mContext);

                item.setInfo(searchFriend.get(position));
                item.setFriendId(friendsId.get(position));
                item.admitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectCheck2(friendsId.get(position), 2);

                    }
                });
                item.rejectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectCheck2(friendsId.get(position), 3);

                    }
                });

                return item;
            }


    }

    public void connectCheck(String name) {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            String urlStr = "http://52.69.116.105:8000/friends/find/" + name;

            Log.d(MainActivity.TAG, "(화면 검색) URL IS " + urlStr);

            new SearchNetwork().execute(urlStr);
        } else {
            // display error
            Toast.makeText(mContext, "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
        }
    }

    class SearchNetwork extends AsyncTask<String, Void, String[]> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(mContext);
            dialog.show();
            searchFriend.clear();
            friendsId.clear();


        }

        @Override
        protected String[] doInBackground(String[] params) {

            String urlStr = params[0];
            StringBuffer buffer = new StringBuffer();
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Cookie", mySession);
                Log.d(MainActivity.TAG, "(화면 검색) mySession is : " + mySession);

                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d(MainActivity.TAG, " (화면 검색) 전체 메세지 " + line);
                    buffer.append(line);
                }
                Log.d(MainActivity.TAG, "(화면 검색) 배열 파싱 시작준비");

                JSONObject root = new JSONObject(buffer.toString());
                JSONArray search = root.getJSONArray("list");


                for (int i = 0; i < search.length(); i++) {
                    JSONObject d = search.getJSONObject(i);
                    Log.d(MainActivity.TAG, "(화면 검색) i : " + i);
                    Log.d(MainActivity.TAG, "(화면 검색) TYPE IS : " + d.getString("type"));

                    if(d.getString("type").equals("search"))
                    {

                        searchNumber++;
                        Log.d(MainActivity.TAG, "(화면 검색) searchNumber" + searchNumber);

                        searchStart=true;
                        Log.d(MainActivity.TAG, "(화면 검색) 안녕");
                    }else {

                        if(i!=0) {
                            searchNumber2=1;//기준선
                        }
                        Log.d(MainActivity.TAG, "(화면 검색) 안녕2");
                    }


                    searchFriend.add(d.getString("friend_username"));
                    friendsId.add(d.getInt("_id"));
                    Log.d(MainActivity.TAG, "(화면 검색) 친구 이름 : " + searchFriend.get(i) + "position is :  " + i);
                    Log.d(MainActivity.TAG, "(화면 검색) 친구 번호 : " + friendsId.get(i) + "position is :  " + i);

                }
                length= searchFriend.size();
                Log.d(MainActivity.TAG, "(화면 검색) 2  " + length);

            } catch (Exception e) {
                Log.d(MainActivity.TAG, "(화면 검색) error2 is " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            dialog.dismiss();
            if (searchStart == true) {
                performSelect();
            }

            notifyDataSetChanged();
            Log.d(MainActivity.TAG, "(화면 검색) 배열 파싱 완료");
            searchStart=false;

        }
    }

    public void setOnLabel(OnLabel label)
    {
        mLabel= label;
    }
    private void performSelect()
    {
        if(mLabel!=null)
            mLabel.setOnLabel();
    }

    public interface OnLabel
    {
        void setOnLabel();
    }


    public void connectCheck2(int id, int num) {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            if(num==1)
            {
                String urlStr = "http://52.69.116.105:8000/friends/request/"+ id;

                Log.d(MainActivity.TAG, "(화면 검색) URL IS " + urlStr);

                new RequestNetwork().execute(urlStr);
            }else if(num==2)
            {
                String urlStr = "http://52.69.116.105:8000/friends/request/" + id + "/accept";

                Log.d(MainActivity.TAG, "(화면 검색) URL IS " + urlStr);

                new RequestNetwork().execute(urlStr);
            }else if(num==3)
            {
                String urlStr = "http://52.69.116.105:8000/friends/request/" + id + "/reject";

                Log.d(MainActivity.TAG, "(화면 검색) URL IS " + urlStr);

                new RequestNetwork().execute(urlStr);
            }
        } else {
            // display error
            Toast.makeText(mContext, "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
        }
    }

    class RequestNetwork extends AsyncTask<String, Void, String[]> {

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
                InputStream is=conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d(MainActivity.TAG, " (요청) 전체 메세지 " + line);
                    buffer.append(line);
                }

            } catch (Exception e) {
                Log.d(MainActivity.TAG, "(요청) error2 is " + e);
                e.printStackTrace();
            }
            Log.d(MainActivity.TAG, "(요청) 성공");

            return null;
        }


        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            dialog.dismiss();
            notifyDataSetChanged();
            Log.d(MainActivity.TAG, "(요청) 배열 파싱 완료");

        }
    }

}