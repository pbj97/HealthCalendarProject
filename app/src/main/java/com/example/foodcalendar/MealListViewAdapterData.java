package com.example.foodcalendar;

public class MealListViewAdapterData {
    private String FoodName;
    private String FoodKcal;
    private String FoodCarboh;
    private String FoodProtein;
    private String FoodFat;

    public void setFoodName(String name){
        this.FoodName = name;
    }
    public void setFoodKcal(String Kcal){
        this.FoodKcal = Kcal;
    }
    public void setFoodCarboh(String Carboh){
        this.FoodCarboh = Carboh;
    }
    public void setFoodProtein(String Protein){
        this.FoodProtein = Protein;
    }
    public void setFoodFat(String Fat){
        this.FoodFat = Fat;
    }

    public String getFoodName(){
        return FoodName;
    }
    public String getFoodKcal(){
        return FoodKcal;
    }
    public String getFoodCarboh(){
        return FoodCarboh;
    }
    public String getFoodProtein(){
        return  FoodProtein;
    }
    public String getFoodFat(){ return FoodFat; }

    public String getFoodKcalToInt(){
        int temp = (int)Double.parseDouble(FoodKcal);
        return Integer.toString(temp);
    }
    public String getFoodCarbohToInt(){
        int temp = (int)Double.parseDouble(FoodCarboh);
        return Integer.toString(temp);
    }
    public String getFoodProteinToInt(){
        int temp = (int)Double.parseDouble(FoodProtein);
        return Integer.toString(temp);
    }
    public String getFoodFatToInt(){
        int temp = (int)Double.parseDouble(FoodFat);
        return Integer.toString(temp);
    }



    MealListViewAdapterData(String name, String kcal, String carboh, String protein, String fat){
        this.FoodName = name;
        this.FoodKcal = kcal;
        this.FoodCarboh = carboh;
        this.FoodProtein = protein;
        this.FoodFat = fat;
    }
}
