package com.example.foodcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;

public class FoodData extends AppCompatActivity {


    EditText edit;
    ListView listView;
    FoodListAdapter adapter;



    String key="A%2BQx%2B0%2BGwXKYaEqA5AvQUsBx0KBD3n4kAxQvz3OXLA1N1KqWkO7GLM0KfDb9Mtn0AY4LZZJiny5AWk7FD1DnbA%3D%3D";

    public static final String FNAME = "FNMSG";
    public static final String FKCAL = "FKMSG";
    public static final String FCARBOH = "FCMSG";
    public static final String FPROTEIN = "FPMSG";
    public static final String FFAT = "FFMSG";


    EditText NameView;
    EditText KcalView;
    EditText CarbohView;
    EditText ProteinView;
    EditText FatView;
    EditText Serving;

    Button addButton;
    Button servingButton;

    String FoodName;
    String FoodKcal;
    String FoodCarboh;
    String FoodProtein;
    String FoodFat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_data);
        listView=(ListView) findViewById(R.id.foodList);
        adapter = new FoodListAdapter();
        listView.setAdapter(adapter);

        Button searchButton =(Button)findViewById(R.id.searchBtn);

        edit= (EditText)findViewById(R.id.edit);
        Serving = (EditText)findViewById(R.id.serving);

        NameView = findViewById(R.id.fname);
        KcalView = findViewById(R.id.fkcal);
        CarbohView = findViewById(R.id.fcarboh);
        ProteinView = findViewById(R.id.fprotein);
        FatView = findViewById(R.id.ffat);

        addButton = findViewById(R.id.addButton);
        servingButton = findViewById(R.id.servingButton);

        //제공량(n인분)을 입력하고 버튼 클릭시 영양정보 수정기능

        servingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NameValue = NameView.getText().toString();
                String KcalValue = KcalView.getText().toString();
                String CarbohValue = CarbohView.getText().toString();
                String ProteinValue = ProteinView.getText().toString();
                String FatValue = FatView.getText().toString();
                String tempServing = Serving.getText().toString();
                double ServingValue = (double)Double.parseDouble(tempServing);
                if(TextUtils.isEmpty(NameValue) || TextUtils.isEmpty(KcalValue)
                        || TextUtils.isEmpty(CarbohValue) || TextUtils.isEmpty(ProteinValue)
                        || TextUtils.isEmpty(FatValue))
                {
                    Toast.makeText(FoodData.this, "모든 영양정보를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    NameView.setText(NameValue + " * " + tempServing);
                    KcalView.setText(StringToInt(KcalValue,tempServing));
                    CarbohView.setText(StringToInt(CarbohValue,tempServing));
                    ProteinView.setText(StringToInt(ProteinValue,tempServing));
                    FatView.setText(StringToInt(FatValue,tempServing));
                }
            }
        });


        //데이터 검색
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();

                        String str= edit.getText().toString();
                        String searchItem = URLEncoder.encode(str);

                        String queryUrl="http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1?Servicekey="+key+"&desc_kor="+searchItem;

                        try{
                            URL url= new URL(queryUrl);
                            InputStream is= url.openStream();
                            InputStreamReader isr=new InputStreamReader(is);

                            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                            XmlPullParser xpp=factory.newPullParser();
                            xpp.setInput(isr);

                            String tag;
                            xpp.next();
                            int eventType= xpp.getEventType();
                            StringBuffer buffer=null;

                            StringBuffer n = null;
                            StringBuffer k = null;
                            StringBuffer c = null;
                            StringBuffer p = null;
                            StringBuffer f = null;

                            while( eventType != XmlPullParser.END_DOCUMENT ){

                                switch( eventType ){

                                    case XmlPullParser.START_DOCUMENT:
                                        break;

                                    case XmlPullParser.START_TAG:
                                        tag= xpp.getName();
                                        if(tag.equals("item")){
                                            n = new StringBuffer();
                                            k = new StringBuffer();
                                            c = new StringBuffer();
                                            p = new StringBuffer();
                                            f = new StringBuffer();
                                        }
                                        else if(tag.equals("DESC_KOR")){

                                            xpp.next();
                                            n.append(xpp.getText());

                                        }
                                        else if(tag.equals("SERVING_WT")){
                                            xpp.next();
                                            n.append(" ("+ xpp.getText()+"g)");
                                        }
                                        else if(tag.equals("NUTR_CONT1")){
                                            xpp.next();
                                            k.append(xpp.getText());
                                        }
                                        else if(tag.equals("NUTR_CONT2")){
                                            xpp.next();
                                            f.append(xpp.getText());
                                        }
                                        else if(tag.equals("NUTR_CONT3")){
                                            xpp.next();
                                            p.append(xpp.getText());
                                        }
                                        else if(tag.equals("NUTR_CONT4")){
                                            xpp.next();
                                            c.append(xpp.getText());
                                        }
                                        break;

                                    case XmlPullParser.TEXT:
                                        break;

                                    case XmlPullParser.END_TAG:
                                        tag= xpp.getName();

                                        if(tag.equals("item")) {
                                            adapter.addItem(new MealListViewAdapterData(n.toString(),k.toString(),c.toString(),p.toString(),f.toString()));


                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    adapter.notifyDataSetChanged();
                                                }
                                            });
                                        }
                                        break;
                                }

                                eventType= xpp.next();
                            }

                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        //검색된 음식 클릭 시 각각의 영양정보 텍스트에 입력
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MealListViewAdapterData item = (MealListViewAdapterData) adapter.getItem(position);
                NameView.setText(item.getFoodName());
                KcalView.setText(item.getFoodKcalToInt());
                FatView.setText(item.getFoodCarbohToInt());
                ProteinView.setText(item.getFoodProteinToInt());
                CarbohView.setText(item.getFoodFatToInt());
            }
        });
//        데이터 삽입
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FoodName = NameView.getText().toString();
                if(NameView.length() == 0){
                    Toast.makeText(FoodData.this, "음식명을 입력해주세요\n ", Toast.LENGTH_SHORT).show();
                }

                FoodKcal = KcalView.getText().toString();
                if(FoodKcal.length() == 0){
                    Toast.makeText(FoodData.this, "칼로리를 입력해주세요\n", Toast.LENGTH_SHORT).show();
                }

                FoodCarboh = CarbohView.getText().toString();
                if(FoodCarboh.length() == 0){
                    Toast.makeText(FoodData.this, "탄수화물 양을 입력해주세요\n", Toast.LENGTH_SHORT).show();
                }

                FoodProtein = ProteinView.getText().toString();
                if(FoodProtein.length() == 0){
                    Toast.makeText(FoodData.this, "단백질 양을 입력해주세요\n", Toast.LENGTH_SHORT).show();
                }

                FoodFat = FatView.getText().toString();
                if(FoodFat.length() == 0){
                    Toast.makeText(FoodData.this, "지방량을 입력해주세요\n", Toast.LENGTH_SHORT).show();
                }

                if(NameView.length() != 0 && KcalView.length() != 0 && CarbohView.length() != 0 && ProteinView.length() != 0 && FatView.length() != 0)
                {
                    Intent intent = new Intent();
                    intent.putExtra(FNAME, FoodName);
                    intent.putExtra(FKCAL, FoodKcal);
                    intent.putExtra(FCARBOH, FoodCarboh);
                    intent.putExtra(FPROTEIN, FoodProtein);
                    intent.putExtra(FFAT, FoodFat);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }
    public String StringToInt(String string, String serving) {
        double temp  = (double)Double.parseDouble(string);
        double tempServing = (double)Double.parseDouble(serving);
        double resultValue = temp * tempServing;
        int returnValaue = (int)Math.round(resultValue);
        return Integer.toString(returnValaue);
    }

}