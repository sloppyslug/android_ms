package org.androidtown.mytap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.androidtown.mytap.org.androidtown.calendar.CalendarView;
import org.androidtown.mytap.org.androidtown.friend.Fragment03;
import org.androidtown.mytap.org.androidtown.myStatus.MyStatus;
import org.androidtown.mytap.org.androidtown.setting.Fragment04;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends FragmentActivity {

    MaterialTabHost tabHost;
    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;


    public View loginView;





    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                frag = new Fragment03();
            } else if (index==3)
            {
                frag = new Fragment04();
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
                    return "MY FRIEND";
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
