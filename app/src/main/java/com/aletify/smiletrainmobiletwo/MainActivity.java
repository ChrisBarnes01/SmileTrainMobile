package com.aletify.smiletrainmobiletwo;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        //JUMP TO CREATE_ACCOUNT PAGE
        //DELETE THIS LATER
        //runCreateAccount();


        if (firstStart){
            runIntroSequence();
        }

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        /*
        final CardView camera = findViewById(R.id.camera);
        final CardView cameraFlow = findViewById(R.id.camera_flow);
        final RatingBar ratingLink = findViewById(R.id.ratingBarLink);
        final CardView seeIntroduction = findViewById(R.id.introduction);


        //Set the Main Customized Titles
        String username = prefs.getString("firstName", "user")+ " " + prefs.getString("lastName", "name");
        String profilePicUrl = prefs.getString("profilePic", "");

        if (username == " "){
            username = "Undefined User";
        }
        TextView subtitleText = findViewById(R.id.subtitleText);
        subtitleText.setText(username);
        String urlImage = prefs.getString("profilePic", "");
        ImageView userProfile = findViewById(R.id.userProfilePic);

        if (urlImage != ""){
            Bitmap myBitmap = BitmapFactory.decodeFile(urlImage);
            userProfile.setImageBitmap(myBitmap);
        }






        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity4.class);
                startActivity(intent);

            }
        });

        cameraFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runMainSequence();
            }
        });

        ratingLink.setRating((float) 0.0);
        ratingLink.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingLink.setRating((float) 0.0);
                runHealthCheck();
            }
        });

        seeIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runIntroSequence();
            }
        });




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

         */
        /*bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);

                switch (item.getItemId()) {
                    case R.id.home_page_nav: {
                        Toast.makeText(getApplicationContext(), "Home!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case R.id.camera_page_nav: {
                        Toast.makeText(getApplicationContext(), "Camera!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case R.id.info_page_nav: {
                        runFAQ();
                    }
                    break;
                    case R.id.settings_page_nav: {
                        Toast.makeText(getApplicationContext(), "Settings!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                return true;
            }
        });

         */


        //Also, used SharedPreferences to do login flow!!




    }

    private void runCreateAccount(){
        Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
        startActivity(intent);
    }

    private void runMainSequence(){
        Intent intent = new Intent(getApplicationContext(), MainSequence.class);
        startActivity(intent);
    }

    private void runFAQ(){
        Intent intent = new Intent(getApplicationContext(), FAQ.class);
        startActivity(intent);
    }

    private void runIntroSequence() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), IntroductorySequence.class);
        overridePendingTransition(0, 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void runHealthCheck(){
        //What preference should I use?
        Intent intent = new Intent(getApplicationContext(), CheckIn.class);
        startActivity(intent);
    }


}

