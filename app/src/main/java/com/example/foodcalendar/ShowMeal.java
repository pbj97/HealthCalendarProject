package com.example.foodcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Timer;
import java.util.TimerTask;

public class ShowMeal extends AppCompatActivity {

    public static final String FNAME = "FNMSG";
    public static final String FKCAL = "FKMSG";
    public static final String FCARBOH = "FCMSG";
    public static final String FPROTEIN = "FPMSG";
    public static final String FFAT = "FFMSG";

    public static final String BF_MSG = "BFMSG";
    public static final String LU_MSG = "LUMSG";
    public static final String DI_MSG = "DIMSG";
    public static final String ALL_KCAL = "AKMSG";
    public static final String ALL_CARBOH = "ACMSG";
    public static final String ALL_PROTEIN = "APMSG";
    public static final String ALL_FAT = "AFMSG";

    private Timer timer;

    ListView Breakfastlist;
    ListView Lunchlist;
    ListView Dinnerlist;

    Button addBreakfastButton;
    Button addLunchButton;
    Button addDinnerButton;
    MealListViewAdapter Breakfastadapter;
    MealListViewAdapter Lunchadapter;
    MealListViewAdapter Dinneradapter;
    TextView Bf_name;
    TextView Bf_All_data;
    TextView Lu_name;
    TextView Lu_All_data;
    TextView Di_name;
    TextView Di_All_data;
    Button Delete;
    Button ResultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_meal);


        Breakfastlist = findViewById(R.id.Breakfastlist);
        Lunchlist = findViewById(R.id.Lunchlist);
        Dinnerlist = findViewById(R.id.Dinnerlist);

        addBreakfastButton = findViewById(R.id.addBreakfastButton);
        addLunchButton = findViewById(R.id.addLunchButton);
        addDinnerButton = findViewById(R.id.addDinnerButton);

        Bf_name = findViewById(R.id.Bf_name);
        Bf_All_data = findViewById(R.id.Bf_All_data);
        Di_name = findViewById(R.id.Di_name);
        Di_All_data = findViewById(R.id.Di_All_Data);
        Lu_name = findViewById(R.id.Lu_name);
        Lu_All_data = findViewById(R.id.Lu_All_Data);

        Delete = findViewById(R.id.Delete_food);
        ResultButton = findViewById(R.id.ResultButton);

        Breakfastadapter = new MealListViewAdapter();
        Lunchadapter = new MealListViewAdapter();
        Dinneradapter = new MealListViewAdapter();


        addBreakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodData.class);
                startActivityForResult(intent, 1);
            }
        });

        addLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodData.class);
                startActivityForResult(intent, 10);
            }
        });

        addDinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodData.class);
                startActivityForResult(intent, 100);
            }
        });

        ResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                int q;
                int w;
                int e;
                int r;
                int k = 0;
                int carboh = 0;
                int protein = 0;
                int fat = 0;
                String a = Bf_name.getText().toString();
                String b = Lu_name.getText().toString();
                String c = Di_name.getText().toString();
                int count = Breakfastadapter.getCount();
                if (count >= 1) {
                    for (int i = 0; i < count; i++) {
                        q = Integer.parseInt(Breakfastadapter.getFKcal(i));
                        k = k + q;
                        w = Integer.parseInt(Breakfastadapter.getFCarboh(i));
                        carboh = carboh + w;
                        e = Integer.parseInt(Breakfastadapter.getFProtein(i));
                        protein = protein + e;
                        r = Integer.parseInt(Breakfastadapter.getFFat(i));
                        fat = fat + r;
                    }
                }
                count = Lunchadapter.getCount();
                if (count >= 1) {
                    for (int i = 0; i < count; i++) {
                        q = Integer.parseInt(Lunchadapter.getFKcal(i));
                        k = k + q;
                        w = Integer.parseInt(Lunchadapter.getFCarboh(i));
                        carboh = carboh + w;
                        e = Integer.parseInt(Lunchadapter.getFProtein(i));
                        protein = protein + e;
                        r = Integer.parseInt(Lunchadapter.getFFat(i));
                        fat = fat + r;
                    }
                }
                count = Dinneradapter.getCount();
                if (count >= 1) {
                    for (int i = 0; i < count; i++) {
                        q = Integer.parseInt(Dinneradapter.getFKcal(i));
                        k = k + q;
                        w = Integer.parseInt(Dinneradapter.getFCarboh(i));
                        carboh = carboh + w;
                        e = Integer.parseInt(Dinneradapter.getFProtein(i));
                        protein = protein + e;
                        r = Integer.parseInt(Dinneradapter.getFFat(i));
                        fat = fat + r;
                    }
                }
                String t = Integer.toString(k);
                String y = Integer.toString(carboh);
                String u = Integer.toString(protein);
                String p = Integer.toString(fat);
                intent.putExtra(BF_MSG, a);
                intent.putExtra(LU_MSG, b);
                intent.putExtra(DI_MSG, c);
                intent.putExtra(ALL_KCAL, t);
                intent.putExtra(ALL_CARBOH, y);
                intent.putExtra(ALL_PROTEIN, u);
                intent.putExtra(ALL_FAT, p);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

        if (requestcode == 1) {
            if (resultcode == RESULT_OK) {
                Intent intent = getIntent();
                String n = data.getStringExtra(FNAME);
                String k = data.getStringExtra(FKCAL);
                String c = data.getStringExtra(FCARBOH);
                String p = data.getStringExtra(FPROTEIN);
                String f = data.getStringExtra(FFAT);
                Breakfastadapter.addItem(new MealListViewAdapterData(n, k, c, p, f));
                Breakfastlist.setAdapter(Breakfastadapter);
                Bffood();
                BfData();
                Timer timer1 = new Timer();
                TimerTask TT = new TimerTask() {
                    @Override
                    public void run() {
                        Bffood();
                        BfData();
                    }
                };
                timer1.schedule(TT, 0, 1000);
            }
        }
        if (requestcode == 10) {
                if (resultcode == RESULT_OK) {
                    Intent intent = getIntent();
                    String n = data.getStringExtra(FNAME);
                    String k = data.getStringExtra(FKCAL);
                    String c = data.getStringExtra(FCARBOH);
                    String p = data.getStringExtra(FPROTEIN);
                    String f = data.getStringExtra(FFAT);
                    Lunchadapter.addItem(new MealListViewAdapterData(n, k, c, p, f));
                    Lunchlist.setAdapter(Lunchadapter);
                    Timer timer2 = new Timer();
                TimerTask AA = new TimerTask() {
                    @Override
                    public void run() {
                        Lufood();
                        LuData();
                    }
                };
                timer2.schedule(AA, 0, 1000);
                Lufood();
                LuData();
            }
        }

        if (requestcode == 100) {
            if (resultcode == RESULT_OK) {
                Intent intent = getIntent();
                String n = data.getStringExtra(FNAME);
                String k = data.getStringExtra(FKCAL);
                String c = data.getStringExtra(FCARBOH);
                String p = data.getStringExtra(FPROTEIN);
                String f = data.getStringExtra(FFAT);
                Dinneradapter.addItem(new MealListViewAdapterData(n, k, c, p, f));
                Dinnerlist.setAdapter(Dinneradapter);
                Timer timer3 = new Timer();
                TimerTask BB = new TimerTask() {
                    @Override
                    public void run() {
                        Difood();
                        DiData();
                    }
                };
                timer3.schedule(BB, 0, 1000);
                Difood();
                DiData();
            }
        }
    }


    public void Bffood() {
        int count = Breakfastadapter.getCount();

        if (count == 0) {
            Bf_name.setText("");
        } else {
            if (count == 1) {
                Bf_name.setText(Breakfastadapter.getFname(count - 1));
            } else {
                String a = Breakfastadapter.getFname(0);
                for (int i = 1; i < count; i++) {
                    a = a + (", " + Breakfastadapter.getFname(i));
                }
                Bf_name.setText(a);
            }
        }
    }

    public void Lufood() {
        int count = Lunchadapter.getCount();
        if (count == 0) {
            Lu_name.setText("");
        } else {
            if (count == 1) {
                Lu_name.setText(Lunchadapter.getFname(count - 1));
            } else {
                String a = Lunchadapter.getFname(0);
                for (int i = 1; i < count; i++) {
                    a = a + (", " + Lunchadapter.getFname(i));
                }
                Lu_name.setText(a);
            }
        }
    }

    public void Difood() {
        int count = Dinneradapter.getCount();
        if (count == 0) {
            Di_name.setText("");
        } else {
            if (count == 1) {
                Di_name.setText(Dinneradapter.getFname(count - 1));
            } else {
                String a = Dinneradapter.getFname(0);
                for (int i = 1; i < count; i++) {
                    a = a + (", " + Dinneradapter.getFname(i));
                }
                Di_name.setText(a);
            }
        }
    }

    public void BfData() {
        int count = Breakfastadapter.getCount();
        int a;
        int b;
        int c;
        int d;
        int k = 0;
        int carboh = 0;
        int protein = 0;
        int fat = 0;

        if (count >= 1) {
            for (int i = 0; i < count; i++) {
                a = Integer.parseInt(Breakfastadapter.getFKcal(i));
                k = k + a;
                b = Integer.parseInt(Breakfastadapter.getFCarboh(i));
                carboh = carboh + b;
                c = Integer.parseInt(Breakfastadapter.getFProtein(i));
                protein = protein + c;
                d = Integer.parseInt(Breakfastadapter.getFFat(i));
                fat = fat + d;
            }
            Bf_All_data.setText("칼로리: " + k + "Kcal 탄수화물: " + carboh + "g 단백질: " + protein + "g 지방: " + fat + "g");
        } else {
            Bf_All_data.setText("칼로리: 0Kcal 탄수화물: 0g 단백질: 0g 지방: 0g");
        }
    }

    public void LuData() {
        int count = Lunchadapter.getCount();
        int a;
        int b;
        int c;
        int d;
        int k = 0;
        int carboh = 0;
        int protein = 0;
        int fat = 0;
        if (count >= 1) {
            for (int i = 0; i < count; i++) {
                a = Integer.parseInt(Lunchadapter.getFKcal(i));
                k = k + a;
                b = Integer.parseInt(Lunchadapter.getFCarboh(i));
                carboh = carboh + b;
                c = Integer.parseInt(Lunchadapter.getFProtein(i));
                protein = protein + c;
                d = Integer.parseInt(Lunchadapter.getFFat(i));
                fat = fat + d;
            }
            Lu_All_data.setText("칼로리: " + k + "Kcal 탄수화물: " + carboh + "g 단백질: " + protein + "g 지방: " + fat + "g");
        } else {
            Lu_All_data.setText("칼로리: 0Kcal 탄수화물: 0g 단백질: 0g 지방: 0g");
        }
    }
        public void DiData () {
            int count = Dinneradapter.getCount();
            int a;
            int b;
            int c;
            int d;
            int k = 0;
            int carboh = 0;
            int protein = 0;
            int fat = 0;
            if (count >= 1) {
                for (int i = 0; i < count; i++) {
                    a = Integer.parseInt(Dinneradapter.getFKcal(i));
                    k = k + a;
                    b = Integer.parseInt(Dinneradapter.getFCarboh(i));
                    carboh = carboh + b;
                    c = Integer.parseInt(Dinneradapter.getFProtein(i));
                    protein = protein + c;
                    d = Integer.parseInt(Dinneradapter.getFFat(i));
                    fat = fat + d;
                }
                Di_All_data.setText("칼로리: " + k + "Kcal 탄수화물: " + carboh + "g 단백질: " + protein + "g 지방: " + fat + "g");
            }
            else {
                Di_All_data.setText("칼로리: 0Kcal 탄수화물: 0g 단백질: 0g 지방: 0g");
            }

        }

}