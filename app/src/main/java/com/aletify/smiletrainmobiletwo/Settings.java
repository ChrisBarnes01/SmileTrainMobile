package com.aletify.smiletrainmobiletwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {


    ImageView arrowImage;
    LinearLayout expandableView;
    CardView cardView;
    EditText firstName;
    EditText lastName;
    Button nameButton;

    ImageView arrowImage2;
    LinearLayout expandableView2;
    CardView cardView2;
    EditText ogpassword;
    EditText newpassword;
    Button nameButton2;

    String globalUsername;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        arrowImage = (ImageView) findViewById(R.id.dropdown_arrow_sign);;
        expandableView = (LinearLayout) findViewById(R.id.dropdown_expandable_view);;
        cardView = (CardView) findViewById(R.id.dropdown_cardView);;
        firstName = (EditText) findViewById(R.id.input1CreateAccount);
        lastName = (EditText) findViewById(R.id.input2CreateAccount);
        nameButton = (Button) findViewById(R.id.setNewNames);


        arrowImage2 = (ImageView) findViewById(R.id.dropdown_arrow_sign2);;
        expandableView2 = (LinearLayout) findViewById(R.id.dropdown_expandable_view2);;
        cardView2 = (CardView) findViewById(R.id.dropdown_cardView2);;
        ogpassword = (EditText) findViewById(R.id.input1CreateAccount2);
        newpassword = (EditText) findViewById(R.id.input2CreateAccount2);
        nameButton2 = (Button) findViewById(R.id.setNewNames2);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        globalUsername = prefs.getString("username", "userId");


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    arrowImage.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    arrowImage.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstNameString = firstName.getText().toString();
                final String lastNameString = lastName.getText().toString();

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("patients");
                mDatabase.child(globalUsername).child("firstName").setValue(firstNameString);
                mDatabase.child(globalUsername).child("lastName").setValue(lastNameString);
                Toast.makeText(getApplicationContext(), "se cambió el nombre", Toast.LENGTH_SHORT);
            }
        });



        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView2.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
                    expandableView2.setVisibility(View.VISIBLE);
                    arrowImage2.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                } else {
                    TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
                    expandableView2.setVisibility(View.GONE);
                    arrowImage2.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });

        nameButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



                final String originalPassword = ogpassword.getText().toString();
                final String newPassword = newpassword.getText().toString();

                AuthCredential credential = EmailAuthProvider.getCredential(globalUsername + "@test.com", originalPassword);

                if (newPassword.length() < 6 || originalPassword.length() < 6){
                    Toast.makeText(getApplicationContext(), "Passwords Must Have At Least 6 Characters", Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Failed to Change Password :" + task.getException(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Failed to Change Password. Incorrect Original Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Failed to Change Password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



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