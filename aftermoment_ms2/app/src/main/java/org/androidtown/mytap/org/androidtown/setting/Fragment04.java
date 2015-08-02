package org.androidtown.mytap.org.androidtown.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.R;

public class Fragment04 extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		Log.d(MainActivity.TAG, "(화면 4) Start");
		return inflater.inflate( R.layout.frag04, container, false );
	}
	
}