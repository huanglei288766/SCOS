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

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener{

    private Context context;
    private ArrayList<String> datas;
    private String data;
    String title;
    private OnRecyclerViewItemClickListener myOnRecyclerViewItemClickListener = null;


    public MyRecyclerViewAdapter(Context context, ArrayList datas, String title){
        this.context = context;
        this.datas = datas;
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
        data = datas.get(i);
        myViewHolder.foodAndWorth.setText(data);
        if (title.equals("未下订单")){
            myViewHolder.chase.setText("退菜");
        }
        myViewHolder.chase.setTag(i);
        myViewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (myOnRecyclerViewItemClickListener != null) {
            switch (v.getId()) {
                case R.id.chase:
                    myOnRecyclerViewItemClickListener.onClick(v, position, true);
                    break;
                default:
                    myOnRecyclerViewItemClickListener.onClick(v, position, true);
                    break;
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView foodAndWorth;
        Button chase;
        public MyViewHolder(View itemView) {
            super(itemView);
            chase = itemView.findViewById(R.id.chase);
            foodAndWorth = itemView.findViewById(R.id.FoodAndWorth);
            itemView.setOnClickListener(MyRecyclerViewAdapter.this);
            chase.setOnClickListener(MyRecyclerViewAdapter.this);
        }
    }

    public interface OnRecyclerViewItemClickListener{
        void onClick(View v, int position, boolean isButton);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.myOnRecyclerViewItemClickListener = listener;
    }


}