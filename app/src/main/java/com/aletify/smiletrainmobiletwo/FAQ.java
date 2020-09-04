//Modified from https://github.com/gifffert/ExpandableCardView/blob/master/app/src/main/java/ru/embersoft/expandablecardview/MainActivity.java

package com.aletify.smiletrainmobiletwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.AutoTransition;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;

import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FAQ extends AppCompatActivity {

    LinearLayout expandableView;
    ImageView arrowBtn;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);

        expandableView = findViewById(R.id.dropdown_expandable_view);
        arrowBtn = findViewById(R.id.dropdown_arrow_sign);
        cardView = findViewById(R.id.dropdown_cardView);

        

    }
}