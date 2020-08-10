package com.example.smiletrainmobiletwo;

public class OnboardingItem {

    private int image;
    private String title;
    private String description;
    private int overlayImage;

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
}
