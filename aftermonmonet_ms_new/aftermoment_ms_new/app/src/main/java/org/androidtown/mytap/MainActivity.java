package org.androidtown.mytap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import org.androidtown.mytap.org.androidtown.calendar.CalendarView;
import org.androidtown.mytap.org.androidtown.friend.Friend;
import org.androidtown.mytap.org.androidtown.myStatus.MyStatus;
import org.androidtown.mytap.org.androidtown.setting.Setting;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends FragmentActivity {

    MaterialTabHost tabHost;
    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;
    public static final String TAG = "AfterMoment";

    String mySessionId;
    MySharedPreference mySharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirstActivity activity = (FirstActivity)FirstActivity.activity;
        activity.finish();
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        mySessionId = intent.getStringExtra("mySessionId");
        Log.d(MainActivity.TAG, "(화면1) MYSESSION ID IS " + mySessionId);
        mySharedPreference = new MySharedPreference(this);
        mySharedPreference.savePreferences(mySessionId);

        tabHost = (MaterialTabHost) findViewById(R.id.tabHost);
        viewPager = (ViewPager) findViewById(R.id.pager);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            MaterialTab tab = tabHost.newTab();
            tab.setText(pagerAdapter.getPageTitle(i));
            tab.setTabListener(new ProductTabListener());
            tabHost.addTab(tab);
        }

        tabHost.setSelectedNavigationItem(1);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int index) {
            Fragment frag = null;

            if (index == 0) {
                frag = new CalendarView();
            } else if (index == 1) {
                frag = new MyStatus();
            } else if (index == 2) {
                frag = new Friend();
            } else if (index==3) {
                frag = new Setting();
            }

            return frag;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CALENDAR";
                case 1:
                    return "HOME";
                case 2:
                    return "FRIEND";
                case 3:
                    return "SETTING";
                default:
                    return null;
            }
        }

    }

    private class ProductTabListener implements MaterialTabListener {

        public ProductTabListener() {

        }

        @Override
        public void onTabSelected(MaterialTab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabReselected(MaterialTab tab) {

        }

        @Override
        public void onTabUnselected(MaterialTab tab) {

        }

    }
}
