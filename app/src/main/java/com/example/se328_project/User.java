package com.example.se328_project;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public int userId;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String phoneNumber;

    public User(int userId, String firstName, String lastName, String emailAddress, String phoneNumber){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
}
