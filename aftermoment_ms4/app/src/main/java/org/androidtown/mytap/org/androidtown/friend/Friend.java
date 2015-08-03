package org.androidtown.mytap.org.androidtown.friend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.R;

public class Friend extends Fragment implements AdapterView.OnItemClickListener{

	View vi;
	GridView gridView;
	GridViewAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		Log.d(MainActivity.TAG, "(화면 3) friend start");
		vi = inflater.inflate( R.layout.friend_activity, container, false );
		gridView = (GridView) vi.findViewById(R.id.gridView);
		adapter = new GridViewAdapter(getActivity());
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);

		return vi;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		adapter.CardDialogShow(position);
	}
}