package org.androidtown.mytap.org.androidtown.friend;

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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by constant on 15. 7. 25..
 */
public class GridViewAdapter extends BaseAdapter {

    Context mContext;
    SimpleDateFormat dateFormat;
    Date date;
    String today;
    String[] parsedToday={null,null};

    FriendCardDialog dialog;

    String[][] friendInfo;

    boolean parsingOk=false;

    int length;



    MySharedPreference mySharedPreference;
    String mySession;


    public GridViewAdapter(Context context) {
        dateFormat = new SimpleDateFormat("MM,dd");
        date = new Date();
        today = dateFormat.format(date);
        parsedToday=parse(today);
        Log.d(MainActivity.TAG, "(화면 3) today is : " +today);
        mContext =context;

        mySharedPreference = new MySharedPreference(mContext);
        mySession=mySharedPreference.getPreferences();
        LoadFriend();

    }
    public void LoadFriend() {

        connectCheck();

    }
    public void connectCheck()
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            String urlStr = "http://52.69.116.105:8000/load_friends_moment/";

            Log.d(MainActivity.TAG, "(화면 3) URL IS " + urlStr);

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
                    Log.d(MainActivity.TAG, " (화면 3) 전체 메세지 " + line);
                    buffer.append(line);
                }
                Log.d(MainActivity.TAG, "(화면 3) 배열 파싱 시작준비");

                JSONObject root = new JSONObject(buffer.toString());
                JSONArray array = root.getJSONArray("friends_moment");
                length = array.length();
                friendInfo = new String[array.length()][3];
                for (int i = 0; i < array.length(); i++) {

                        JSONObject d = array.getJSONObject(i);
                        Log.d(MainActivity.TAG, "(화면 3) i : " + i);

                        friendInfo[i][0] = d.getString("emotion");
                        Log.d(MainActivity.TAG, "(화면 3) emotion : " + friendInfo[i][0]);

                        Log.d(MainActivity.TAG, "(화면 3) emotion : " + friendInfo[0][0]);

                        friendInfo[i][1] = d.getString("username");
                        Log.d(MainActivity.TAG, "(화면 3) username : " + friendInfo[i][1]);

                        friendInfo[i][2] = d.getString("comment");
                        Log.d(MainActivity.TAG, "(화면 3) comment : " + friendInfo[i][2]);

                        Log.d(MainActivity.TAG, "(화면 3) 0 "+ i + "  " + friendInfo[i][1] + " " +
                                friendInfo[i][0] + " " + friendInfo[i][2]);
                }
            }catch(Exception e){
                Log.d(MainActivity.TAG, "(화면 3) error2 is " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            Log.d(MainActivity.TAG, "(화면 3) 파싱 끝났따" );
            parsingOk=true;

            notifyDataSetChanged();
            dialog.dismiss();

            for(int i=0; i<length; i++)
            {
                Log.d(MainActivity.TAG, "(화면 3) 1 " + friendInfo[i][0] + " " + friendInfo[i][1] + " " + friendInfo[i][2]);
            }



        }
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
        Friend_item friend_item;
        if(convertView==null) {
            friend_item= new Friend_item(mContext);
        } else {
            friend_item=(Friend_item)convertView;
        }
        while (true) {

            if (parsingOk){

                Log.d(MainActivity.TAG, "(화면 3) ㅅㄱ"+ position);
                friend_item.setInfo(friendInfo[position][1], friendInfo[position][0], friendInfo[position][2]);
                Log.d(MainActivity.TAG, "(화면 3) 2" + friendInfo[position][1] + " " +
                        friendInfo[position][0] + " " + friendInfo[position][2]);
            }


            return friend_item;
        }
    }

    public void CardDialogShow(int position)
    {
        Log.d(MainActivity.TAG, "(화면 3) card position is " + position);

        String title = parsedToday[0] + "." + parsedToday[1];

        dialog = new FriendCardDialog(mContext, friendInfo[position][1],
                friendInfo[position][0], friendInfo[position][2], title);

        Log.d(MainActivity.TAG, "(화면 3) 1" + friendInfo[position][1] + " " +
                friendInfo[position][0] + " " + friendInfo[position][2]);

        dialog.show();
    }


    public String[] parse(String today)
    {
        String[] temp = {null, null};
        if(today.contains(","))
        {
            temp=today.split(",");
        }else{
            temp=null;
        }
        return temp;
    }
}
