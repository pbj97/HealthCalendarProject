package com.example.foodcalendar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Calendar extends AppCompatActivity {
    public static final String KEY_MSG = "msg";
    public static final int Code = 200;

    public static final String BF_MSG = "BFMSG";
    public static final String LU_MSG = "LUMSG";
    public static final String DI_MSG = "DIMSG";
    public static final String ALL_KCAL = "AKMSG";
    public static final String ALL_CARBOH = "ACMSG";
    public static final String ALL_PROTEIN = "APMSG";
    public static final String ALL_FAT = "AFMSG";

    int kcal;
    int carboh; //탄수화물
    int protein; //단백질
    int fat; // 지방
    TextView kcalview;
    TextView carbohview;
    TextView proteinview;
    TextView fatview;
    Button check;
    TextView daytext;
    TextView ResultText;
    TextView noticekcal;
    TextView noticecarboh;
    TextView noticeprotein;
    TextView noticefat;

    public String ClickDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        kcalview = findViewById(R.id.kcal);
        carbohview = findViewById(R.id.carboh1);
        proteinview = findViewById(R.id.protein);
        fatview = findViewById(R.id.fat);
        check = findViewById(R.id.button2);
        MaterialCalendarView materialCalendarView = findViewById(R.id.calendarview);
        materialCalendarView.setSelectedDate(CalendarDay.today());
        daytext = findViewById(R.id.daytext);
        ResultText = findViewById(R.id.ResultText);
        noticekcal = findViewById(R.id.noticekcal);
        noticecarboh = findViewById(R.id.noticecarboh);
        noticeprotein = findViewById(R.id.noticeprotein);
        noticefat = findViewById(R.id.noticefat);
        //materialCalendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(2022, 3, 4))));

        Intent intent = getIntent();
        String msg = intent.getStringExtra(KEY_MSG);
        kcal = Integer.parseInt(msg);
        carboh = (int) (kcal * 0.5  / 4);
        protein = (int) (kcal * 0.3 / 4);
        fat = (int) (kcal * 0.2 / 9);
        kcalview.setText(kcal + "kcal");
        carbohview.setText(carboh + "g");
        proteinview.setText(protein + "g");
        fatview.setText(fat + "g");

        OneDayDBhelper dBhelper = new OneDayDBhelper(this);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        //앱실행시-날짜표시텍스트->현재날짜로 초기화
        SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = java.util.Calendar.getInstance().getTime();
        String Todaydate = dataformat.format(currentTime);
        daytext.setText(Todaydate);
        ClickDate = Todaydate;

        try {
            String sql = "select * from OneDay where Date= "+ "'" +Todaydate+"'";
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.getCount() <= 0){
                ResultText.setText("검사하기 버튼으로 해당 날짜의 식사를 확인해보세요");
                noticekcal.setText(" ");
                noticecarboh.setText(" ");
                noticeprotein.setText(" ");
                noticefat.setText(" ");
            }
            while(cursor.moveToNext()) {
                //String lackKcal = " ";
                String lackCarboh = " ";
                String lackProtein = " ";
                String lackFat = " ";
                //int lackKcal = 0;
                String a = cursor.getString(1); //날짜
                String b = cursor.getString(2); //아침
                String c = cursor.getString(3); //점심
                String d = cursor.getString(4); //저녁
                int e = cursor.getInt(5); //칼로리
                if (e < kcal) {
                    //lackKcal = kcal - e;
                }
                int f = cursor.getInt(6); //탄수화물
                int g = cursor.getInt(7); //단백질
                int h = cursor.getInt(8); //지방방
                String lackKcal = String.valueOf(kcal - e);
                ResultText.setText("아침: " + b + "\n" +"점심: "+ c + "\n" + "저녁: " + d + "\n" +
                        "칼로리: " + e + "kcal 탄수화물: " + f + "g 단백질: " + g + "g 지방: " + h + "g\n\n");

                if(e < kcal) {
                    noticekcal.setText(String.valueOf(kcal - e) + "kcal 부족");
                    noticekcal.setTextColor(Color.parseColor("#FF0000"));
                }
                else {
                    noticekcal.setText("칼로리 충분");
                    noticekcal.setTextColor(Color.parseColor("#00FF00"));
                }
                if(f < carboh) {
                    noticecarboh.setText(String.valueOf(carboh - f) + "g 부족");
                    noticecarboh.setTextColor(Color.parseColor("#FF0000"));
                }
                else {
                    noticecarboh.setText("탄수화물 충분");
                    noticecarboh.setTextColor(Color.parseColor("#00FF00"));
                }
                if (g < protein) {
                    noticeprotein.setText(String.valueOf(protein - g) + "g 부족");
                    noticeprotein.setTextColor(Color.parseColor("#FF0000"));
                }
                else {
                    noticeprotein.setText("단백질 충분");
                    noticeprotein.setTextColor(Color.parseColor("#00FF00"));
                }
                if (h < fat) {
                    noticefat.setText(String.valueOf(fat - h) + "g 부족");
                    noticefat.setTextColor(Color.parseColor("#FF0000"));
                }
                else {
                    noticefat.setText("지방 충분");
                    noticefat.setTextColor(Color.parseColor("#00FF00"));
                }
            }
        }
        catch (Exception e){
            ResultText.setText("오류");
        }

        //달력에 한글적용
        materialCalendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        materialCalendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));

        //주말에 색상적용(파랑, 빨강)
        materialCalendarView.addDecorator(new SundayDecorator());
        materialCalendarView.addDecorator(new SaturdayDecorator());

        CalenderDot(materialCalendarView, db);

        //날짜 변경시 변경 날짜 텍스트에 표시
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String Sdate = dataformat.format(date.getDate());
                daytext.setText(Sdate);
                ClickDate = Sdate;
                try {
                    String sql = "select * from OneDay where Date= "+ "'" +Sdate+"'";
                    Cursor cursor = db.rawQuery(sql, null);
                    if(cursor.getCount() <= 0){
                            ResultText.setText("검사하기 버튼으로 해당 날짜의 식사를 확인해보세요");
                            noticekcal.setText(" ");
                            noticecarboh.setText(" ");
                            noticeprotein.setText(" ");
                            noticefat.setText(" ");
                    }
                    while(cursor.moveToNext()) {
                        String lackKcal = "";
                        String lackCarboh = "";
                        String lackProtein = "";
                        String lackFat = "";

                        String a = cursor.getString(1); //날짜
                        String b = cursor.getString(2); //아침
                        String c = cursor.getString(3); //점심
                        String d = cursor.getString(4); //저녁
                        int e = cursor.getInt(5); //칼로리
                        int f = cursor.getInt(6); //탄수화물
                        int g = cursor.getInt(7); //단백질
                        int h = cursor.getInt(8); //지방방

                        ResultText.setText("아침: " + b + "\n" +"점심: "+ c + "\n" + "저녁: " + d + "\n" +
                                "칼로리: " + e + "kcal 탄수화물: " + f + "g 단백질: " + g + "g 지방: " + h + "g\n\n");

                        if(e < kcal) {
                            noticekcal.setText(String.valueOf(kcal - e) + "kcal 부족");
                            noticekcal.setTextColor(Color.parseColor("#FF0000"));
                        }
                        else {
                            noticekcal.setText("칼로리 충분");
                            noticekcal.setTextColor(Color.parseColor("#00FF00"));
                        }
                        if(f < carboh) {
                            noticecarboh.setText(String.valueOf(carboh - f) + "g 부족");
                            noticecarboh.setTextColor(Color.parseColor("#FF0000"));
                        }
                        else {
                            noticecarboh.setText("탄수화물 충분");
                            noticecarboh.setTextColor(Color.parseColor("#00FF00"));
                        }
                        if (g < protein) {
                            noticeprotein.setText(String.valueOf(protein - g) + "g 부족");
                            noticeprotein.setTextColor(Color.parseColor("#FF0000"));
                        }
                        else {
                            noticeprotein.setText("단백질 충분");
                            noticeprotein.setTextColor(Color.parseColor("#00FF00"));
                        }
                        if (h < fat) {
                            noticefat.setText(String.valueOf(fat - h) + "g 부족");
                            noticefat.setTextColor(Color.parseColor("#FF0000"));
                        }
                        else {
                            noticefat.setText("지방 충분");
                            noticefat.setTextColor(Color.parseColor("#00FF00"));
                        }
                    }
                }
                catch (Exception e){
                    ResultText.setText("오류");
                }
            }
        });

        //검사하기 버튼
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowMeal.class);
                startActivityForResult(intent, 1000);
            }
        });
    }
    //결과 확인
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        super.onActivityResult(requestcode, resultcode, data);
        if(requestcode == 1000){
            if(resultcode == RESULT_OK){
                String q = data.getStringExtra(BF_MSG);
                String w = data.getStringExtra(LU_MSG);
                String e = data.getStringExtra(DI_MSG);
                String r = data.getStringExtra(ALL_KCAL);
                String t = data.getStringExtra(ALL_CARBOH);
                String y = data.getStringExtra(ALL_PROTEIN);
                String u = data.getStringExtra(ALL_FAT);

                OneDayDBhelper dBhelper = new OneDayDBhelper(this);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                dBhelper.insertDB(db, ClickDate, q, w, e, r, t ,y ,u);
                ResultText.setText("아침: " + q + "\n" +"점심: "+ w + "\n" + "저녁: " + e + "\n" +
                        "칼로리: " + r + "kcal 탄수화물: " + t + "g 단백질: " + y + "g 지방: " + u + "g\n");

                int k =Integer.parseInt(r);
                int c = Integer.parseInt(t);
                int p = Integer.parseInt(y);
                int f = Integer.parseInt(u);
                if(k < kcal) {
                    noticekcal.setText(String.valueOf(kcal - k) + "kcal 부족");
                    noticekcal.setTextColor(Color.parseColor("#FF0000"));
                }
                else {
                    noticekcal.setText("칼로리 충분");
                    noticekcal.setTextColor(Color.parseColor("#00FF00"));
                }
                if(c < carboh) {
                    noticecarboh.setText(String.valueOf(carboh - c) + "g 부족");
                    noticecarboh.setTextColor(Color.parseColor("#FF0000"));
                }
                else {
                    noticecarboh.setText("탄수화물 충분");
                    noticecarboh.setTextColor(Color.parseColor("#00FF00"));
                }
                if (p < protein) {
                    noticeprotein.setText(String.valueOf(protein - p) + "g 부족");
                    noticeprotein.setTextColor(Color.parseColor("#FF0000"));
                }
                else {
                    noticeprotein.setText("단백질 충분");
                    noticeprotein.setTextColor(Color.parseColor("#00FF00"));
                }
                if (f < fat) {
                    noticefat.setText(String.valueOf(fat - f) + "g 부족");
                    noticefat.setTextColor(Color.parseColor("#FF0000"));
                }
                else {
                    noticefat.setText("지방 충분");
                    noticefat.setTextColor(Color.parseColor("#00FF00"));
                }


                MaterialCalendarView materialCalendarView = findViewById(R.id.calendarview);
                CalenderDot(materialCalendarView, db);
            }
        }
    }

    public void CalenderDot(MaterialCalendarView materialCalendarView, SQLiteDatabase db){
        try {
            Cursor cursor = db.rawQuery("select * from OneDay", null);
            while (cursor.moveToNext()){
                String TheDate = cursor.getString(1);
                String year = TheDate.substring(0, 4);
                int Iyear = Integer.parseInt(year);
                String month = TheDate.substring(5, 7);
                int Imonth = Integer.parseInt(month);
                String day = TheDate.substring(8, 10);
                int Iday = Integer.parseInt(day);
                int Checkcount = 0;
                int Ckcal = cursor.getInt(5);
                if(Ckcal >= kcal){
                    Checkcount++;
                }
                int Ccarboh = cursor.getInt(6);
                if(Ccarboh >= carboh){
                    Checkcount++;
                }
                int Cprotein = cursor.getInt(7);
                if(Cprotein >= protein){
                    Checkcount++;
                }
                int Cfat = cursor.getInt(8);
                if(Cfat >= fat){
                    Checkcount++;
                }
                if (Checkcount == 4){
                    materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, Collections.singleton(CalendarDay.from(Iyear, Imonth - 1, Iday))));
                }
                else if(Checkcount <= 3 && Checkcount >= 1){
                    materialCalendarView.addDecorator(new EventDecorator(Color.rgb(243, 115,033), Collections.singleton(CalendarDay.from(Iyear, Imonth - 1, Iday))));
                }
                else {
                    materialCalendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(Iyear, Imonth - 1, Iday))));
                }
            }
        }
        catch (Exception e){
            ResultText.setText("오류");
        }
    }
}