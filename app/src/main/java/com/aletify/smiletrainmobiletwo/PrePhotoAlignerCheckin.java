package com.aletify.smiletrainmobiletwo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class PrePhotoAlignerCheckin extends AppCompatActivity {

    ToggleButton holeNo;
    ToggleButton holeYes;
    ToggleButton crackNo;
    ToggleButton crackYes;
    boolean isCracked;
    boolean hasHoles;
    boolean touchedHoles;
    boolean touchedCracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_photo_aligner_checkin);

        final TextView clickHereText = (TextView)findViewById(R.id.clickable_text_link);
        final TextView nextButton = (TextView)findViewById(R.id.prev_aligner_button_to_camera);
        touchedCracks = false;
        touchedHoles = false;

        String first = "¿Este es tu primer alineador? ";
        String next = "Oprime aquí.";

        clickHereText.setText(first + next, TextView.BufferType.SPANNABLE);
        Spannable s = (Spannable)clickHereText.getText();
        int start = 0;
        int end = first.length();
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), end, end + next.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        clickHereText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Link to Tutorial Slide", Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (touchedHoles == false || touchedCracks == false ){
                    Toast.makeText(getApplicationContext(), "Must answer Questions", Toast.LENGTH_LONG).show();
                }
                else if(isCracked == false && hasHoles == false){
                    Intent intent = new Intent(getApplicationContext(), TestAlligner.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), SecondaryCamera.class);
                    startActivity(intent);
                }


            }
        }
        );

        holeNo = findViewById(R.id.holeButton1);
        holeYes = findViewById(R.id.holeButton2);
        holeNo.setText("No");
        holeNo.setTextOn("No");
        holeNo.setTextOff("No");

        holeYes.setText("Si");
        holeYes.setTextOn("Si");
        holeYes.setTextOff("Si");

        holeNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holeYes.setChecked(false);
                    holeYes.setTextColor(getResources().getColor(R.color.colorBlack));
                    holeNo.setTextColor(getResources().getColor(R.color.colorAccent));
                    hasHoles = false;
                } else {
                    holeYes.setChecked(true);
                    holeYes.setTextColor(getResources().getColor(R.color.colorAccent));
                    holeNo.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                touchedHoles = true;
            }
        });
        holeYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holeNo.setChecked(false);
                    holeYes.setTextColor(getResources().getColor(R.color.colorAccent));
                    holeNo.setTextColor(getResources().getColor(R.color.colorBlack));
                    hasHoles = true;
                } else {
                    holeNo.setChecked(true);
                    holeYes.setTextColor(getResources().getColor(R.color.colorBlack));
                    holeNo.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                touchedHoles = true;
            }
        });



        crackNo = findViewById(R.id.crackButton1);
        crackYes = findViewById(R.id.crackButton2);
        crackNo.setText("No");
        crackNo.setTextOn("No");
        crackNo.setTextOff("No");

        crackYes.setText("Si");
        crackYes.setTextOn("Si");
        crackYes.setTextOff("Si");

        crackNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    crackYes.setChecked(false);
                    crackYes.setTextColor(getResources().getColor(R.color.colorBlack));
                    crackNo.setTextColor(getResources().getColor(R.color.colorAccent));
                    isCracked = false;
                } else {
                    crackYes.setChecked(true);
                    crackYes.setTextColor(getResources().getColor(R.color.colorAccent));
                    crackNo.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                touchedCracks = true;
            }
        });
        crackYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    crackNo.setChecked(false);
                    crackYes.setTextColor(getResources().getColor(R.color.colorAccent));
                    crackNo.setTextColor(getResources().getColor(R.color.colorBlack));
                    isCracked = true;
                } else {
                    crackNo.setChecked(true);
                    crackYes.setTextColor(getResources().getColor(R.color.colorBlack));
                    crackNo.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                touchedCracks = true;
            }
        });

    }
}