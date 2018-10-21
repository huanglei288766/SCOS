package es.source.code.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import es.source.code.fragment.FoodOrderViewFragment;
import es.source.code.fragment.MyFragment;

public class ViewPagerAdpter extends FragmentPagerAdapter {

    private ArrayList<MyFragment> fragments;

    public ViewPagerAdpter(FragmentManager fm, ArrayList<MyFragment> fragments){
        super(fm);
        this.fragments = fragments;
    }



    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //获得页面标题
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }
}
