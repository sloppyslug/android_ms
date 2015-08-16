package org.androidtown.mytap.org.androidtown.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.androidtown.mytap.FirstActivity;
import org.androidtown.mytap.MainActivity;
import org.androidtown.mytap.MySharedPreference;
import org.androidtown.mytap.R;

public class Setting extends Fragment {

    View vi;
    Button logoutButton;
    Button myFriendButton;
    MySharedPreference mySharedPreference;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		Log.d(MainActivity.TAG, "(화면 4) Start");
		vi=inflater.inflate( R.layout.logout_layout, container, false );
        mySharedPreference = new MySharedPreference(getActivity());
        logoutButton = (Button)vi.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySharedPreference.removeIDPreferences();
                mySharedPreference.removePWDPreferences();
                mySharedPreference.removeAllPreferences();
                Intent intent = new Intent(getActivity(), FirstActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        myFriendButton = (Button)vi.findViewById(R.id.myFriend);
        myFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyFriendActivity.class);
                startActivity(intent);
            }
        });

		return vi;
	}
	
}