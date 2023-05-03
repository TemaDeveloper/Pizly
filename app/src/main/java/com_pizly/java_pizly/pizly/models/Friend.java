package com_pizly.java_pizly.pizly.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Friend {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("uid")
    @Expose
    private int uid;

    @SerializedName("friendID")
    @Expose
    private int friendID;

    @SerializedName("friend_request")
    @Expose
    private int friend_request;

    public Friend() {
    }

    public Friend(int id, int uid, int friendID, int friend_request) {
        this.id = id;
        this.uid = uid;
        this.friendID = friendID;
        this.friend_request = friend_request;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public int getFriend_request() {
        return friend_request;
    }

    public void setFriend_request(int friend_request) {
        this.friend_request = friend_request;
    }
}
