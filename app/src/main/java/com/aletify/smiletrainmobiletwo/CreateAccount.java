package com.aletify.smiletrainmobiletwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateAccount extends AppCompatActivity {

    private CreateAccountAdapter createAccountAdapter;
    private LinearLayout layoutProgressIndicators;
    private MaterialButton buttonOnboardingAction;
    private ViewPager2 createAccountViewPager;
    private FirebaseAuth mAuth;
    String globalUsername;
    String globalPassword;
    CardView view1;
    CardView view2;
    CardView view3;
    CardView view4;
    CardView view5;
    CardView view6;
    private String globalFirstName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        layoutProgressIndicators = findViewById(R.id.layoutProgressIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupCreateAccountItems();

        createAccountViewPager = findViewById(R.id.createAccountViewPager);
        createAccountViewPager.setAdapter(createAccountAdapter);

        setupProgressIndicators();
        setCurrentProgressIndicator(0);

        createAccountViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentProgressIndicator(position);
            }
        });

        //Stop User From Swiping Forward or Back
        createAccountViewPager.setUserInputEnabled(false);

        //This sets mAuth.
        mAuth = FirebaseAuth.getInstance();

    }

    private void setupCreateAccountItems() {
        List<CreateAccountItem> createAccountItems = new ArrayList<>();

        CreateAccountItem page1 = new CreateAccountItem();
        page1.setTitle("Ingresar");
        page1.setDescription("Use los detalles que el Dr. le informó para autenticarse");
        page1.setInput1("USUARIO");
        page1.setInput_prefill_1("Usuario");
        page1.setInput2("CONTRASEÑA TEMPORAL");
        page1.setInput_prefill_2("Contraseña Temporal");
        page1.setSelectCharacterItem(false);

        CreateAccountItem page2 = new CreateAccountItem();
        page2.setTitle("Configurar clave");
        page2.setDescription("Por favor cree una nueva contraseña para su cuenta");
        page2.setInput1("NUEVA CONTRASEÑA");
        page2.setInput_prefill_1("Nueva contraseña");
        page2.setInput2("REPETIR CONTRASEÑA");
        page2.setInput_prefill_2("Repetir contraseña");
        page2.setSelectCharacterItem(false);


        CreateAccountItem page3 = new CreateAccountItem();
        page3.setTitle("Configura tu perfil");
        page3.setInput1("PRIMER NOMBRE");
        page3.setInput_prefill_1("Primer Nombre");
        page3.setInput2("APELLIDO");
        page3.setInput_prefill_2("Apellido");
        page3.setInput3("Número WhatsApp");
        page3.setInput_prefill_3("+57-MMM-XXX-XXXX");
        page3.setSelectCharacterItem(false);

        CreateAccountItem page4 = new CreateAccountItem();
        page4.setSelectCharacterItem(true);



        createAccountItems.add(page1);
        createAccountItems.add(page2);
        createAccountItems.add(page3);
        createAccountItems.add(page4);

        createAccountAdapter = new CreateAccountAdapter(createAccountItems);

    }

    private void setupProgressIndicators(){
        ImageView[] indicators = new ImageView[createAccountAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutProgressIndicators.addView(indicators[i]);
        }
    }

    private void clearAll(){
        view1.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        view2.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        view3.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        view4.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        view5.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        view6.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
    }



    private void setCurrentProgressIndicator(int index){
        int childCount = layoutProgressIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView)layoutProgressIndicators.getChildAt(i);
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

        if (index == 0){
            buttonOnboardingAction.setText("Siguiente");
            buttonOnboardingAction.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    EditText username = (EditText) findViewById(R.id.input1CreateAccount);
                    EditText password = (EditText) findViewById(R.id.input2CreateAccount);

                    final String usernameInput = username.getText().toString();
                    final String usernameString =  usernameInput + "@test.com";
                    final String passwordString = password.getText().toString();

                    try {
                        mAuth.signInWithEmailAndPassword(usernameString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                                    globalUsername = usernameInput;
                                    globalPassword = passwordString;

                                    if(createAccountViewPager.getCurrentItem() + 1 < createAccountAdapter.getItemCount()){
                                        createAccountViewPager.setCurrentItem(createAccountViewPager.getCurrentItem() + 1);
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), "Registration Failed. Try Again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Failed to Register", Toast.LENGTH_LONG).show();
                    };
                }
            });
        }
        else if (index == 1){
            buttonOnboardingAction.setText("Siguiente");
            buttonOnboardingAction.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    EditText firstPassword = (EditText) findViewById(R.id.input1CreateAccount);
                    EditText secondPassword = (EditText) findViewById(R.id.input2CreateAccount);

                    final String firstPasswordString = firstPassword.getText().toString();
                    final String secondPasswordString = secondPassword.getText().toString();

                    AuthCredential credential = EmailAuthProvider.getCredential(globalUsername + "@test.com", globalPassword);

                    if (firstPasswordString.length() < 6){
                        Toast.makeText(getApplicationContext(), "Passwords Must Have At Least 6 Characters", Toast.LENGTH_LONG).show();
                    }
                    else if (firstPasswordString.equals(secondPasswordString)){
                        try {
                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                user.updatePassword(firstPasswordString).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                                            if(createAccountViewPager.getCurrentItem() + 1 < createAccountAdapter.getItemCount()){
                                                                createAccountViewPager.setCurrentItem(createAccountViewPager.getCurrentItem() + 1);
                                                            }
                                                        } else {
                                                            Log.d("TAG", "Error password not updated");
                                                            Toast.makeText(getApplicationContext(), "Failed to Change Password :" + task.getException(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Log.d("TAG", "Error auth failed");
                                                Toast.makeText(getApplicationContext(), "Failed to Change Password. -- addOnComplete Listener", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Failed to Change Password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Passwords Do Not Match", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else if (index == 2){
            buttonOnboardingAction.setText("Siguiente");
            buttonOnboardingAction.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    EditText firstName = (EditText) findViewById(R.id.input1CreateAccount);
                    EditText lastName = (EditText) findViewById(R.id.input2CreateAccount);
                    EditText whatsAppNumber = (EditText) findViewById(R.id.input3CreateAccount);

                    final String firstNameString = firstName.getText().toString();
                    final String lastNameString = lastName.getText().toString();
                    final String whatsAppNumberString = whatsAppNumber.getText().toString();

                    //Mock Dates
                    List<CalendarObject> calendarObjectList = new ArrayList<>();


                    //1 Calendar Object

                    CalendarObject object1 = new CalendarObject(CalendarObject.PHYSICAL_APPOINTMENT, "260");

                    //2 Calendar Object 2
                    CalendarObject object2 = new CalendarObject(CalendarObject.PICTURES_DUE, "261");

                    //Calendar Object 3
                    CalendarObject object3 = new CalendarObject(CalendarObject.PHYSICAL_APPOINTMENT, "280");

                    calendarObjectList.add(object1);
                    calendarObjectList.add(object2);
                    calendarObjectList.add(object3);


                    if (firstNameString.length() < 1 || lastNameString.length() < 1 || whatsAppNumber.length() < 1){
                        Toast.makeText(getApplicationContext(), "Please fill out All Parameters", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("patients");
                        User user = new User(globalUsername, firstNameString, lastNameString, whatsAppNumberString);
                        user.calendarObjectList = calendarObjectList;
                        mDatabase.child(globalUsername).setValue(user);
                        globalFirstName = firstNameString;


                        //UPDATE SHARED PREFERENCES
                        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("username", globalUsername);
                        editor.apply();
                        editor.putBoolean("LoggedIn", true);
                        editor.apply();

                        if(createAccountViewPager.getCurrentItem() + 1 < createAccountAdapter.getItemCount()){
                            createAccountViewPager.setCurrentItem(createAccountViewPager.getCurrentItem() + 1);
                        }
                        else{
                            startActivity(new Intent(getApplicationContext(), IntroductorySequence.class));
                            finish();
                        }
                    }
                }
            });
        }
        else{
            //Manage Select Avatar

            TextView message = (TextView) findViewById(R.id.welcomeMessage);
            String messageToSend = "Bienvenidos a Altefy, " + globalFirstName;

            view1 = (CardView) findViewById(R.id.view1);
            view2 = (CardView) findViewById(R.id.view2);
            view3 = (CardView) findViewById(R.id.view3);
            view4 = (CardView) findViewById(R.id.view4);
            view5 = (CardView) findViewById(R.id.view5);
            view6 = (CardView) findViewById(R.id.view6);


            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                    view1.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("avatarId", R.id.view1);
                    editor.apply();
                }
            });
            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                    view2.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("avatarId", R.id.view2);
                    editor.apply();
                }
            });
            view3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                    view3.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("avatarId", R.id.view3);
                    editor.apply();
                }
            });
            view4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                    view4.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("avatarId", R.id.view4);
                    editor.apply();
                }
            });
            view5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                    view5.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("avatarId", R.id.view5);
                    editor.apply();
                }
            });
            view6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                    view6.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("avatarId", R.id.view6);
                    editor.apply();
                }
            });


            buttonOnboardingAction.setText("Siguiente");
            buttonOnboardingAction.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){


                    if(createAccountViewPager.getCurrentItem() + 1 < createAccountAdapter.getItemCount()){
                        createAccountViewPager.setCurrentItem(createAccountViewPager.getCurrentItem() + 1);
                    }
                    else{
                        startActivity(new Intent(getApplicationContext(), IntroductorySequence.class));
                        finish();
                    }

                }
            });
        }
    }
}