package es.source.code.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import es.source.code.adapter.ViewPagerAdpter;
import es.source.code.adapter.ViewPagerAdpterFoodOrder;
import es.source.code.fragment.FoodOrderViewFragment;
import es.source.code.fragment.MyFragment;

public class FoodOrderView extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    String[] FoodOrderedTitles = {"未下订单", "已下订单"};

    ArrayList<FoodOrderViewFragment> fragments = new ArrayList<>();
    ViewPagerAdpterFoodOrder viewPagerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);

        viewPager = findViewById(R.id.foodOrderViewPager);
        tabLayout = findViewById(R.id.foodOrderViewTabLayout);
        initViewPager();
    }

    public void initFragments(){

        FoodOrderViewFragment fragment1 = new FoodOrderViewFragment(FoodOrderedTitles[0]);
        FoodOrderViewFragment fragment2 = new FoodOrderViewFragment(FoodOrderedTitles[1]);

        fragments.add(fragment1);
        fragments.add(fragment2);

        viewPagerAdpter = new ViewPagerAdpterFoodOrder(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdpter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void initViewPager(){
        initFragments();
    }
}
