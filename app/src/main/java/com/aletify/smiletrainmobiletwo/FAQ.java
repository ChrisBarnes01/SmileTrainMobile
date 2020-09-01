//Modified from https://github.com/gifffert/ExpandableCardView/blob/master/app/src/main/java/ru/embersoft/expandablecardview/MainActivity.java

package com.aletify.smiletrainmobiletwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FAQ extends AppCompatActivity {

    ConstraintLayout expandableView;
    Button arrowBtn;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);

        expandableView = findViewById(R.id.expandableView);
        arrowBtn = findViewById(R.id.arrowBtn);
        cardView = findViewById(R.id.cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    arrowBtn.setBackgroundResource(R.drawable.hello);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    arrowBtn.setBackgroundResource(R.drawable.construction);
                }
            }
        });
    }
}