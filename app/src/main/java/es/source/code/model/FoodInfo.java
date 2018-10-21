package es.source.code.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FoodInfo {
    int foodType;
    String foodName;
    int foodWorth;

    public final static String[] titles = {"冷菜", "热菜", "海鲜", "酒水"};

    public FoodInfo(int foodType, String foodName, int foodWorth){
        this.foodName = foodName;
        this.foodType = foodType;
        this.foodWorth = foodWorth;
    }

}
