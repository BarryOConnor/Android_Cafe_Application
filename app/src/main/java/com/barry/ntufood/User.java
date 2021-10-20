package com.barry.ntufood;

import com.google.firebase.Timestamp;



public class User {
    private String forename;
    private String surname;
    private String email;
    private Timestamp registeredDate;
    private String uid;

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Timestamp registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User() {}

    public User(String pForename, String pSurname, String pEmail, Timestamp pRegisteredDate, String pUid) {
        forename = pForename;
        surname = pSurname;
        email = pEmail;
        registeredDate = pRegisteredDate;
        uid = pUid;
    }

    public String formattedName() {
        return forename + " " + surname;
    }


}
