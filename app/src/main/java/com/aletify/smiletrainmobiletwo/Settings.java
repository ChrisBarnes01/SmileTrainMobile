package com.aletify.smiletrainmobiletwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.settings_page_nav);
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


        getPermissions();
        if (permissionsGranted() == false) {
            Toast.makeText(getApplicationContext(), "You Must Grant Permissions to use Camera", Toast.LENGTH_SHORT);
        }
        else{
            Intent intent = new Intent(getApplicationContext(), CameraActivity4.class);
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