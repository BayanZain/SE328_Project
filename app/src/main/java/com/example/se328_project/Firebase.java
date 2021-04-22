package com.example.se328_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Firebase extends AppCompatActivity {

    final private FirebaseDatabase database;
    final public DatabaseReference myRef;
    private Query query;
    private int lastRootId = 4;
    int runTimes=1;//Used to run onDateChange method just once as it runs in synchronized way

    SharedPreferences sp;

    public Firebase(){
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void insert(int id, String firstName, String lastName, String emailAddress, String phoneNumber, Context c){
        runTimes=1;
        query = myRef.orderByChild("userId").equalTo(id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                while(runTimes<=1){
                    runTimes++;
                    if (dataSnapshot.getChildrenCount() <= 0) {
                        Toasty.success(c, "Added Successfully", Toast.LENGTH_SHORT, true).show();
                        User user = new User(id, firstName, lastName, emailAddress, phoneNumber);
                        String rootId = String.valueOf(++lastRootId);
                        writeWithSuccess(rootId,user);
                    }
                    else{
                        Toasty.error(c, "This ID is Taken", Toast.LENGTH_SHORT, true).show();
                        Log.d("Bayan","id taken");
                    }
                }
                try {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    }
                } catch (Exception e) {
                    Log.d("Bayan-Exception", e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Bayan", "Failed to read value.", error.toException());
            }
        });
    }

    public void writeWithSuccess(String userId, User user) {
        myRef.child(userId).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Bayan", "SUCCESS writing..." + userId);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Bayan", "Error: " + e);
            }
        });
    }

    public void update(int userId, String key2update, String newValue, Context c){
        query = myRef.orderByChild("userId").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0){
                    Toasty.error(c, "No such User", Toast.LENGTH_SHORT, true).show();
                }
                try{
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        ds.getRef().child(key2update).setValue(newValue);
                        Toasty.success(c, "Updated Successfully", Toast.LENGTH_SHORT, true).show();
                    }
                }
                catch(Exception e){
                    Log.d("Bayan-Exception",e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Bayan", "Failed to update value.", error.toException());
            }
        });
    }


    public void delete(int userId , Context c){
        runTimes=1;
        query = myRef.orderByChild("userId").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                while(runTimes<=1){
                    runTimes++;
                    if (dataSnapshot.getChildrenCount() > 0) {
                        Toasty.success(c, "Deleted from Firebase Successfully", Toast.LENGTH_SHORT, true).show();
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                    }
                    else{
                        Toasty.error(c, "No Such User", Toast.LENGTH_SHORT, true).show();
                        Log.d("Bayan","id taken");
                    }
                }
                try{
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        //ds.getRef().removeValue();
                        //Toasty.success(c, "Deleted from SQL Successfully", Toast.LENGTH_SHORT, true).show();
                    }
                }
                catch(Exception e){
                    Log.d("Bayan-Exception",e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Bayan", "Failed to read value.", error.toException());
            }
        });
    }

}