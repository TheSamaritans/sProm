package com.example.hbsha.sprom;
/**
 * Created by Bhuvie.
 */

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;


public class viewkbBoard extends Fragment{
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;
    ProgressBar pbar;
    todoFragment fragobj1;inprogressFragment fragobj2;doneFragment fragobj3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        fragobj1 = new todoFragment();
        fragobj1.setArguments(bundle);
        fragobj2 = new inprogressFragment();
        fragobj2.setArguments(bundle);
        fragobj3 = new doneFragment();
        fragobj3.setArguments(bundle);
        View x =  inflater.inflate(R.layout.activity_viewkb_board,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
       // tabLayout.setupWithViewPager(viewPager);
        pbar=(ProgressBar)x.findViewById(R.id.progressBarsprint);
        DBMgr DBInstance=DBMgr.GetInstance();
        int prgs=DBInstance.RetrieveProgress(bundle.getString("sprintid"));
        pbar.setProgress(prgs);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return x;
    }


    /*private void setupWithViewPager(ViewPager viewPager) {
        AdapterView adapter = new AdapterView(getFragmentManager());
        getFragmentManager();
        adapter.addFragment(new todoFragment(), "ToDo");
        adapter.addFragment(new inprogressFragment(), "InProgress");
        adapter.addFragment(new doneFragment(), "Done");
        viewPager.setAdapter(adapter);
    }*/


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return fragobj1;
                case 1 : return fragobj2;
                case 2 : return fragobj3;
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }


        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "ToDo";
                case 1 :
                    return "InProgress";
                case 2 :
                    return "Done";
            }
            return null;
        }
    }

}
