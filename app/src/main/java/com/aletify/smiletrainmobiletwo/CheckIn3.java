package com.aletify.smiletrainmobiletwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckIn3 extends AppCompatActivity {


    Button checkInButton;
    ToggleButton toggle1;
    ToggleButton toggle2;

    TextView lost0;
    ImageView lost1;
    ImageView lost2;
    ImageView lost3;
    ImageView lost4;
    TextView lost5;
    int lostTeethNumber;

    ImageView pain1;
    ImageView pain2;
    ImageView pain3;
    ImageView pain4;
    ImageView pain5;
    int painNumber;

    TextView new0;
    ImageView new1;
    ImageView new2;
    ImageView new3;
    ImageView new4;
    TextView new5;
    int newNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in4);

        lost0 = (TextView) findViewById(R.id.lost0);
        lost1 = (ImageView) findViewById(R.id.lost1);
        lost2 = (ImageView) findViewById(R.id.lost2);
        lost3 = (ImageView) findViewById(R.id.lost3);
        lost4 = (ImageView) findViewById(R.id.lost4);
        lost5 = (TextView) findViewById(R.id.lost5);
        lostTeethNumber = -1;

        lost0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllLostTeeth();
                lost0.setBackgroundResource(R.drawable.border_background);
                lostTeethNumber = 0;
            }
        });
        lost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllLostTeeth();
                lost1.setBackgroundResource(R.drawable.border_background);
                lostTeethNumber = 1;
            }
        });
        lost2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllLostTeeth();
                lost2.setBackgroundResource(R.drawable.border_background);
                lostTeethNumber = 2;
            }
        });
        lost3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllLostTeeth();
                lost3.setBackgroundResource(R.drawable.border_background);
                lostTeethNumber = 3;
            }
        });
        lost4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllLostTeeth();
                lost4.setBackgroundResource(R.drawable.border_background);
                lostTeethNumber = 4;
            }
        });
        lost5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllLostTeeth();
                lost5.setBackgroundResource(R.drawable.border_background);
                lostTeethNumber = 5;
            }
        });

        pain1 = (ImageView) findViewById(R.id.pain_1);
        pain2 = (ImageView) findViewById(R.id.pain_2);
        pain3 = (ImageView) findViewById(R.id.pain_3);
        pain4 = (ImageView) findViewById(R.id.pain_4);
        pain5 = (ImageView) findViewById(R.id.pain_5);
        painNumber = -1;

        pain1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllPain();
                pain1.setBackgroundResource(R.drawable.border_background);
                painNumber = 1;
            }
        });
        pain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllPain();
                pain2.setBackgroundResource(R.drawable.border_background);
                painNumber = 2;
            }
        });
        pain3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllPain();
                pain3.setBackgroundResource(R.drawable.border_background);
                painNumber = 3;
            }
        });
        pain4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllPain();
                pain4.setBackgroundResource(R.drawable.border_background);
                painNumber = 4;
            }
        });
        pain5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllPain();
                pain5.setBackgroundResource(R.drawable.border_background);
                painNumber = 5;
            }
        });

        new0 = (TextView) findViewById(R.id.gained0);
        new1 = (ImageView) findViewById(R.id.gained1);
        new2 = (ImageView) findViewById(R.id.gained2);
        new3 = (ImageView) findViewById(R.id.gained3);
        new4 = (ImageView) findViewById(R.id.gained4);
        new5 = (TextView) findViewById(R.id.gained5);
        newNumber = -1;

        new0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllNew();
                new0.setBackgroundResource(R.drawable.border_background);
                newNumber = 0;
            }
        });
        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllNew();
                new1.setBackgroundResource(R.drawable.border_background);
                newNumber = 1;
            }
        });
        new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllNew();
                new2.setBackgroundResource(R.drawable.border_background);
                newNumber = 2;
            }
        });
        new3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllNew();
                new3.setBackgroundResource(R.drawable.border_background);
                newNumber = 3;
            }
        });
        new4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllNew();
                new4.setBackgroundResource(R.drawable.border_background);
                newNumber = 4;
            }
        });
        new5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deselectAllNew();
                new5.setBackgroundResource(R.drawable.border_background);
                newNumber = 5;
            }
        });


        toggle1 = findViewById(R.id.simpleToggleButton1);
        toggle2 = findViewById(R.id.simpleToggleButton2);
        toggle1.setText("No");
        toggle1.setTextOn("No");
        toggle1.setTextOff("No");

        toggle2.setText("Si");
        toggle2.setTextOn("Si");
        toggle2.setTextOff("Si");

        checkInButton = findViewById(R.id.check_in_button_to_camera);
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent jump = new Intent(getApplicationContext(), SecondaryCamera.class);
                uploadCheckinToFirebase();
                Intent jump = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(jump);
            }
        });

        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggle2.setChecked(false);
                    toggle2.setTextColor(getResources().getColor(R.color.colorBlack));
                    toggle1.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    toggle2.setChecked(true);
                    toggle2.setTextColor(getResources().getColor(R.color.colorAccent));
                    toggle1.setTextColor(getResources().getColor(R.color.colorBlack));

                }
            }
        });
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggle1.setChecked(false);
                    toggle2.setTextColor(getResources().getColor(R.color.colorAccent));
                    toggle1.setTextColor(getResources().getColor(R.color.colorBlack));
                } else {
                    toggle1.setChecked(true);
                    toggle2.setTextColor(getResources().getColor(R.color.colorBlack));
                    toggle1.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });

    }

    private void uploadCheckinToFirebase(){
        List<CalendarObject> calendarObjectList = new ArrayList<>();


        //1 Calendar Object

        CheckInSet mySet = new CheckInSet();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String username = prefs.getString("username", "userId");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("patients");
        DatabaseReference userProfile = mDatabase.child(username).child("checkInObjectList");
        Map<String, Object> checkInUpdates = new HashMap<>();
        checkInUpdates.put(Integer.toString(mySet.date), mySet);
        userProfile.updateChildren(checkInUpdates);

    }

    private void deselectAllLostTeeth(){
        lost0.setBackgroundResource(R.color.colorWhite);
        lost1.setBackgroundResource(R.color.colorWhite);
        lost2.setBackgroundResource(R.color.colorWhite);
        lost3.setBackgroundResource(R.color.colorWhite);
        lost4.setBackgroundResource(R.color.colorWhite);
        lost5.setBackgroundResource(R.color.colorWhite);
    }

    private void deselectAllPain(){
        pain1.setBackgroundResource(R.color.colorWhite);
        pain2.setBackgroundResource(R.color.colorWhite);
        pain3.setBackgroundResource(R.color.colorWhite);
        pain4.setBackgroundResource(R.color.colorWhite);
        pain5.setBackgroundResource(R.color.colorWhite);
    }

    private void deselectAllNew(){
        new0.setBackgroundResource(R.color.colorWhite);
        new1.setBackgroundResource(R.color.colorWhite);
        new2.setBackgroundResource(R.color.colorWhite);
        new3.setBackgroundResource(R.color.colorWhite);
        new4.setBackgroundResource(R.color.colorWhite);
        new5.setBackgroundResource(R.color.colorWhite);
    }

}