package com.aletify.smiletrainmobiletwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING;

public class TestAlligner extends AppCompatActivity {

    private static MainSequenceAdapter mainAdapter;
    private LinearLayout layoutMainIndicators;
    private MaterialButton buttonMainAction;
    private static Context mContext;
    private static List<OnboardingItem> onboardingItems;
    private static int currentIndex;
    private static int currentLimit;
    private static ViewPager2 mainViewPager;





    private static ToggleButton toggle1;
    public static ToggleButton toggle2;
    private static ToggleButton toggle3;
    private static ToggleButton toggle4;
    public static ToggleButton toggle5;
    public static ToggleButton toggle6;
    public static ToggleButton toggle7;
    public static ToggleButton toggle8;



    private android.app.FragmentManager supportFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sequence);
        currentIndex = 0;
        currentLimit = 0;
        mContext = this;
        onboardingItems = new ArrayList<>();
        setupMainItems();
        mainViewPager = findViewById(R.id.mainViewPager);



        layoutMainIndicators = findViewById(R.id.layoutMainIndicators);
        buttonMainAction = findViewById(R.id.buttonMainAction);


        mainViewPager.setAdapter(mainAdapter);
        mainViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override

            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (mainViewPager.getCurrentItem() > currentLimit) {
                    mainViewPager.setCurrentItem(currentLimit);
                }


                /*if (state == SCROLL_STATE_DRAGGING && mainViewPager.getCurrentItem() == 0) {
                    mainViewPager.setUserInputEnabled(false);
                } else {
                    mainViewPager.setUserInputEnabled(true);
                }*/
            }
            /*@Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffset > 0 && mainViewPager.getCurrentItem() == currentLimit){
                    Toast.makeText(getApplicationContext(), "Swipe to next", Toast.LENGTH_SHORT).show();
                    mainViewPager.setUserInputEnabled(false);
                }
                else{
                    mainViewPager.setUserInputEnabled(true);
                }


            }

             */
        });

        //setupMainIndicators();
        supportFragmentManager = getFragmentManager();
        //setCurrentMainIndicator(0);


        mainViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //setCurrentMainIndicator(position);
            }
        });

        buttonMainAction.setEnabled(false);
        buttonMainAction.setText("Seguir");
        buttonMainAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mainViewPager.getCurrentItem() + 1 < mainAdapter.getItemCount()){
                    mainViewPager.setCurrentItem(mainViewPager.getCurrentItem() + 1);
                }
                else{
                    startActivity(new Intent(getApplicationContext(), reviewInstructions.class));
                    finish();
                }

            }
        });
    }

    public static void setToggles(ToggleButton button1, ToggleButton button2){

        if(currentIndex == 0) {


            toggle1 = button1;
            toggle1.setText("No");
            toggle1.setTextOn("No");
            toggle1.setTextOff("No");

            toggle2 = button2;
            toggle2.setText("Si");
            toggle2.setTextOn("Si");
            toggle2.setTextOff("Si");
            toggle1.setVisibility(View.VISIBLE);
            toggle2.setVisibility(View.VISIBLE);

            toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggle1.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

                        if (mainViewPager.getCurrentItem() + 1 < mainAdapter.getItemCount()) {
                            mainViewPager.setCurrentItem(mainViewPager.getCurrentItem() + 1);
                        }
                        if (currentLimit < 1){
                            currentLimit = 1;
                        };

                    } else {
                        toggle1.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    }
                }
            });

            toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggle2.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                        Intent toPicture = new Intent(mContext.getApplicationContext(), reviewInstructions.class);
                        mContext.startActivity(toPicture);
                    }
                }
            });
        }
        else if (currentIndex == 1){
            toggle3 = button1;
            toggle4 = button2;

            toggle3.setVisibility(View.INVISIBLE);
            toggle4.setVisibility(View.VISIBLE);

            toggle4.setText("Completado");
            toggle4.setTextOn("Completado");
            toggle4.setTextOff("Completado");

            toggle4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggle4.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                        if (mainViewPager.getCurrentItem() + 1 < mainAdapter.getItemCount()) {
                            mainViewPager.setCurrentItem(mainViewPager.getCurrentItem() + 1);
                        }
                        if (currentLimit < 2){
                            currentLimit = 2;
                        };
                    } else {
                        toggle4.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    }
                }
            });



        }
        else if (currentIndex == 2){
            toggle5 = button1;
            toggle6 = button2;

            toggle5.setVisibility(View.VISIBLE);
            toggle6.setVisibility(View.VISIBLE);

            toggle5.setText("No");
            toggle5.setTextOn("No");
            toggle5.setTextOff("No");

            toggle6.setText("Si");
            toggle6.setTextOn("Si");
            toggle6.setTextOff("Si");

            toggle5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggle5.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                        if (mainViewPager.getCurrentItem() + 1 < mainAdapter.getItemCount()) {
                            mainViewPager.setCurrentItem(mainViewPager.getCurrentItem() + 1);
                        }
                        if (currentLimit < 3){
                            currentLimit = 3;
                        };
                    } else {
                        toggle5.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    }
                }
            });

            toggle6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggle6.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                        Intent toPicture = new Intent(mContext.getApplicationContext(), reviewInstructions.class);
                        mContext.startActivity(toPicture);
                    }
                }
            });


        }
        else if (currentIndex == 3){
            toggle7 = button1;
            toggle8 = button2;

            toggle7.setVisibility(View.VISIBLE);
            toggle8.setVisibility(View.VISIBLE);
            toggle7.setText("No");
            toggle7.setTextOn("No");
            toggle7.setTextOff("No");

            toggle8.setText("Si");
            toggle8.setTextOn("Si");
            toggle8.setTextOff("Si");

            //Take secondary picture
            toggle8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggle8.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                        Intent toPicture = new Intent(mContext.getApplicationContext(), SecondaryCamera.class);
                        mContext.startActivity(toPicture);
                    }
                }
            });

            //Take Main Picture!
            toggle7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggle7.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                        Intent toPicture = new Intent(mContext.getApplicationContext(), reviewInstructions.class);
                        mContext.startActivity(toPicture);
                    }
                }
            });
        }




        currentIndex += 1;



    }


    private void setupMainItems() {
        OnboardingItem page1 = new OnboardingItem();
        page1.setDescription("Coloca el alineador e tu mano. Pasa el dedo sobre los bordes del alineador. ¿Sientes algún punto filoso o áspero?");
        page1.setIndex(0);
        page1.setImage(R.drawable.pre_1);


        OnboardingItem page2 = new OnboardingItem();
        page2.setDescription("Utiliza una lima para las uñas para suavizar la aspereza. Ten cuidado de no limar excesivamente, revisando continuamente con el dedo mientras pules los bordes.");
        page2.setImage(R.drawable.pre_2);
        page2.setIndex(1);


        OnboardingItem page3 = new OnboardingItem();
        page3.setDescription("Colócate el alineador y muerde suavemente el asentador elástico que te entregó tu Doctor. Una vez haya asentado totalmente tu alineador, observa si quedan espacios (por falta de asentamiento) entre el plástico y los bordes de tus dientes. ¿Permanece el espacio, a pesar de morder sobre el asentador elástico?");
        page3.setImage(R.drawable.pre_3);
        page3.setIndex(2);

        OnboardingItem page4 = new OnboardingItem();
        page4.setDescription("Repite el proceso de morder sobre el asentador elástico, en el lugar donde se observa la falta de asentamiento, intentando mejorar el ajuste. No te preocupes si no lo logras fácilmente, a veces requiere varios intentos. Aún ves algún desajuste o espacio del alineador? ");
        page4.setImage(R.drawable.pre_4);
        page4.setIndex(3);

/*
        OnboardingItem page5 = new OnboardingItem();
        page5.setDescription("Aún ves algún desajuste o espacio del alineador? ");
        page5.setImage(-1111);

        onboardingItems.add(page2);
        */
        onboardingItems.add(page1);
        onboardingItems.add(page2);
        onboardingItems.add(page3);
        onboardingItems.add(page4);
        mainAdapter = new MainSequenceAdapter(onboardingItems);


        //onboardingItems.add(page3);
        //onboardingItems.add(page4);
        //onboardingItems.add(page5);
        mainAdapter.notifyDataSetChanged();

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



        //TextView textTitleMainSequence = findViewById(R.id.textTitleMainSequence);
        //textTitleMainSequence.setText("Ok, test.");

        /*ToggleButton toggle1 = findViewById(R.id.simpleToggleButton1);
        ToggleButton toggle2 = findViewById(R.id.simpleToggleButton2);


        toggle2.setText("Yes");
        toggle2.setTextOn("Yes");
        toggle2.setTextOff("Yes");



        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*
                if (isChecked) {
                    toggle2.setChecked(false);
                    toggle2.setTextColor(getResources().getColor(R.color.colorBlack));
                    toggle1.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    toggle2.setChecked(true);
                    toggle2.setTextColor(getResources().getColor(R.color.colorAccent));
                    toggle1.setTextColor(getResources().getColor(R.color.colorBlack));

                }
                 *//*
            }
        });
        */




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