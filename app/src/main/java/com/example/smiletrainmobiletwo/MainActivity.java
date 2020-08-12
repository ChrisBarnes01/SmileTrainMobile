package com.example.smiletrainmobiletwo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smiletrainmobiletwo.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart){
            runIntroSequence();
        }
        //final ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);

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
        //Toast.makeText(getApplicationContext(), urlImage, Toast.LENGTH_SHORT).show();
        Bitmap myBitmap = BitmapFactory.decodeFile(urlImage);
        userProfile.setImageBitmap(myBitmap);
        




        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), CameraActivity2.class);
                Intent intent = new Intent(getApplicationContext(), CameraActivity4.class);
                startActivity(intent);

            }
        });

        cameraFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runMainSequence();
                //Toast.makeText(getApplicationContext(), "Going to Camera Flow!!", Toast.LENGTH_SHORT).show();
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


        //Also, used SharedPreferences to do login flow!!


    }

    private void runMainSequence(){
        Intent intent = new Intent(getApplicationContext(), MainSequence.class);
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
        /*new AlertDialog.Builder(this).setTitle("StartingDialog")
                .setMessage("This should only be displayed once")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();

         */
    }

    private void runHealthCheck(){
        //What preference should I use?
        Intent intent = new Intent(getApplicationContext(), CheckIn.class);
        //overridePendingTransition(0, 0);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_);
        startActivity(intent);

    }


}

