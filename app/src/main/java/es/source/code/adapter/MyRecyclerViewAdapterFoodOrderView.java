package es.source.code.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import es.source.code.activity.R;
import es.source.code.activity.SingleIntent;

public class MyRecyclerViewAdapterFoodOrderView extends RecyclerView.Adapter<MyRecyclerViewAdapterFoodOrderView.MyViewHolder> implements View.OnClickListener{

    static String[] titles = {"冷菜","热菜","海鲜","酒水"};
    private Context context;
    String data;
    String title;
    private OnRecyclerViewItemClickListener myOnRecyclerViewItemClickListener = null;
    static int flag;
    static int type;
    static int num;
    static SingleIntent singleIntent = SingleIntent.getSingleInstance();


    public MyRecyclerViewAdapterFoodOrderView(Context context, String title){
        this.context = context;
        this.title = title;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.item_recyclerview, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        singleIntent = SingleIntent.getSingleInstance();
        findItemInItemListOnCreate(i);
        data = titles[type] + num +"\n"+singleIntent.itemList[type][num]+"份";
        myViewHolder.foodAndWorth.setText(data);
        if (title.equals("未下订单")){
            myViewHolder.chase.setText("退菜");
        }else {
            myViewHolder.chase.setVisibility(View.INVISIBLE);
        }
        myViewHolder.chase.setTag(titles[type] + num);
        singleIntent.positionChanged(i, true);
    }

    @Override
    public int getItemCount() {
        int k = 0;
        singleIntent = SingleIntent.getSingleInstance();
        for(type = 0; type < 4; type++){
            for (num = 0; num < 20; num++){
                if (singleIntent.itemList[type][num] > 0){
                    k++;
                }
            }
        }
        return k;
    }

    @Override
    public void onClick(View v) {
        String typeAndNum = (String) v.getTag();
        findItemInItemList(typeAndNum);
        int index = findIndexWithPosition();
        if (myOnRecyclerViewItemClickListener != null) {
            switch (v.getId()) {
                case R.id.chase:
                    myOnRecyclerViewItemClickListener.onClick(v, index, true);
                    break;
                default:
                    myOnRecyclerViewItemClickListener.onClick(v, index, false);
                    break;
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView foodAndWorth;
        Button chase;

         MyViewHolder(View itemView) {
            super(itemView);
            chase = itemView.findViewById(R.id.chase);
            foodAndWorth = itemView.findViewById(R.id.FoodAndWorth);
            itemView.setOnClickListener(MyRecyclerViewAdapterFoodOrderView.this);
            chase.setOnClickListener(MyRecyclerViewAdapterFoodOrderView.this);
        }
    }

    public interface OnRecyclerViewItemClickListener{
        void onClick(View v, int position, boolean isButton);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.myOnRecyclerViewItemClickListener = listener;
    }

    private void findItemInItemList(String typeAndNum){
        for (type = 0; type < 4; type ++){
            for (num = 0; num < 20; num++){
                if ((titles[type] + num).equals(typeAndNum)){
                    return ;
                }
            }
        }
        return;
    }

    private void findItemInItemListOnCreate(int position){
        singleIntent = SingleIntent.getSingleInstance();
        int flag = -1;
        for (type = 0; type < 4; type++)
            for (num = 0; num < 20; num++) {
                if (singleIntent.itemList[type][num] != 0) flag++;
                if (flag == position) return;
            }
    }

    private int findIndexWithPosition(){
        singleIntent = SingleIntent.getSingleInstance();
        int flag = -1;
        for (int i = 0; i < type; i++){
            for (int j = 0; j < 20; j++){
                if (singleIntent.itemList[i][j] > 0){
                    flag ++;
                }
            }
        }
        for (int j = 0; j <= num; j++){
            if (singleIntent.itemList[type][j] > 0){
                flag++;
            }
        }
        return flag;
    }

    public void tuiCai(int position){
        findItemInItemListOnCreate(position);
        singleIntent = SingleIntent.getSingleInstance();
        singleIntent.itemList[type][num] --;
        if (singleIntent.itemList[type][num] == 0){
            notifyItemRemoved(position);
        }
    }

    public void tiJiao(){
        singleIntent = SingleIntent.getSingleInstance();
        notifyItemRangeRemoved(0,getItemCount());
        singleIntent.moveList();
    }


}