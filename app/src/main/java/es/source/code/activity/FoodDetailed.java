package es.source.code.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import es.source.code.adapter.ViewPagerAdapterFoodDetail;
import es.source.code.adapter.ViewPagerAdpter;
import es.source.code.fragment.FoodDetailedFragment;
import es.source.code.fragment.MyFragment;

public class FoodDetailed extends AppCompatActivity {

    int image;
    ArrayList<FoodDetailedFragment> fragments;
    String data;
    static String[] titles = {"冷菜","热菜","海鲜","酒水"};

    ViewPager viewPager;

    ViewPagerAdapterFoodDetail viewPagerAdapterFoodDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detailed);

        viewPager = findViewById(R.id.viewPagerFoodDetailed);
        image = R.drawable.account;

        fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++){
            for (int j = 0; j < 3; j++){
                data = titles[i] + j + "\n" + "单价：" + (j+1)* 10 ;
                fragments.add(new FoodDetailedFragment(titles[i], data, image, i, j));
            }
        }

        viewPagerAdapterFoodDetail = new ViewPagerAdapterFoodDetail(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdapterFoodDetail);

    }
}
