package com.example.se328_project;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseList extends ListActivity {

    Firebase firebase;
    DatabaseReference databaseReference;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebase = new Firebase();
        databaseReference = firebase.myRef;

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String userId = dataSnapshot.child("userId").getValue().toString();
                String firstName = dataSnapshot.child("firstName").getValue().toString();
                String lastName = dataSnapshot.child("lastName").getValue().toString();
                String emailAddress = dataSnapshot.child("emailAddress").getValue().toString();
                String phoneNumber = dataSnapshot.child("phoneNumber").getValue().toString();
                arrayList.add("\nUser ID: "+userId+"\nFirst Name: "+firstName+"\nLast Name: "+lastName+"\nEmail Address: "
                        +emailAddress+"\nPhone Number: "+phoneNumber+"\n");
                setListAdapter(new ArrayAdapter<String>(FirebaseList.this, R.layout.activity_firebase_list, R.id.itemlist, arrayList));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }}



