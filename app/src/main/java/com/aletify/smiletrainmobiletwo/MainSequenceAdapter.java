package com.aletify.smiletrainmobiletwo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainSequenceAdapter extends RecyclerView.Adapter<MainSequenceAdapter.MainSequenceViewHolder>{

    private List<OnboardingItem> mainSequenceItems;

    public MainSequenceAdapter(List<OnboardingItem> mainSequenceItems) {
        this.mainSequenceItems = mainSequenceItems;
    }

    @NonNull
    @Override
    public MainSequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainSequenceViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_main_sequence, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MainSequenceViewHolder holder, int position) {
        holder.setOnboardingData(mainSequenceItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mainSequenceItems.size();
    }

    class MainSequenceViewHolder extends RecyclerView. ViewHolder{
        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageMainSequence;
        private ToggleButton toggle1;
        private ToggleButton toggle2;


        MainSequenceViewHolder(@NonNull final View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitleMainSequence);
            textDescription = itemView.findViewById(R.id.textDescriptionMainSequence);
            imageMainSequence = itemView.findViewById(R.id.imageMainSequence);
            toggle1 = itemView.findViewById(R.id.simpleToggleButton1);
            toggle2 = itemView.findViewById(R.id.simpleToggleButton2);
            toggle1.setText("No");
            toggle1.setTextOn("No");
            toggle1.setTextOff("No");

            toggle2.setText("Yes");
            toggle2.setTextOn("Yes");
            toggle2.setTextOff("Yes");



            toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggle2.setChecked(false);
                        toggle2.setTextColor(itemView.getContext().getResources().getColor(R.color.colorBlack));
                        toggle1.setTextColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
                    } else {
                        toggle2.setChecked(true);
                        toggle2.setTextColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
                        toggle1.setTextColor(itemView.getContext().getResources().getColor(R.color.colorBlack));

                    }
                }
            });
            toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggle1.setChecked(false);
                        toggle2.setTextColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
                        toggle1.setTextColor(itemView.getContext().getResources().getColor(R.color.colorBlack));
                    } else {
                        toggle1.setChecked(true);
                        toggle2.setTextColor(itemView.getContext().getResources().getColor(R.color.colorBlack));
                        toggle1.setTextColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
                    }
                }
            });



        }

        void setOnboardingData(OnboardingItem onboardingItem){
            if (onboardingItem.getTitle() == null){
                textTitle.setVisibility(View.GONE);
            }
            else{
                textTitle.setText(onboardingItem.getTitle());
            }
            textDescription.setText(onboardingItem.getDescription());
            if (onboardingItem.getImage() == -1111){
                imageMainSequence.setVisibility(View.GONE);
            }
            else{
                imageMainSequence.setImageResource(onboardingItem.getImage());
            }
        }
    }
}
