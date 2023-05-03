package com_pizly.java_pizly.pizly.models;

public class ItemSearcher {
    public static final int LayoutOne = 0;
    public static final int LayoutTwo = 1;
    public static final int LayoutThree = 2;

    private int viewType;
    private int imageTicket;
    private String imageParty, imageUser;
    private String title;
    private int personID;
    private boolean enabling;


    public ItemSearcher(int viewType, String title, boolean enabling, int imageTicket) {
        this.title = title;
        this.enabling = enabling;
        this.viewType = viewType;
        this.imageTicket = imageTicket;
    }

    public ItemSearcher(int viewType, String imageParty, String title) {
        this.imageUser = imageParty;
        this.imageParty = imageParty;
        this.title = title;
        this.viewType = viewType;
    }

    public ItemSearcher(int viewType, String imageUser, String title, int personID) {
        this.imageUser = imageUser;
        this.personID = personID;
        this.title = title;
        this.viewType = viewType;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEnabling() {
        return enabling;
    }

    public void setEnabling(boolean enabling) {
        this.enabling = enabling;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public int getImageTicket() {
        return imageTicket;
    }

    public void setImageTicket(int imageTicket) {
        this.imageTicket = imageTicket;
    }

    public String getImageParty() {
        return imageParty;
    }

    public void setImageParty(String imageParty) {
        this.imageParty = imageParty;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
