package com.aletify.smiletrainmobiletwo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StyleableRes;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class DropdownView extends ConstraintLayout {

    @StyleableRes
    int index0 = 0;
    @StyleableRes
    int index1 = 1;
    @StyleableRes
    int index2 = 2;


    TextView titleText;
    TextView messageText;
    TextView actionText;
    ImageView arrowImage;
    LinearLayout expandableView;
    CardView cardView;

    public DropdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.dropdown_view, this);

        int[] sets = { R.attr.actionText, R.attr.messageText, R.attr.titleText };
        TypedArray typedArray = context.obtainStyledAttributes(attrs, sets);
        CharSequence title = typedArray.getText(index2);
        CharSequence message = typedArray.getText(index1);
        CharSequence action = typedArray.getText(index0);
        typedArray.recycle();

        initComponents();

        setTitleText(title);
        setMessageText(message);
        setActionText(action);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    arrowImage.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    arrowImage.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });
    }

    private void initComponents() {

        titleText = (TextView) findViewById(R.id.dropdown_title);
        messageText = (TextView) findViewById(R.id.dropdown_message_text);;
        actionText = (TextView) findViewById(R.id.dropdown_action_item);;
        arrowImage = (ImageView) findViewById(R.id.dropdown_arrow_sign);;
        expandableView = (LinearLayout) findViewById(R.id.dropdown_expandable_view);;
        cardView = (CardView) findViewById(R.id.dropdown_cardView);;
    }

    public CharSequence getTitleText() {
        return titleText.getText();
    }

    public void setTitleText(CharSequence value) {
        titleText.setText(value);
    }

    public CharSequence getMessageText() {
        return messageText.getText();
    }

    public void setMessageText(CharSequence value) {
        messageText.setText(value);
    }

    public CharSequence getActionText() {
        return actionText.getText();
    }

    public void setActionText(CharSequence value) {
        actionText.setText(value);
    }
}
