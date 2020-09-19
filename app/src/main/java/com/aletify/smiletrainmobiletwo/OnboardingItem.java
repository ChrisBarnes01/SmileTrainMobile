package com.aletify.smiletrainmobiletwo;

import android.content.Intent;

public class OnboardingItem {

    private int image;
    private String title;
    private String description;
    private boolean selectCharacterItem;
    private int overlayImage;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }



    public int getOverlayImage() {
        return overlayImage;
    }

    public void setOverlayImage(int overlayImage) {
        this.overlayImage = overlayImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "index " + Integer.valueOf(index);
    }
}
