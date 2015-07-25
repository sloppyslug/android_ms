package org.androidtown.mytap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;

import org.androidtown.mytap.org.androidtown.calendar.CalendarView;
import org.androidtown.mytap.org.androidtown.friend.Fragment03;
import org.androidtown.mytap.org.androidtown.myStatus.MyStatus;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends FragmentActivity {

    MaterialTabHost tabHost;
    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int sum = outMetrics.densityDpi;
        Log.d("dpi", "dpi : " + sum);
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

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

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
                frag = new Fragment03();

            }

            return frag;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CALENDAR";
                case 1:
                    return "HOME";
                case 2:
                    return "MY FRIEND";
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
