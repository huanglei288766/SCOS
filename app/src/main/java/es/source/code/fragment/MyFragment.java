package es.source.code.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity.FoodDetailed;
import es.source.code.model.FoodInfo;
import es.source.code.utils.SingleIntent;
import es.source.code.adapter.MyRecyclerViewAdapter;


@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {
    public final String[] titles = {"冷菜", "热菜", "海鲜", "酒水"};
    private String title;
    Context mContext;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    ArrayList<String> datas;
    SingleIntent singleIntent;
    public List<FoodInfo> foodlist;
    private int[] store = new int[3];


    public MyFragment(String title, ArrayList<String> datas){
        super();
        this.title = title;
        this.datas = datas;
    }

    //创建上下文
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }


    //创建视图
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(mContext);
        return recyclerView;
    }

    //绑定数据
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myRecyclerViewAdapter = new MyRecyclerViewAdapter(mContext, datas,title);
        recyclerView.setAdapter(myRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));



        myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isButton) {

                if (isButton){
                    Button button = v.findViewWithTag(position);
                    button.setText("退菜");
                    Toast.makeText(mContext, "点菜成功", Toast.LENGTH_SHORT).show();
                    singleIntent = SingleIntent.getSingleInstance();
                    singleIntent.addItem(titleToInt(),position);
                }else{
                    Intent intent = new Intent();
                    intent.setClass(mContext, FoodDetailed.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }


    public String getTitle() {
        return title;
    }

    public int titleToInt(){
        int i;
        for (i = 0; i < titles.length; i++){
            if (titles[i].equals(title)){
                break;
            }
        }
        return i;
    }

    public void refreshStore(List<FoodInfo> foodlist){
        Log.d("FoodViewFragment", "获取当前foodList");
        this.foodlist = new ArrayList<>();
        for (int i = 0; i < foodlist.size(); i++){
            if (foodlist.get(i).getFoodType() == titleToInt()){
                this.foodlist.add(foodlist.get(i));
            }
        }
        refresh(this.foodlist);
    }

    public void refresh(List<FoodInfo> foodlist) {
        Log.d("FoodViewFragment", "NotifyChanged");
        for (int i = 0; i < foodlist.size(); i++){
            int num = foodlist.get(i).getFoodNum();
            store[num] = foodlist.get(i).getStore();
            myRecyclerViewAdapter.storeChanged(store);
            myRecyclerViewAdapter.notifyItemChanged(num);
        }
    }

}
