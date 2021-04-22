package com.example.se328_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class Search extends AppCompatActivity {

    EditText valueET;
    Spinner spinner;
    TextView user;
    DatabaseHelper db = new DatabaseHelper(this);
    Firebase firebase = new Firebase ();
    DatabaseReference databaseReference;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        databaseReference = firebase.myRef;

        valueET = (EditText)findViewById(R.id.value);
        spinner = (Spinner)findViewById(R.id.spinner2);
        user = (TextView)findViewById(R.id.user);
        Button searchFire = (Button)findViewById(R.id.searchFire);
        Button searchSQL = (Button)findViewById(R.id.searchSQL);
        Button copy = (Button)findViewById(R.id.copy);


        searchFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = valueET.getText().toString();
                String choice = spinner.getSelectedItem().toString();

                if (choice.equals("userId")) {
                    query = databaseReference.orderByChild(choice).equalTo(Integer.parseInt(value));
                } else {
                    query = databaseReference.orderByChild(choice).equalTo(value);
                }
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() == 0) {
                            Toasty.error(getBaseContext(), "No such User", Toast.LENGTH_SHORT, true).show();
                        }
                        try {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String userId = ds.child("userId").getValue().toString();
                                String firstName = ds.child("firstName").getValue().toString();
                                String lastName = ds.child("lastName").getValue().toString();
                                String emailAddress = ds.child("emailAddress").getValue().toString();
                                String phoneNumber = ds.child("phoneNumber").getValue().toString();

                                user.setText("\nUser ID: " + userId + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + "\nPhone Number: "
                                        + phoneNumber+ "\nEmail Address: " + emailAddress );
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
        });

        searchSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = valueET.getText().toString();
                String choice = spinner.getSelectedItem().toString();

                Cursor cursor = db.getSpecificResult(choice,value);
                if(cursor.getCount() <= 0){
                    Toasty.error(getBaseContext(), "No such User", Toast.LENGTH_SHORT, true).show();
                }
                else {
                    String id = cursor.getString(0);
                    String fName = cursor.getString(1);
                    String lName = cursor.getString(2);
                    String phone = cursor.getString(3);
                    String email = cursor.getString(4);

                    user.setText("\nUser ID: "+id+"\nFirst Name: "+fName+"\nLast Name: "+lName+"\nEmail Address: "
                            +phone+"\nPhone Number: "+email+"\n");
                }
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = valueET.getText().toString();
                String choice = spinner.getSelectedItem().toString();

                if(choice.equals("userId")){
                    query = databaseReference.orderByChild(choice).equalTo(Integer.parseInt(value));
                }
                else{
                    query = databaseReference.orderByChild(choice).equalTo(value);
                }
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()==0){
                            Toasty.error(getBaseContext(), "No such User", Toast.LENGTH_SHORT, true).show();
                        }
                        try{
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                int userId = Integer.parseInt(ds.child("userId").getValue().toString());
                                String firstName = ds.child("firstName").getValue().toString();
                                String lastName = ds.child("lastName").getValue().toString();
                                String emailAddress = ds.child("emailAddress").getValue().toString();
                                String phoneNumber = ds.child("phoneNumber").getValue().toString();

                                if(!db.addData(userId,firstName,lastName,phoneNumber,emailAddress)){
                                    Toasty.error(getBaseContext(), "copying Failed", Toast.LENGTH_SHORT, true).show();
                                }
                                else{
                                    Toasty.success(getBaseContext(), "Copied Successfully", Toast.LENGTH_SHORT, true).show();
                                }
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
        });

    }
}