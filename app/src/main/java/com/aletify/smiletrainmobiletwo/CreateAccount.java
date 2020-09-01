package com.aletify.smiletrainmobiletwo;

import android.content.Intent;
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

public class CreateAccount extends AppCompatActivity {

    private CreateAccountAdapter createAccountAdapter;
    private LinearLayout layoutProgressIndicators;
    private MaterialButton buttonOnboardingAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        layoutProgressIndicators = findViewById(R.id.layoutProgressIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupCreateAccountItems();

        final ViewPager2 createAccountViewPager = findViewById(R.id.createAccountViewPager);
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

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(createAccountViewPager.getCurrentItem() + 1 < createAccountAdapter.getItemCount()){
                    createAccountViewPager.setCurrentItem(createAccountViewPager.getCurrentItem() + 1);
                }
                else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity2.class));
                    finish();
                }
            }
        });
    }

    private void setupCreateAccountItems() {
        List<CreateAccountItem> createAccountItems = new ArrayList<>();

        CreateAccountItem page1 = new CreateAccountItem();
        page1.setTitle("Welcome to Aletify.");
        page1.setDescription("We're here to help you manage your dental health.");
        //page1.setInput1("First Input");
        //page1.setInput_prefill_2("prefill");
        page1.setInput2("Second Input");
        page1.setInput_prefill_2("prefill");
        //page1.setImage(R.drawable.hello);

        CreateAccountItem page2 = new CreateAccountItem();
        page2.setTitle("This app is an early stage demo.");
        //page2.setDescription("But, we're working to get the final version out to you.");
        //page2.setImage(R.drawable.construction);

        createAccountItems.add(page1);
        createAccountItems.add(page2);

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

        if (index == createAccountAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Start");
        }
        else{
            buttonOnboardingAction.setText("Next");
        }
    }
}