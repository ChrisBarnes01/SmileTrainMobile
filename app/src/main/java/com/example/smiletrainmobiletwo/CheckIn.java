package com.example.smiletrainmobiletwo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckIn extends AppCompatActivity {

    List<String> questions = Arrays.asList(new String[]{"How are you today?", "How much pain do you have?"});
    List<Float> responses;
    Boolean hasBeenPressed;
    Boolean isDone;
    Integer index;
    Boolean needsReporting;
    TextView healthquestion;
    RatingBar ratingBar;
    Button checkinButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        hasBeenPressed = false;
        index = 0;
        responses = new ArrayList<>();
        isDone = false;
        needsReporting = false;

        healthquestion = findViewById(R.id.healthquestion);
        ratingBar = findViewById(R.id.ratingBar);
        healthquestion.setText(questions.get(index));
        checkinButton = findViewById(R.id.checkInButton);
        checkinButton.setText("Confirm");


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //String value = String.valueOf(v);
                //healthquestion.setText(value);
                hasBeenPressed = true;
            }
        });

        checkinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if the rating is filled!
                if (hasBeenPressed & isDone == false){
                    Float rating = ratingBar.getRating();
                    responses.add(rating);

                    if (index +1 < questions.size() ){
                        index += 1;
                        healthquestion.setText(questions.get(index));
                        ratingBar.setRating(0);
                        hasBeenPressed = false;
                        //Now, set something so that it doesn't break!!!
                    }
                    else{
                        ratingBar.setVisibility(View.INVISIBLE);

                        //Check to see health values;
                        double baseValue = 2.1;
                        for(int i = 0; i < responses.size(); i++){
                            if (responses.get(i) < baseValue){
                                needsReporting = true;
                            }
                        }

                        if (needsReporting == true){
                            healthquestion.setText("You reported feeling bad. Please use WhatsApp to notify your doctor.");
                            checkinButton.setText("Submit to whatsapp");
                            hasBeenPressed = false;
                            isDone = true;
                        }
                        else{
                           changeToManageHealth();
                        }


                    }
                }
                else if (hasBeenPressed == false && isDone == false) {
                    Toast.makeText(getApplicationContext(), "Please select a Health Condition", Toast.LENGTH_SHORT).show();
                }
                else if (isDone = true){
                    if (needsReporting){
                        String message = "Hello Doctor. The patient reported the following: ";
                        for(int i = 0; i < questions.size(); i++){
                            message += questions.get(i) + " " + String.valueOf(responses.get(i)) + "; ";
                        }


                        openwhatsapp(getCurrentFocus(), message);
                        //Put a wait here
                        changeToManageHealth();

                    }
                    else{
                        jumpToHome();
                    }
                }

            }
        });
    }

    protected void changeToManageHealth(){
        healthquestion.setText("Great! Now Jump into managing your health! ");
        checkinButton.setText("Continue");
        hasBeenPressed = false;
        isDone = true;
        needsReporting = false;
    }

    protected void openwhatsapp(View view, String message){
        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    protected void jumpToHome(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}