package com_pizly.java_pizly.pizly.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("hobby")
    @Expose
    private String hobby;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("phone_no")
    @Expose
    private String phoneNo;

    @SerializedName("age")
    @Expose
    private int age;
    private int userImage;

    @SerializedName("image")
    @Expose
    private String image;



    public Person(){}

    public Person(String email, String phoneNo, String password, String hobby, String description, String name, int age) {
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.hobby = hobby;
        this.description = description;
        this.name = name;
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String name, int userImage) {
        this.name = name;
        this.userImage = userImage;
    }

    public String getName() {
        return name;
    }

    public int getUserImage() {
        return userImage;
    }
}
