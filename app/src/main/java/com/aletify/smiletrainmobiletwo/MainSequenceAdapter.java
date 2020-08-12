package com.aletify.smiletrainmobiletwo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        MainSequenceViewHolder(@NonNull View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitleMainSequence);
            textDescription = itemView.findViewById(R.id.textDescriptionMainSequence);
            imageMainSequence = itemView.findViewById(R.id.imageMainSequence);
        }

        void setOnboardingData(OnboardingItem onboardingItem){
            textTitle.setText(onboardingItem.getTitle());
            textDescription.setText(onboardingItem.getDescription());
            imageMainSequence.setImageResource(onboardingItem.getImage());
        }
    }
}
