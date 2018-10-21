package es.source.code.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import es.source.code.fragment.FoodDetailedFragment;

public class ViewPagerAdapterFoodDetail extends FragmentPagerAdapter {

    private ArrayList<FoodDetailedFragment> fragments;

    public ViewPagerAdapterFoodDetail(FragmentManager fm, ArrayList<FoodDetailedFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }



    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
