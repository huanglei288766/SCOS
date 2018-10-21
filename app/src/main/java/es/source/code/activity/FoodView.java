package es.source.code.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import es.source.code.adapter.ViewPagerAdpter;
import es.source.code.fragment.MyFragment;

public class FoodView extends AppCompatActivity {

    static String[] titles = {"冷菜","热菜","海鲜","酒水"};
    ArrayList<String> datas;
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<MyFragment> fragments;
    ViewPagerAdpter viewPagerAdpter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        viewPager = findViewById(R.id.FoodViewPager);
        tabLayout = findViewById(R.id.FoodTabLayout);

        initFragments();
        initViewPager();
    }

    /**
     * ActionBar定义
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * ActionBar 点击事件
     * @param item 包括已点菜品
     * @return      未知
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ordered:
                try {
                    intent = new Intent();
                    intent.setClass(FoodView.this, FoodOrderView.class);
                    FoodView.this.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(FoodView.this, e.getMessage(), Toast.LENGTH_LONG);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * FoodList TextView datas 初始化
     * @param foodType          0-3
     * @param foodTypeLentth    每种菜的长度
     * @return
     */
    private static ArrayList<String> foodDataInit(int foodType, int foodTypeLentth){
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < foodTypeLentth; i++){
            datas.add(titles[foodType]+i+"\t"+"价格："+10*(i+1));
        }
        return datas;
    }

    private void  initFragments(){
        fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            datas = foodDataInit(i,3);
            fragments.add(new MyFragment(titles[i], datas));
        }
    }

    private void initViewPager(){
        viewPagerAdpter = new ViewPagerAdpter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdpter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
