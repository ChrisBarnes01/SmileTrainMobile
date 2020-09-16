package com.aletify.smiletrainmobiletwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CheckIn3 extends AppCompatActivity {


    Button checkInButton;
    ToggleButton toggle1;
    ToggleButton toggle2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in4);


        toggle1 = findViewById(R.id.simpleToggleButton1);
        toggle2 = findViewById(R.id.simpleToggleButton2);
        toggle1.setText("No");
        toggle1.setTextOn("No");
        toggle1.setTextOff("No");

        toggle2.setText("Yes");
        toggle2.setTextOn("Yes");
        toggle2.setTextOff("Yes");

        checkInButton = findViewById(R.id.check_in_button_to_camera);
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jump = new Intent(getApplicationContext(), SecondaryCamera.class);
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
}