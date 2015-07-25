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
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.mytap.R;
import org.androidtown.mytap.org.androidtown.myStatus.MyStatus;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter {
    static final int FIRST_DAY_OF_WEEK = 0; // Sunday = 0, Monday = 1
    private Context mContext;
    private Calendar month;
    private Calendar selectedDate;
    TextView dayView;

    private ImageView dateImage;
    private ImageView commentImage;
    String TAG = "CALENDAR";
    String TAG2 = "CALENDAR2";
    boolean dayComment;

    public String[] days;



    public CalendarAdapter(Context c, Calendar monthCalendar) {
        month = monthCalendar;
        selectedDate = (Calendar) monthCalendar.clone();
        mContext = c;
        month.set(Calendar.DAY_OF_MONTH, 1);
        refreshDays();
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

        //1부터 끝까지

        if(!days[position].equals(""))
        {
            //LoadCalendar(month.get(Calendar.MONTH), days[position]);
            if(dayComment==true)
            {
                commentImage.setVisibility(View.VISIBLE);
                Log.d(TAG2, "들어왔따");
            }
            dayComment=false;
            Log.d(TAG2, "position is " + days[position]);
        }
        return v;
    }

    public void refreshDays() {
        int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.d(TAG2, "lastDay is " + lastDay);
        int firstDay = (int) month.get(Calendar.DAY_OF_WEEK);
        Log.d(TAG2, "firstDay is " + firstDay);

        // figure size of the array
        if (firstDay == 1) {
            days = new String[lastDay + (FIRST_DAY_OF_WEEK * 6)];
            Log.d(TAG2, "days size is " + days.length);

        } else {
            days = new String[lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1)];
            Log.d(TAG2, "days size is " + days.length);

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
        @Override
        protected String[] doInBackground(String[] params) {
            String urlStr = params[0];
            StringBuffer buffer = new StringBuffer();
            String[] arr = new String[4];
            try {
                URL url = new URL(urlStr);
                InputStream is = url.openConnection().getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d(TAG, " 전체 메세지 " + line);
                    buffer.append(line);
                }
                if(buffer.toString()!=null)
                {
                    JSONObject root = new JSONObject(buffer.toString());
                    String emotion = root.getString("emotion");
                    Log.d(TAG, "emotion : " + emotion);
                    String comment = root.getString("comment");
                    Log.d(TAG, "comment : " + comment);
                    String month = root.getString("month");
                    Log.d(TAG, "month : " + month);
                    String day = root.getString("day");
                    Log.d(TAG, "day : " + day);
                    arr[0] = emotion;
                    arr[1] = comment;
                    arr[2] = month;
                    arr[3] = day;
                    return arr;

                }
            } catch (Exception e) {
                arr[0]=null;
                arr[1]=null;
                arr[2]=null;
                arr[3]=null;

            }
            return arr;
        }

        @Override
        protected void onPostExecute(String[] s) {
            if(s[0]!=null)
            {
                int emoticon = Integer.parseInt(s[0]);
                String comment = s[1];
                if(comment!=null)
                {
                    setDayComment();
                    Log.d(TAG2, "comment is " + comment);
                }
                int month = Integer.parseInt(s[2]);
                int day = Integer.parseInt(s[3]);
                Log.d(TAG2, "emoticon is " + emoticon + " month is " + month + " day is " + day);


            }
        }
    }
    public void setDayComment()
    {
        dayComment=true;
    }


    public void LoadCalendar(int monthInt, String today) {
        String[] day;
        String month = String.valueOf(++monthInt);
        String urlStr = "http://52.69.116.105:8000/load_specific_moment/2015/" + month + "/" + today;
        Log.d(TAG2, "URL IS " + urlStr);
        try {
            day = new CalendarNetwork().execute(urlStr).get();
        } catch (Exception e) {
            day = null;
        }
        if (day[1] != null)
        {
            dateImage.setImageBitmap(MyStatus.items.get(Integer.parseInt(day[0])));
            Log.d(TAG2, "day[0] is " + Integer.parseInt(day[0]));
            commentImage.setVisibility(View.VISIBLE);
        }
        Log.d(TAG2, "GET 한 값 "+ day[0] + day[1] + day[2] + day[3]);
    }

}

