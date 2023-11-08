package com.example.prestigebankingfinal;

public class DataModel {
private String name;
private String email;
private String phonenumber;
private String gender;
private String dob;
private String address;
private String pin;
private String image;
private String mpin;
private String creditamount;


    public DataModel(){
        //Empty Constructor for data transmission

    }

    public DataModel(String name, String phonenumber, String address, String email, String pin, String image, String dob, String gender,String creditamount,String mpin) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
        this.email = email;
        this.pin = pin;
        this.image = image;
        this.dob = dob;
        this.gender = gender;
        this.creditamount=creditamount;
        this.mpin=mpin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCreditamount() {
        return creditamount;
    }

    public String getMpin() {
        return mpin;
    }

    public void setMpin(String mpin) {
        this.mpin = mpin;
    }

    public void setCreditamount(String creditamount) {
        this.creditamount = creditamount;
    }
}
