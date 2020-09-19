package com.aletify.smiletrainmobiletwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TooEarlyForPictureNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_too_early_for_picture_notification);

        Button toCamera = (Button)findViewById(R.id.takePhotosAnywayLink);
        Button backToMain = (Button)findViewById(R.id.cancelPhotosLink);

        toCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCamera = new Intent(getApplicationContext(), PrePhotoAlignerCheckin.class);
                startActivity(toCamera);
            }
        });
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCamera = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toCamera);
            }
        });


    }
}