package com.aletify.smiletrainmobiletwo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreateAccountAdapter extends RecyclerView.Adapter<CreateAccountAdapter.OnboardingViewHolder>{

    private List<CreateAccountItem> onboardingItems;

    public CreateAccountAdapter(List<CreateAccountItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_create_account, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    class OnboardingViewHolder extends RecyclerView. ViewHolder{
        private TextView textTitle;
        private TextView textDescription;
        private TextView input1Text;
        private TextView input2Text;
        private TextView input3Text;

        private EditText input1;
        private EditText input2;
        private EditText input3;

        OnboardingViewHolder(@NonNull View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitleCreateAccount);
            textDescription = itemView.findViewById(R.id.textDescriptionCreateAccount);
            input1Text = itemView.findViewById(R.id.inputText1CreateAccount);
            input2Text = itemView.findViewById(R.id.inputText2CreateAccount);
            input3Text = itemView.findViewById(R.id.inputText3CreateAccount);

            input1 = itemView.findViewById(R.id.input1CreateAccount);
            input2 = itemView.findViewById(R.id.input2CreateAccount);
            input3 = itemView.findViewById(R.id.input3CreateAccount);

        }

        void setOnboardingData(CreateAccountItem onboardingItem){

            //TEXT TITLE
            if (onboardingItem.getTitle() == null){
                textTitle.setVisibility(View.GONE);
            }
            else{
                textTitle.setText(onboardingItem.getTitle());
            }

            //TEXT DESCRIPTION
            if (onboardingItem.getDescription() == null){
                textDescription.setVisibility(View.GONE);
            }
            else{
                textDescription.setText(onboardingItem.getDescription());
            }

            //INPUT1 TEXT
            if (onboardingItem.getInput1() == null){
                input1Text.setVisibility(View.GONE);
            }
            else {
                input1Text.setText(onboardingItem.getInput1());
            }

            //INPUT 1 Fillable
            if (onboardingItem.getInput_prefill_1() == null){
                input1.setVisibility(View.GONE);
            }
            else{
                input1.setHint(onboardingItem.getInput_prefill_1());
            }

            //INPUT2 TEXT
            if (onboardingItem.getInput2() == null){
                input2Text.setVisibility(View.GONE);
            }
            else {
                input2Text.setText(onboardingItem.getInput2());
            }

            //INPUT 2 Fillable
            if (onboardingItem.getInput_prefill_2() == null){
                input2.setVisibility(View.GONE);
            }
            else{
                input2.setHint(onboardingItem.getInput_prefill_2());
            }

            //INPUT3 TEXT
            if (onboardingItem.getInput3() == null){
                input3Text.setVisibility(View.GONE);
            }
            else {
                input3Text.setText(onboardingItem.getInput3());
            }

            //INPUT 3 Fillable
            if (onboardingItem.getInput_prefill_3() == null){
                input3.setVisibility(View.GONE);
            }
            else{
                input3.setHint(onboardingItem.getInput_prefill_3());
            }


        }
    }
}
