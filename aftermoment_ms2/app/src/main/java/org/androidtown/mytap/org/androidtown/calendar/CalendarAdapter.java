/*
* Copyright 2011 Lauri Nevala.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.androidtown.mytap.org.androidtown.calendar;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.MySharedPreference;
import org.androidtown.mytap.ProgressDialog;
import org.androidtown.mytap.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter {
    static final int FIRST_DAY_OF_WEEK = 0; // Sunday = 0, Monday = 1

    private Context mContext;

    private Calendar month;
    private CardDialog dialog;
    private TextView dayView;
    private int firstDay;
    private int lastDay;

    private ImageView dateImage;
    private ImageView commentImage;

    //1~30(31)
    public String[] days;
    //해당 월의 모든 날짜의 정보.
    private String[][] dayInfo;

    String currentMonth;

    boolean parsingOk=false;

    MySharedPreference mySharedPreference;
    String mySession;



    public CalendarAdapter(Context c, Calendar monthCalendar) {
        month = monthCalendar;
        mContext = c;
        month.set(Calendar.DAY_OF_MONTH, 1);
        refreshDays();
        mySharedPreference = new MySharedPreference(mContext);
        mySession=mySharedPreference.getPreferences();
        Log.d(MainActivity.TAG, "(화면 2) MySession is : " + mySession);
        LoadCalendar(month.get(Calendar.MONTH));
        Log.d(MainActivity.TAG, "(화면 2) hi");

    }

    public int getCount() {
        return days.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_item, null);
        }
        dayView = (TextView) v.findViewById(R.id.date);
        dateImage = (ImageView)v.findViewById(R.id.date_icon);
        commentImage = (ImageView)v.findViewById(R.id.commentImage);
         //모두 클릭 안되게 한후
        dayView.setClickable(false);
        dayView.setFocusable(false);

        //글자가 있는거는 클릭가능
        if (days[position].equals("")) {
            dayView.setClickable(true);
            dayView.setFocusable(true);
        }

        //글자 쓰기
        if(position%7==6)
        {
            dayView.setTextColor(Color.parseColor("#8fc31f"));
        }else if(position%7==0)
        {
            dayView.setTextColor(Color.parseColor("#8fc31f"));
        }else{
            dayView.setTextColor(Color.parseColor("#dae000"));
        }
        dayView.setText(days[position]);

        while (true)
        {
            if(parsingOk)
            {
                if(!days[position].equals(""))
                {
                    Log.d(MainActivity.TAG, "(화면 2) po  " + days[position]);
                    int t = Integer.parseInt(days[position])-1;
                    if (dayInfo[t][0] != null) {
                        Log.d(MainActivity.TAG, "(화면 2) if문 들어옴 ");
                        int temp = Integer.parseInt(dayInfo[t][0])-2;
                        switch (temp) {

                            case 0:
                                Log.d(MainActivity.TAG, "(화면 2) 1");

                                dateImage.setImageResource(R.drawable.cal_soso);
                                break;
                            case 1:
                                Log.d(MainActivity.TAG, "(화면 2) 2");
                                dateImage.setImageResource(R.drawable.cal_happy);

                                break;
                            case 2:
                                Log.d(MainActivity.TAG, "(화면 2) 3");
                                dateImage.setImageResource(R.drawable.cal_sohappy);

                                break;
                            case 3:
                                Log.d(MainActivity.TAG, "(화면 2) 4");
                                dateImage.setImageResource(R.drawable.cal_sad);

                                break;
                            case 4:
                                Log.d(MainActivity.TAG, "(화면 2) 5");
                                dateImage.setImageResource(R.drawable.cal_angry);

                                break;
                        }
                        if(!dayInfo[t][1].equals(""))
                        {
                            commentImage.setVisibility(View.VISIBLE);

                        }
                    }
                }
            }
            return v;
        }
    }

    public void refreshDays() {
        lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        firstDay = (int) month.get(Calendar.DAY_OF_WEEK);
        dayInfo = new String[lastDay+1][4];


        // figure size of the array
        if (firstDay == 1) {
            days = new String[lastDay + (FIRST_DAY_OF_WEEK * 6)];
        } else {
            days = new String[lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1)];
        }
        int j = FIRST_DAY_OF_WEEK;

        // populate empty days before first real day
        if (firstDay > 1) {
            for (j = 0; j < firstDay - FIRST_DAY_OF_WEEK; j++) {
                days[j] = "";
            }
        } else {
            for (j = 0; j < FIRST_DAY_OF_WEEK * 6; j++) {
                days[j] = "";
            }
            j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
        }

        // populate days
        int dayNumber = 1;
        for (int i = j - 1; i < days.length; i++) {
            days[i] = "" + dayNumber;
            dayNumber++;
        }

    }

    class CalendarNetwork extends AsyncTask<String, Void, String[]> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog= new ProgressDialog(mContext);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String[] params) {

            int today = 0;
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
                    Log.d(MainActivity.TAG, " (화면 2) 전체 메세지 " + line);
                    buffer.append(line);
                }
                Log.d(MainActivity.TAG, "(화면 2) 배열 파싱 시작준비");

                JSONObject root = new JSONObject(buffer.toString());
                JSONArray array = root.getJSONArray("days");
                for (int i = 0; i < array.length(); i++) {
                    try {
                        Log.d(MainActivity.TAG, "(화면 2) 배열 파싱 시작 today is : " + today);
                        JSONObject d = array.getJSONObject(i);
                        dayInfo[today][0] = d.getString("emotion");
                        dayInfo[today][1] = d.getString("comment");
                        Log.d(MainActivity.TAG, "(화면 2) emotion : " + dayInfo[i][0]);
                        Log.d(MainActivity.TAG, "(화면 2) comment : " + dayInfo[i][1]);
                        today++;
                    } catch (Exception e) {
                        Log.d(MainActivity.TAG, "(화면 2) error is " + e);
                        Log.d(MainActivity.TAG, "(화면 2) 안되 today is " + today);
                        today++;
                        dayInfo[today][0] = null;
                        dayInfo[today][1] = null;
                    }
                }
                String user = root.getString("username");
                Log.d(MainActivity.TAG, "username is " + user);
            }catch(Exception e){
                Log.d(MainActivity.TAG, "(화면 2) error2 is " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            parsingOk=true;
            notifyDataSetChanged();
            dialog.dismiss();
        }
    }
    public void LoadCalendar(int monthInt) {

        connectCheck(monthInt);

    }
    public void connectCheck(int monthInt)
    {
        currentMonth = CalendarView.stringMonth(monthInt);
        String month = String.valueOf(++monthInt);
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            //Toast.makeText(this,"네트워크 연결중입니다.", Toast.LENGTH_SHORT).show();

            String urlStr = "http://52.69.116.105:8000/monthly_moment/2015/" + month;
            Log.d(MainActivity.TAG, "(화면 2) URL IS " + urlStr);

            new CalendarNetwork().execute(urlStr);
        } else {
            // display error
            Toast.makeText(mContext, "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
        }
    }



    public void CardDialogShow(int position)
    {
        int firstday=firstDay-2;
        int today = position-firstday;
        Log.d(MainActivity.TAG, "(화면 2) position is " + position);
        Log.d(MainActivity.TAG, "(화면 2) card today is " + today);
        for(int j=0; j<4; j++)
        {
            Log.d(MainActivity.TAG, "(화면 2) card dayInfo is" + dayInfo[today][j]);
        }
        String title = today + "." + currentMonth;
        if(dayInfo[today-1][1]!=null)
        {
            dialog = new CardDialog(mContext, dayInfo[today-1][0], dayInfo[today-1][1], title);

            Log.d(MainActivity.TAG, "(화면 2) card emotion is " + dayInfo[today-1][0] + "card comment is " + dayInfo[today-1][1]);

            dialog.show();
        }
    }



}

