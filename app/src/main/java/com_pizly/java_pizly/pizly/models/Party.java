package com_pizly.java_pizly.pizly.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Party {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("open_date")
    @Expose
    private String open_date;
    @SerializedName("rules")
    @Expose
    private String rules;
    @SerializedName("open_time")
    @Expose
    private String open_time;
    @SerializedName("finish_time")
    @Expose
    private String finish_time;
    @SerializedName("additional_info")
    @Expose
    private String additional_info;
    @SerializedName("pricing")
    @Expose
    private String pricing;
    @SerializedName("uid")
    @Expose
    private int uid;
    @SerializedName("imageParty")
    @Expose
    private String imageParty;

    public Party() {
    }


    public Party(String title, String address, String open_date, String rules, String open_time, String finish_time, String additional_info, String pricing, int uid, String imageParty) {
        this.title = title;
        this.address = address;
        this.open_date = open_date;
        this.rules = rules;
        this.open_time = open_time;
        this.finish_time = finish_time;
        this.additional_info = additional_info;
        this.pricing = pricing;
        this.uid = uid;
        this.imageParty = imageParty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageParty() {
        return imageParty;
    }

    public void setImageParty(String imageParty) {
        this.imageParty = imageParty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
