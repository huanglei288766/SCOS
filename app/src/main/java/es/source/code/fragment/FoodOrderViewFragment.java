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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import es.source.code.activity.FoodDetailed;
import es.source.code.activity.R;
import es.source.code.utils.MyAsyncTask;
import es.source.code.utils.SingleIntent;
import es.source.code.adapter.MyRecyclerViewAdapterFoodOrderView;

@SuppressLint("ValidFragment")
public class FoodOrderViewFragment extends Fragment {
    private String title;
    Context context;

    int[] foodSum= {0, 0};
    int[] worthSum = {0,0};

    RecyclerView recyclerView;
    Button orderButton;
    TextView foodSumTextView;
    TextView worthSumTextView;
    ProgressBar progressBar;

    MyRecyclerViewAdapterFoodOrderView myRecyclerViewAdapter;
    SingleIntent singleInstance;

    View view;

    public FoodOrderViewFragment(String title){
        super();
        this.title = title;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.food_order_view_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.foodOrderViewRecycler);
        foodSumTextView = view.findViewById(R.id.foodSum);
        worthSumTextView = view.findViewById(R.id.worthSum);
        orderButton = view.findViewById(R.id.orderButton);
        progressBar = view.findViewById(R.id.progressBarFoodOrderView);
        progressBar.setVisibility(View.INVISIBLE);

        calFoodSumAndWorthSum(foodSumTextView, worthSumTextView);

        if (title.equals("未下订单")){
            orderButton.setText("提交订单");
        }else{
            orderButton.setText("结账");
        }

        initRecyclerView();

    }

    public String getTitle() {
        return title;
    }

    private void calFoodSumAndWorthSum(TextView foodSumTextView, TextView worthSumTextView){
        foodSum[0] = 0;
        foodSum[1] = 0;
        worthSum[0] = 0;
        worthSum[1] = 0;
        singleInstance = SingleIntent.getSingleInstance();
        for (int type = 0; type < 4; type ++){
            for (int num = 0; num < 20; num++){
                foodSum[0] += singleInstance.itemList[type][num];
                foodSum[1] += singleInstance.paidList[type][num];
                if (singleInstance.itemList[type][num] > 0){
                    worthSum[0] += (num + 1) * 10 * singleInstance.itemList[type][num];
                }
                if (singleInstance.paidList[type][num] > 0){
                    worthSum[1] += (num+1) * 10 * singleInstance.itemList[type][num];
                }
            }
        }
        if (getTitle().equals("未下订单")) {
            foodSumTextView.setText("菜品总数：" + foodSum[0]);
            worthSumTextView.setText("菜品总价：" + worthSum[0]);
        }else{
            foodSumTextView.setText("菜品总数：" + foodSum[1]);
            worthSumTextView.setText("菜品总价：" + worthSum[1]);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            if (recyclerView != null){
            initRecyclerView();
            }
    }

    public void initRecyclerView(){

        recyclerView = view.findViewById(R.id.foodOrderViewRecycler);
        foodSumTextView = view.findViewById(R.id.foodSum);
        worthSumTextView = view.findViewById(R.id.worthSum);
        orderButton = view.findViewById(R.id.orderButton);

        myRecyclerViewAdapter = new MyRecyclerViewAdapterFoodOrderView(context, title);

        recyclerView.setAdapter(myRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));

        myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapterFoodOrderView.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isButton) {
                if (isButton){
                    singleInstance = SingleIntent.getSingleInstance();
                    try{
                        Toast.makeText(context, "将退第"+position+"项",Toast.LENGTH_SHORT).show();

                        myRecyclerViewAdapter.tuiCai(position);

                        calFoodSumAndWorthSum(foodSumTextView, worthSumTextView);
                    }catch (Exception e){
                        Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent intent = new Intent();
                    intent.setClass(context, FoodDetailed.class);
                    context.startActivity(intent);
                }
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleInstance = SingleIntent.getSingleInstance();
                if (((Button) v).getText().equals("提交订单")) {
                    try {
                        myRecyclerViewAdapter.tiJiao();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
                    }
                    Toast.makeText(context, "已提交", Toast.LENGTH_LONG).show();
                }else{
                    MyAsyncTask myAsyncTask = new MyAsyncTask(context, progressBar, orderButton);
                    myAsyncTask.execute();
                }
            }
        });

    }


}
