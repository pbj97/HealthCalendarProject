package com.example.foodcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OneDayDBhelper extends SQLiteOpenHelper {
    public OneDayDBhelper(Context context){
        super(context, "OneDayDB", null, 10);
    }
    public void onCreate(SQLiteDatabase db){
        String SQL = "create table if not exists OneDay (" +
                "_id integer primary key autoincrement," +
                "Date," +
                "Breakfast," +
                "Lunch," +
                "Dinner," +
                "Kcal," +
                "Carboh," +
                "Protein," +
                "Fat)";
        db.execSQL(SQL);
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        if(newVersion == 10) {
            db.execSQL("DROP TABLE OneDay");
            onCreate(db);
        }
    }

    public void insertDB(SQLiteDatabase db, String DATE, String BF_MSG, String LU_MSG, String DI_MSG, String KCAL, String CARBOH, String PROTEIN, String FAT){
        ContentValues values = new ContentValues();
        values.put("Date", DATE);
        values.put("Breakfast", BF_MSG);
        values.put("Lunch", LU_MSG);
        values.put("Dinner", DI_MSG);
        values.put("Kcal", KCAL);
        values.put("Carboh", CARBOH);
        values.put("Protein", PROTEIN);
        values.put("Fat", FAT);
        db.insert("OneDay", null, values);
    }
}
