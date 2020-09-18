package com.aletify.smiletrainmobiletwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class TestAlligner extends AppCompatActivity {

    private MainSequenceAdapter mainAdapter;
    private LinearLayout layoutMainIndicators;
    private MaterialButton buttonMainAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sequence);

        layoutMainIndicators = findViewById(R.id.layoutMainIndicators);
        buttonMainAction = findViewById(R.id.buttonMainAction);

        setupMainItems();

        final ViewPager2 mainViewPager = findViewById(R.id.mainViewPager);
        mainViewPager.setAdapter(mainAdapter);

        setupMainIndicators();
        setCurrentMainIndicator(0);

        mainViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentMainIndicator(position);
            }
        });

        buttonMainAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mainViewPager.getCurrentItem() + 1 < mainAdapter.getItemCount()){
                    mainViewPager.setCurrentItem(mainViewPager.getCurrentItem() + 1);
                }
                else{
                    startActivity(new Intent(getApplicationContext(), CameraActivity4.class));
                    finish();
                }

            }
        });
    }



    private void setupMainItems() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem page1 = new OnboardingItem();
        page1.setDescription("Coloca el alineador e tu mano. Pasa el dedo sobre los bordes del alineador. ¿Sientes algún punto filoso o áspero?");
        page1.setImage(R.drawable.pre_1);

        OnboardingItem page2 = new OnboardingItem();
        page2.setDescription("Utiliza una lima para las uñas para suavizar la aspereza. Ten cuidado de no limar excesivamente, revisando continuamente con el dedo mientras pules los bordes.");
        page2.setImage(R.drawable.pre_2);

        OnboardingItem page3 = new OnboardingItem();
        page3.setDescription("Colócate el alineador y muerde suavemente el asentador elástico que te entregó tu Doctor. Una vez haya asentado totalmente tu alineador, observa si quedan espacios (por falta de asentamiento) entre el plástico y los bordes de tus dientes. ¿Permanece el espacio, a pesar de morder sobre el asentador elástico?");
        page3.setImage(R.drawable.pre_3);

        OnboardingItem page4 = new OnboardingItem();
        page4.setDescription("Repite el proceso de morder sobre el asentador elástico, en el lugar donde se observa la falta de asentamiento, intentando mejorar el ajuste. No te preocupes si no lo logras fácilmente, a veces requiere varios intentos.");
        page4.setImage(R.drawable.pre_4);

        OnboardingItem page5 = new OnboardingItem();
        page5.setDescription("Aún ves algún desajuste o espacio del alineador? ");
        page5.setImage(-1111);

        onboardingItems.add(page1);
        onboardingItems.add(page2);
        onboardingItems.add(page3);
        onboardingItems.add(page4);
        onboardingItems.add(page5);

        mainAdapter = new MainSequenceAdapter(onboardingItems);

    }

    private void setupMainIndicators(){
        ImageView[] indicators = new ImageView[mainAdapter.getItemCount()];
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
            layoutMainIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentMainIndicator(int index){
        int childCount = layoutMainIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView)layoutMainIndicators.getChildAt(i);
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

        if (index == mainAdapter.getItemCount()-1){
            buttonMainAction.setText("Comenzar");
        }
        else{
            buttonMainAction.setText("Seguir");
        }
    }
}