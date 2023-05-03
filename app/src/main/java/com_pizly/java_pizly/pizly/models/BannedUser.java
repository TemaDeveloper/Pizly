package com_pizly.java_pizly.pizly.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannedUser {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("blockedFromID")
    @Expose
    private int blockedFromID;

    @SerializedName("blockToID")
    @Expose
    private int blockToID;

    public BannedUser() {
    }

    public BannedUser(int id, int blockedFromID, int blockToID) {
        this.id = id;
        this.blockedFromID = blockedFromID;
        this.blockToID = blockToID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlockedFromID() {
        return blockedFromID;
    }

    public void setBlockedFromID(int blockedFromID) {
        this.blockedFromID = blockedFromID;
    }

    public int getBlockToID() {
        return blockToID;
    }

    public void setBlockToID(int blockToID) {
        this.blockToID = blockToID;
    }
}
