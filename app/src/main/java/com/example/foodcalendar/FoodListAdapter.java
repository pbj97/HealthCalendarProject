package com.example.foodcalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodListAdapter extends BaseAdapter {
    ArrayList<MealListViewAdapterData> items = new ArrayList<MealListViewAdapterData>();
    Context context;

    public void clear(){ items.clear(); }

    public int getCount(){
        return items.size();
    }

    public Object getItem(int position){
        return items.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public String getFname(int i)
    {
        MealListViewAdapterData Data = items.get(i);
        return Data.getFoodName();
    }

    public String getFKcal(int i)
    {
        MealListViewAdapterData Data = items.get(i);
        return Data.getFoodKcal();
    }

    public String getFCarboh(int i)
    {
        MealListViewAdapterData Data = items.get(i);
        return Data.getFoodCarboh();
    }

    public String getFProtein(int i)
    {
        MealListViewAdapterData Data = items.get(i);
        return Data.getFoodProtein();
    }

    public String getFFat(int i)
    {
        MealListViewAdapterData Data = items.get(i);
        return Data.getFoodFat();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        context = parent.getContext();
        MealListViewAdapterData listitem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.food_info2, parent, false);
        }


        TextView tvName = convertView.findViewById(R.id.FoodNameText);
        TextView tvKcal = convertView.findViewById(R.id.FoodKcalText);
        TextView tvCarboh = convertView.findViewById(R.id.FoodCarbohText);
        TextView tvProtein = convertView.findViewById(R.id.FoodProteinText);
        TextView tvFat = convertView.findViewById(R.id.FoodFatText);

        tvName.setText(listitem.getFoodName());
        tvKcal.setText(listitem.getFoodKcal());
        tvCarboh.setText(listitem.getFoodCarboh());
        tvProtein.setText(listitem.getFoodProtein());
        tvFat.setText(listitem.getFoodFat());



        return convertView;
    }



    public void addItem(MealListViewAdapterData item){
        items.add(item);
    }
}

