package es.source.code.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import es.source.code.fragment.FoodOrderViewFragment;
import es.source.code.fragment.MyFragment;

public class ViewPagerAdpterFoodOrder extends FragmentPagerAdapter {

    private ArrayList<FoodOrderViewFragment> fragments;

    public ViewPagerAdpterFoodOrder(FragmentManager fm, ArrayList<FoodOrderViewFragment> fragments){
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
