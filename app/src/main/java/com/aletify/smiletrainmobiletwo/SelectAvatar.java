package com.aletify.smiletrainmobiletwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SelectAvatar extends AppCompatActivity {
    CardView view1;
    CardView view2;
    CardView view3;
    CardView view4;
    CardView view5;
    CardView view6;
    TextView message;
    Button jumpButton;
    boolean selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);


        view1 = (CardView) findViewById(R.id.view1);
        view2 = (CardView) findViewById(R.id.view2);
        view3 = (CardView) findViewById(R.id.view3);
        view4 = (CardView) findViewById(R.id.view4);
        view5 = (CardView) findViewById(R.id.view5);
        view6 = (CardView) findViewById(R.id.view6);
        message = (TextView) findViewById(R.id.welcomeMessage);
        jumpButton = (Button) findViewById(R.id.confirm_to_instructions_button);


        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");

        String displayMessage = "Bienvenidos a Altefy, " + firstName;
        message.setText(displayMessage);

        selected = false;

        jumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selected){
                    Toast.makeText(SelectAvatar.this, "Please Select an Avatar", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), IntroductorySequence.class);
                    startActivity(intent);
                }
            }
        });



        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                view1.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("avatarId", "icon_1");
                editor.apply();
                selected = true;
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                view2.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("avatarId", "icon_2");
                editor.apply();
                selected = true;
            }
        });
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                view3.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("avatarId", "icon_3");
                editor.apply();
                selected = true;
            }
        });
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                view4.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("avatarId", "icon_4");
                editor.apply();
                selected = true;
            }
        });
        view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                view5.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("avatarId", "icon_5");
                editor.apply();
                selected = true;
            }
        });
        view6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                view6.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("avatarId", "icon_6");
                editor.apply();
                selected = true;
            }
        });
    }

    private void clearAll(){
        view1.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        view2.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        view3.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        view4.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        view5.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        view6.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
    }

}