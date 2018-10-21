package es.source.code.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.source.code.activity.R;
import es.source.code.activity.SingleIntent;


@SuppressLint("ValidFragment")
public class FoodDetailedFragment extends Fragment {
    public final String[] titles = {"冷菜", "热菜", "海鲜", "酒水"};
    private String title;
    Context mContext;

    ViewPager viewPager;

    ImageView imageView;
    TextView textView;
    Button button;
    EditText editText;

    View view;
    int image;
    String data;

    int type;
    int num;

    SingleIntent singleIntent;




    public FoodDetailedFragment(String title, String data, int image, int type, int num){
        super();
        this.title = title;
        this.data = data;
        this.image = image;
        this.type = type;
        this.num = num;
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
        view = inflater.inflate(R.layout.food_detailed_fragment, container, false);
        return view;
    }

    //绑定数据
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView3);
        button = view.findViewById(R.id.button2);
        editText = view.findViewById(R.id.editText);
        singleIntent = SingleIntent.getSingleInstance();



        imageView.setImageResource(R.drawable.cold);
        textView.setText(data);

        if (singleIntent.itemList[type][num] > 0){
            button.setText("退菜");
        }else{
            button.setText("点菜");
        }

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
}
