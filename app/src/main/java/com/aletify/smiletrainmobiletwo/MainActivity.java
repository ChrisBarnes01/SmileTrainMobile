package com.aletify.smiletrainmobiletwo;

import android.Manifest;
import android.app.ActivityOptions;
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
import androidx.core.util.Pair;

import android.text.Spannable;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LinearLayout layoutCheckInIndicators;

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
        final TextView numberOfDaysNotification = findViewById(R.id.home_day_reminder_card_text);
        final Button checkInButton = findViewById(R.id.home_check_in_card_button);
        final TextView dayOfWeekLabel = findViewById(R.id.homepage_day_of_week_label);
        final TextView monthYearLabel = findViewById(R.id.homepage_month_and_year_label);
        final TextView dateLabel = findViewById(R.id.homepage_date_label);
        layoutCheckInIndicators = findViewById(R.id.home_check_in_record_view);

        Calendar myCalendar = Calendar.getInstance();
        dayOfWeekLabel.setText(myCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));//myCalendar.get(Calendar.DAY_OF_WEEK));
        monthYearLabel.setText(myCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + myCalendar.get(Calendar.YEAR));
        Date date = new Date();
        dateLabel.setText((String) DateFormat.format("dd",   date));



        getPermissions();
        setupCheckInRecordIndicators();


        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runHealthCheck();
            }
        });

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
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);

                switch (item.getItemId()) {
                    case R.id.home_page_nav: {
                        Toast.makeText(getApplicationContext(), "Home!", Toast.LENGTH_SHORT).show();
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

        //Set The Text for number of days, and the right card:

        int numberOfDaysUntilCheckIn = 2;

        String first = Integer.valueOf(numberOfDaysUntilCheckIn) + " Days";
        String next = ", until you will need to send in your new aligner photos";

        numberOfDaysNotification.setText(first + next, TextView.BufferType.SPANNABLE);
        Spannable s = (Spannable)numberOfDaysNotification.getText();
        int start = 0;
        int end = first.length();
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);




        //Also, used SharedPreferences to do login flow!!




    }

    private boolean permissionsGranted(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            return false;
        }
        return true;
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

    private void runCreateAccount(){
        Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
        startActivity(intent);
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

    private void runMainSequence(){
        Intent intent = new Intent(getApplicationContext(), MainSequence.class);
        startActivity(intent);
    }

    private void runFAQ(){

        Intent intent = new Intent(getApplicationContext(), FAQ.class);
        startActivity(intent);

    }

    private void runSettings(){
        Intent intent = new Intent(getApplicationContext(), Settings.class);
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
        Intent intent = new Intent(getApplicationContext(), CheckIn3.class);
        startActivity(intent);
    }






    //Indicators
    /*
    private void setupCheckInRecordIndicators(){
        ImageView[] indicators = new ImageView[7];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        layoutParams.weight = 1;


        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutCheckInIndicators.addView(indicators[i]);
        }
    }
     */


    private void setupCheckInRecordIndicators(){
        String[] daysOfWeek = {"L", "M", "M", "J", "V", "S", "D"};
        LinearLayout[] indicators = new LinearLayout[daysOfWeek.length];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        layoutParams.weight = 1;

        //ImageView[] indicators = new ImageView[7];



        for (int i = 0; i < daysOfWeek.length; i++){
            //Create New Layout, then add a new ImageView and TextView
            LinearLayout layoutEntry = new LinearLayout(getApplicationContext());
            layoutEntry.setPadding(5,5,5,5);
            layoutEntry.setOrientation(LinearLayout.VERTICAL);


            //Now, add the appropriate Drawable Image
            ImageView imageToBeAdded = new ImageView(getApplicationContext());
            imageToBeAdded.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.circle_with_checkmark
            ));
            imageToBeAdded.setPadding(5, 10, 5, 0);
            imageToBeAdded.setMaxHeight(10);
            imageToBeAdded.setMaxWidth(10);

            layoutEntry.addView(imageToBeAdded);

            //Get Text To Add, and Add it to the New Linear Layout
            TextView dayOfWeek = new TextView(getApplicationContext());
            dayOfWeek.setText(daysOfWeek[i]);
            dayOfWeek.setGravity(Gravity.CENTER);
            layoutEntry.addView(dayOfWeek);


            //layoutEntry.setBackgroundResource(R.drawable.border_background);
            //Add New Linear View to Overall View
            layoutEntry.setLayoutParams(layoutParams);
            layoutCheckInIndicators.addView(layoutEntry);
        }
    }

    /*
    private void setCurrentCheckInRecordIndicator(int index){
        int childCount = layoutCheckInIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView)layoutCheckInIndicators.getChildAt(i);
            if (i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active)
                );
            }
            else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive)
                );
            }
        }

        if (index == onboardingAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Start");
        }
        else{
            buttonOnboardingAction.setText("Next");
        }
    }
*/

}

