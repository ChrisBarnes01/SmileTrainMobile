package com.example.smiletrainmobiletwo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainSequence extends AppCompatActivity {

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
        page1.setTitle("Para poder tomar fotos nítidas");
        page1.setDescription("Limpie la lente de la cámara con una tela");
        page1.setImage(R.drawable.main1);

        OnboardingItem page2 = new OnboardingItem();
        page2.setTitle("Para poder tomar fotos bien iluminadas");
        page2.setDescription("Sentase detrás de una luz");
        page2.setImage(R.drawable.main2);

        OnboardingItem page3 = new OnboardingItem();
        page3.setTitle("Para poder tomar fotos claras");
        page3.setDescription("Mantener la cara visible. Asegurate: - Amarrarte el pelo");
        page3.setImage(R.drawable.main3);

        OnboardingItem page4 = new OnboardingItem();
        page4.setTitle("Para poder tomar fotos no borrosas");
        page4.setDescription("Agarra el cellular cerca de su cuerpo");
        page4.setImage(R.drawable.main4);

        OnboardingItem page5 = new OnboardingItem();
        page5.setTitle("Para poder tomar fotos alineadas");
        page5.setDescription("Agarra el cellular a la altura de su cara");
        page5.setImage(R.drawable.main5);

        OnboardingItem page6 = new OnboardingItem();
        page6.setTitle("Para ver los alineadores");
        page6.setDescription("Coloque los alineadores superior e inferior");
        page6.setImage(R.drawable.main6);

        OnboardingItem page7 = new OnboardingItem();
        page7.setTitle("Para poder tomar fotos detalladas");
        page7.setDescription("Coloca los retractores y hace fuerza hacia afuera para que se vea la mayor parte posible de sus dientes");
        page7.setImage(R.drawable.main7);

        onboardingItems.add(page1);
        onboardingItems.add(page2);
        onboardingItems.add(page3);
        onboardingItems.add(page4);
        onboardingItems.add(page5);
        onboardingItems.add(page6);
        onboardingItems.add(page7);


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