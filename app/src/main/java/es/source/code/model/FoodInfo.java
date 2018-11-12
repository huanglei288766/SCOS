package es.source.code.model;


import java.io.Serializable;

public class FoodInfo implements Serializable {
    int foodType;
    String foodName;
    int foodWorth;
    int store;
    int foodNum;

    public final static String[] titles = {"冷菜", "热菜", "海鲜", "酒水"};

    public FoodInfo(){

    }

    public FoodInfo(int foodType, int foodNum, String foodName, int foodWorth, int store){

        this.foodName = foodName;
        this.foodNum = foodNum;
        this.foodType = foodType;
        this.foodWorth = foodWorth;
        this.store = store;

    }

    public String getFoodName() {
        return foodName;
    }

    public int getPrice() {
        return foodWorth;
    }
    public int getFoodType(){
        return  foodType;
    }

    public int getFoodNum(){
        return foodNum;
    }

    public int getStore(){
        return store;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodWorth() {
        return foodWorth;
    }

    public void setFoodWorth(int foodWorth) {
        this.foodWorth = foodWorth;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public static String[] getTitles() {
        return titles;
    }
}
