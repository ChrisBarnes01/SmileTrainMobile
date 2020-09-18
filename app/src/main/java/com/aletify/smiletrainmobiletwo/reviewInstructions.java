package com.aletify.smiletrainmobiletwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class reviewInstructions extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_instructions);

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
        //int avatarId = prefs.getInt("avatarId", R.id.view1);
        int avatarId = R.id.view1;

        OnboardingItem page0 = new OnboardingItem();
        page0.setImage(R.drawable.icon_1);
        page0.setTitle("Ahora tomemos fotos con tus nuevos alineadores puestos.");
        page0.setDescription("Antes de tomar las fotos, recuerda……");

        OnboardingItem page1 = new OnboardingItem();
        page1.setTitle("Limpiar la cámara de tu celular");
        page1.setImage(R.drawable.general_1);

        OnboardingItem page2 = new OnboardingItem();
        page2.setTitle("Asegúrate de estar en un lugar bien iluminado.");
        page2.setImage(R.drawable.general_2);

        OnboardingItem page3 = new OnboardingItem();
        page3.setTitle("Si tienes cabello largo, sujétalo en una cola de caballo.");
        page3.setImage(R.drawable.general_4);

        OnboardingItem page4 = new OnboardingItem();
        page4.setTitle("Sostén tu celular cerca a la cara del paciente.");
        page4.setImage(R.drawable.general_5);

        OnboardingItem page5 = new OnboardingItem();
        page5.setTitle("Sostén el teléfono a la altura de la cara del paciente.");
        page5.setImage(R.drawable.general_6);

        OnboardingItem page6 = new OnboardingItem();
        page6.setTitle("Mantén al alineador puesto para todas las fotos.");
        page6.setImage(R.drawable.general_7);

        OnboardingItem page7 = new OnboardingItem();
        page7.setTitle("Coloca el retractor de labios y tracciona hacia los lados y hacia atrás");
        page7.setImage(R.drawable.general_8);

        onboardingItems.add(page0);
        onboardingItems.add(page1);
        onboardingItems.add(page2);
        onboardingItems.add(page3);
        onboardingItems.add(page4);
        onboardingItems.add(page5);
        onboardingItems.add(page6);
        onboardingItems.add(page7);


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
            buttonOnboardingAction.setText("Tomar fotos");
        }
        else{
            buttonOnboardingAction.setText("siguiente");
        }
    }
}