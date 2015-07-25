package org.androidtown.mytap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

		public class Fragment02 extends Fragment {
		TextView TextYear;
		TextView TextMonth;
		ArrayList<String> mitems;
		ArrayAdapter<String> mon2;
		View vi;


		@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {

			vi= inflater.inflate(R.layout.frag02, container, false);

			TextYear = (TextView) vi.findViewById(R.id.years);
			TextMonth = (TextView) vi.findViewById(R.id.months);
			mitems = new ArrayList<String>();
			mon2 	= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mitems);

			adapter = new CalendarAdapter(vi.getContext(), mon2);
			dayView = (GridView) vi.findViewById(R.id.grid1);
			gridView = (GridView)vi.findViewById(R.id.grid1);
			mitems.setAdapter(mon2);

			Date date = new Date();

			int year = date.getYear() + 1900;
			final int month = date.getMonth() + 1;

			TextYear.setText(year + "");
			TextMonth.setText(month + "");

			fillDate(year, month);

			Button btn = (Button) vi.findViewById(R.id.click_2);
			btn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					switch (v.getId()) {
						case R.id.click_2:
							try {
								EditText et_year = (EditText) vi.findViewById(R.id.years);
								EditText et_month = (EditText) vi.findViewById(R.id.months);

								int int_year = Integer.parseInt(et_year.getText().toString());
								int int_month = Integer.parseInt(et_month.getText().toString());

								if (int_month > 0 && int_month < 13)

								{


									Toast.makeText(getActivity(), "onclick", Toast.LENGTH_LONG).show();
									mitems.clear();
									mitems.add("일");
									mitems.add("월");
									mitems.add("화");
									mitems.add("수");
									mitems.add("목");
									mitems.add("금");
									mitems.add("토");


									Date current = new Date(int_year - 1900, int_month - 1, 1);
									int day = current.getDay();

									for (int i = 0; i < day; i++)
										mitems.add("");
									current.setDate(32);
									int last = 32 - current.getDate();

									for (int i = 1; i <= last; i++) {
										mitems.add(i + "");
									}

								}

								else if(int_month <1 || int_month>12)
								{
									Toast.makeText(getActivity(), "다시 입력해주세요", Toast.LENGTH_LONG).show();
								}



							} catch (Exception e) {
								Toast.makeText(getActivity(), "error!!", Toast.LENGTH_LONG).show();
								e.printStackTrace();
							}


							break;
					}
				}
			});

			return vi;
		}


		private  void fillDate(int int_year, int int_month)
		{
			mitems.clear();
			mitems.add("일");
			mitems.add("월");
			mitems.add("화");
			mitems.add("수");
			mitems.add("목");
			mitems.add("금");
			mitems.add("토");


			Date current = new Date(int_year - 1900, int_month - 1, 1);
			int day = current.getDay();


			for (int i = 0 ; i < day ; i++)

				mitems.add("");

			current.setDate(32);
			int last = 32 - current.getDate();

			for (int i = 1; i <= last; i++)
			{
				mitems.add(i + "");
			}
		}


			public class GridViewAdapter extends BaseAdapter {
			private ArrayList<String> mon_th;


			@Override
			public int getCount() {
				return mon_th.size();
			}

			@Override
			public Object getItem(int position) {
				return mon_th.get(position);
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return null;
			}
		}


	}

