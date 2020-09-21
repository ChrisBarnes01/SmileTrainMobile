package com.aletify.smiletrainmobiletwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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



import java.util.ArrayList;
import java.util.List;

public class LogInFinal extends AppCompatActivity {
        private CreateAccountAdapter createAccountAdapter;
        private LinearLayout layoutProgressIndicators;
        private MaterialButton buttonOnboardingAction;
        private ViewPager2 createAccountViewPager;
        private FirebaseAuth mAuth;
        private TextView LinkButton;
        String globalUsername;
        String globalPassword;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_log_in_final);

            mAuth = FirebaseAuth.getInstance();
            layoutProgressIndicators = findViewById(R.id.layoutProgressIndicators);
            buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

            setupCreateAccountItems();

            createAccountViewPager = findViewById(R.id.createAccountViewPager);
            createAccountViewPager.setAdapter(createAccountAdapter);

            setCurrentProgressIndicator();



            //Stop User From Swiping Forward or Back
            createAccountViewPager.setUserInputEnabled(false);

            LinkButton = (TextView) findViewById(R.id.clickable_text_link_for_login);
            LinkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                    startActivity(intent);
                }
            });



        }

        private void setupCreateAccountItems() {
            List<CreateAccountItem> createAccountItems = new ArrayList<>();

            CreateAccountItem page1 = new CreateAccountItem();
            page1.setTitle("Iniciar Sesion");
            page1.setInput1("USUARIO");
            page1.setInput_prefill_1("Usuario");
            page1.setInput2("CONTRASEÑA");
            page1.setInput_prefill_2("Contraseña Temporal");

            createAccountItems.add(page1);

            createAccountAdapter = new CreateAccountAdapter(createAccountItems);

        }



        private void setCurrentProgressIndicator(){
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

                                        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("username", usernameInput);
                                        editor.apply();
                                        editor.putBoolean("LoggedIn", true);
                                        editor.apply();

                                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

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

    }