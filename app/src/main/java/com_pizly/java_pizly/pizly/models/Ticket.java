package com_pizly.java_pizly.pizly.models;

public class Ticket {

    private String title;
    private int image;
    private String quickDescription;
    private boolean active;

    public Ticket(String title, String quickDescription, int image) {
        this.title = title;
        this.image = image;
        this.quickDescription = quickDescription;
    }

    public Ticket(String title, String quickDescription, int image, boolean active) {
        this.title = title;
        this.image = image;
        this.active = active;
        this.quickDescription = quickDescription;
    }

    public boolean isActive() {
        return active;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getQuickDescription() {
        return quickDescription;
    }
}
