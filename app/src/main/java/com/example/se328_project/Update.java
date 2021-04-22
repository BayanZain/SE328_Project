package com.example.se328_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Update extends AppCompatActivity {

    EditText idET, newET;
    Spinner spinner;
    Firebase firebase = new Firebase ();
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        idET = (EditText)findViewById(R.id.id_update);
        newET = (EditText)findViewById(R.id.newValue);

        spinner = (Spinner)findViewById(R.id.spinner);
        Button updateFire = (Button)findViewById(R.id.updateFire);
        Button updateSQL = (Button)findViewById(R.id.updateSQL);

        updateFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(idET.getText().toString());
                String newV = newET.getText().toString();
                String choice = spinner.getSelectedItem().toString();
                firebase.update(id,choice,newV,getBaseContext());
            }
        });

        updateSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idET.getText().toString();
                String newV = newET.getText().toString();
                String choice = spinner.getSelectedItem().toString();

                if(db.checkUser(id)){
                    db.update(id,choice,newV);
                    Toasty.success(getBaseContext(), "Updated Successfully", Toast.LENGTH_SHORT, true).show();
                }
                else{
                    Toasty.error(getBaseContext(), "No such user", Toast.LENGTH_SHORT, true).show();
                }

            }
        });


    }
}