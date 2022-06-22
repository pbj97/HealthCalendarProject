package com.example.foodcalendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final int CALENDER = 101;
    public static final String KEY_MSG = "msg";

    private RadioGroup choicegender;
    private int age;
    private int play = 0;
    RadioButton ManButton;
    RadioButton WomanButton;
    String check = "남성";
    String kcal;
    EditText agetext;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agetext = findViewById(R.id.agetext);
        button = findViewById(R.id.button);

        choicegender = findViewById(R.id.RadioGroup);
        ManButton = findViewById(R.id.ManButton);
        WomanButton = findViewById(R.id.WomanButton);
        restoreState();
        choicegender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.ManButton) {
                    check = "남성";
                } else if (i == R.id.WomanButton) {
                    check = "여성";
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String agemsg = agetext.getText().toString();
                age = Integer.parseInt(agemsg);

                if(age <= 0) {
                Toast.makeText(MainActivity.this, "나이를 다시 입력해주세요", Toast.LENGTH_LONG).show();
                }

                if (age >= 1 && age <= 3) {
                    kcal = "1200";
                    play = 1;
                }
                else if (age >= 4 && age <= 6) {
                    kcal = "1600";
                    play = 1;
                }
                else if (age >= 7 && age <= 9) {
                    kcal = "1800";
                    play = 1;
                }

                else if (check == "남성") {
                    if (age >= 10 && age <= 12) {
                        kcal = "2200";
                        play = 1;
                    } else if (age >= 13 && age <= 15) {
                        kcal = "2500";
                        play = 1;
                    } else if (age >= 16 && age <= 19) {
                        kcal = "2700";
                        play = 1;
                    } else if (age >= 20 && age <= 29) {
                        kcal = "2500";
                        play = 1;
                    } else if (age >= 30 && age <= 49) {
                        kcal = "2500";
                        play = 1;
                    } else if (age >= 50 && age <= 64) {
                        kcal = "2300";
                        play = 1;
                    } else if (age >= 65 && age <= 74) {
                        kcal = "2000";
                        play = 1;
                    } else if (age >= 75){
                        kcal = "1800";
                        play = 1;
                    }
                }

                else if (check == "여성") {
                    if (age >= 10 && age <= 12) {
                        kcal = "2000";
                        play = 1;
                    } else if (age >= 13 && age <= 15) {
                        kcal = "2100";
                        play = 1;
                    } else if (age >= 16 && age <= 19) {
                        kcal = "2100";
                        play = 1;
                    } else if (age >= 20 && age <= 29) {
                        kcal = "2000";
                        play = 1;
                    } else if (age >= 30 && age <= 49) {
                        kcal = "2000";
                        play = 1;
                    } else if (age >= 50 && age <= 64) {
                        kcal = "1900";
                        play = 1;
                    } else if (age >= 65 && age <= 74) {
                        kcal = "1700";
                        play = 1;
                    } else if(age >= 75){
                        kcal = "1600";
                        play = 1;
                    }
                }
                if (play == 1) {
                    Intent intent = new Intent(getApplicationContext(), Calendar.class);
                    intent.putExtra(KEY_MSG, kcal);
                    startActivity(intent);
                    saveState();
                    finish();
                }
            }
        }); //버튼 이벤트
    }

    protected void restoreState(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if((pref != null) && (pref.contains("contents"))){
            String contents = pref.getString("contents","");
            Intent intent = new Intent(getApplicationContext(), Calendar.class);
            intent.putExtra(KEY_MSG, contents);
            startActivity(intent);
            finish();
        }
    }

    protected void saveState(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("contents", kcal);
        editor.commit();
    }
}