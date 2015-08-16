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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.androidtown.mytap.R;

import java.util.Calendar;


public class CalendarView extends Fragment {
	GridView dayView;
	DayGridViewAdapter dayAdapter;
	Calendar month;
	CalendarAdapter adapter;
	GridView gridView;
	View vi;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vi = inflater.inflate(R.layout.calendar, container, false);
		month = Calendar.getInstance();

		adapter = new CalendarAdapter(vi.getContext(), month);

		dayView = (GridView) vi.findViewById(R.id.dayView);
		dayView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return true;
				}
				return false;
			}
		});
		dayAdapter = new DayGridViewAdapter(vi.getContext());
		dayView.setAdapter(dayAdapter);


		gridView = (GridView) vi.findViewById(R.id.gridview);
		gridView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return true;
				}
				return false;
			}
		});
		gridView.setAdapter(adapter);

		TextView title = (TextView) vi.findViewById(R.id.textView);
		String monthString = String.valueOf(stringMonth(month.get(Calendar.MONTH)));
		title.setText(monthString + android.text.format.DateFormat.format("  yyyy", month));
		title.setTextSize(18);


		Button previous = (Button) vi.findViewById(R.id.preButton);
		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
					month.set((month.get(Calendar.YEAR) - 1), month.getActualMaximum(Calendar.MONTH), 1);
				} else {
					month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
				}
				refreshCalendar();
			}
		});


		Button next = (Button) vi.findViewById(R.id.toButton);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
					month.set((month.get(Calendar.YEAR) + 1), month.getActualMinimum(Calendar.MONTH), 1);
				} else {
					month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
				}
				refreshCalendar();
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//기록보여주고
				adapter.CardDialogShow(position);
			}
		});

		return vi;
	}

	public void refreshCalendar() {
		TextView title = (TextView) vi.findViewById(R.id.textView);
		adapter = new CalendarAdapter(vi.getContext(), month);
		gridView.setAdapter(adapter);
		String monthString = String.valueOf(stringMonth(month.get(Calendar.MONTH)));
		title.setText(monthString + android.text.format.DateFormat.format("  yyyy", month));
		title.setTextSize(18);
	}

	public static String stringMonth(int month)
	{
		switch(month){
			case 0:
				return "JANUARY";
			case 1:
				return "FEBRUARY";
			case 2:
				return "MARCH";
			case 3:
				return "APRIL";
			case 4:
				return "MAY";
			case 5:
				return "JUNE";
			case 6:
				return "JULY";
			case 7:
				return "AUGUST";
			case 8:
				return "SEPTEMBER";
			case 9:
				return "OCTOBER";
			case 10:
				return "NOVEMBER";
			case 11:
				return "DECEMBER";
			default:
				return "what";
		}
	}
}

