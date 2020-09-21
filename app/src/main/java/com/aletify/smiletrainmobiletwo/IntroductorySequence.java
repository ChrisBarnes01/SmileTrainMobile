package com.aletify.smiletrainmobiletwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroductorySequence extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory_sequence);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupOnboardingItems();

        final ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                }
                else{
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String avatarName = prefs.getString("avatarId", "icon_1");
        int resourceId = getApplicationContext().getResources().getIdentifier(avatarName, "drawable", getApplicationContext().getPackageName());

        OnboardingItem page1 = new OnboardingItem();
        //page1.setTitle("Welcome to Aletify.");
        page1.setDescription("Hola! Bienvenido a Altefy! ¡Estamos muy contentos de tenerte aquí! Esta aplicación fue diseñada para hacer que tu tratamiento ortodóncico sea tan fácil y divertido como sea posible");
        page1.setImage(resourceId);

        OnboardingItem page2 = new OnboardingItem();
        //page2.setTitle("This app is an early stage demo.");
        page2.setDescription("Será una forma fácil de comunicarte con tu Doctor y sus colaboradores durante el tratamiento. Hemos incluido muchas ayudas para que esa comunicación sea más efectiva. Este corto tutorial te guiará mientras aprendes a manejar Altefy y asegurará que la experiencia sea un éxito.");
        page2.setImage(resourceId);

        OnboardingItem page3 = new OnboardingItem();
        page3.setDescription("Tienes una pregunta que no está aquí? Oprime el botón “OTRO” y mándanos un mensaje. Estaremos encantados de contestar cualquier pregunta que se te pueda ocurrir.");
        page3.setImage(resourceId);

        onboardingItems.add(page1);
        onboardingItems.add(page2);
        onboardingItems.add(page3);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);

    }

    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
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
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView)layoutOnboardingIndicators.getChildAt(i);
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
}