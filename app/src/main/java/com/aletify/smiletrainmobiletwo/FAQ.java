//Modified from https://github.com/gifffert/ExpandableCardView/blob/master/app/src/main/java/ru/embersoft/expandablecardview/MainActivity.java

package com.aletify.smiletrainmobiletwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.transition.AutoTransition;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FAQ extends AppCompatActivity {

    LinearLayout expandableView;
    ImageView arrowBtn;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);

        expandableView = findViewById(R.id.dropdown_expandable_view);
        arrowBtn = findViewById(R.id.dropdown_arrow_sign);
        cardView = findViewById(R.id.dropdown_cardView);

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.info_page_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);

                switch (item.getItemId()) {
                    case R.id.home_page_nav: {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case R.id.camera_page_nav: {
                        runCamera();
                    }
                    break;
                    case R.id.info_page_nav: {
                        runFAQ();
                    }
                    break;
                    case R.id.settings_page_nav: {
                        runSettings();
                    }
                    break;
                }
                return true;
            }
        });

    }

    private void getPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // here, Permission is not granted
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, 50);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // here, Permission is not granted
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 50);
        }
    }

    private boolean permissionsGranted(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            return false;
        }
        return true;
    }

    private void runCamera(){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        int numberOfDaysUntilCheckIn = prefs.getInt("numberOfDaysUntilCheckIn", 1);
        getPermissions();
        if (permissionsGranted() == false) {
            Toast.makeText(getApplicationContext(), "You Must Grant Permissions to use Camera", Toast.LENGTH_SHORT);
        }
        if (numberOfDaysUntilCheckIn == 0){
            Intent intent = new Intent(getApplicationContext(), PrePhotoAlignerCheckin.class);

            startActivity(intent);
        }
        else{
            Intent intent = new Intent(getApplicationContext(), TooEarlyForPictureNotification.class);
            startActivity(intent);
        }
    }

    private void runFAQ(){

        Intent intent = new Intent(getApplicationContext(), FAQ.class);
        startActivity(intent);

    }

    private void runSettings(){
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
    }

}