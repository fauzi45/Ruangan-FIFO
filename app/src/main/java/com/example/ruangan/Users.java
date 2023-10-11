package com.example.ruangan;

public class Users {

    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    private String PhoneNumber;
    private String key;

    public Users(String key) {
        this.key = key;
    }

    private String Email;
    private String Password;
    private String Uid;
    private String userType;


    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public Users() {

    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Users(String uid, String name, String phoneNumber, String email, String password, String userType) {
        Uid = uid;
        Name = name;
        PhoneNumber = phoneNumber;
        Email = email;
        Password = password;
        userType = userType;
    }
}
