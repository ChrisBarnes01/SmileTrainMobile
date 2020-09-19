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

    private TextView textTitle;
    private TextView textDescription;
    private ImageView imageMainSequence;
    public ToggleButton toggle1;
    public ToggleButton toggle2;
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

    //public ToggleButton getButton1() { return toggle1; };

    class MainSequenceViewHolder extends RecyclerView. ViewHolder{

        MainSequenceViewHolder(@NonNull final View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitleMainSequence);
            textDescription = itemView.findViewById(R.id.textDescriptionMainSequence);
            imageMainSequence = itemView.findViewById(R.id.imageMainSequence);
            toggle1 = itemView.findViewById(R.id.simpleToggleButton1);
            toggle2 = itemView.findViewById(R.id.simpleToggleButton2);

            toggle1.setVisibility(View.INVISIBLE);
            toggle2.setVisibility(View.INVISIBLE);

            TestAlligner.setToggles(toggle1, toggle2);
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
