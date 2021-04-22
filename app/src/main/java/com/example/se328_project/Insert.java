package com.example.se328_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Insert extends AppCompatActivity {

    EditText idET, fnameET, lnameET, emailET, phoneET;
    Firebase firebase = new Firebase ();
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        idET = (EditText)findViewById(R.id.ID);
        fnameET = (EditText)findViewById(R.id.FName);
        lnameET = (EditText)findViewById(R.id.LName);
        phoneET = (EditText)findViewById(R.id.phone);
        emailET = (EditText)findViewById(R.id.email);

        Button addFire = (Button)findViewById(R.id.addFire);
        Button addSQL = (Button)findViewById(R.id.addSQL);

        addFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(idET.getText().toString());
                String fname = fnameET.getText().toString();
                String lname = lnameET.getText().toString();
                String phone = phoneET.getText().toString();
                String email = emailET.getText().toString();
                firebase.insert(id, fname, lname, email,phone,getBaseContext());
            }
        });

        addSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(idET.getText().toString());
                String fname = fnameET.getText().toString();
                String lname = lnameET.getText().toString();
                String phone = phoneET.getText().toString();
                String email = emailET.getText().toString();

                if(!db.addData(id,fname,lname,phone,email)){
                    Toasty.error(getBaseContext(), "ID is taken", Toast.LENGTH_SHORT, true).show();
                }
                else{
                    Toasty.success(getBaseContext(), "Added Successfully", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

    }
}