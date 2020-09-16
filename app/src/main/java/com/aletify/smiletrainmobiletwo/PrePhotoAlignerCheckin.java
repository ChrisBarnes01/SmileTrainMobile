package com.aletify.smiletrainmobiletwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PrePhotoAlignerCheckin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_photo_aligner_checkin);

        final TextView clickHereText = (TextView)findViewById(R.id.clickable_text_link);
        final TextView nextButton = (TextView)findViewById(R.id.prev_aligner_button_to_camera);


        String first = "First Set of Aligners? ";
        String next = "Click Here";

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
                Intent intent = new Intent(getApplicationContext(), CameraActivity4.class);
                startActivity(intent);
            }
        }
        );


    }
}