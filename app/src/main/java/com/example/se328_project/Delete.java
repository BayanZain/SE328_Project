package com.example.se328_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class Delete extends AppCompatActivity {

    EditText idDel;
    Firebase firebase = new Firebase ();
    public DatabaseReference myRef= firebase.myRef;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        idDel = (EditText)findViewById(R.id.id_delete);
        Button deleteFire = (Button)findViewById(R.id.deleteFire);
        Button deleteSQL = (Button)findViewById(R.id.deleteSQL);

        deleteFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(idDel.getText().toString());
                firebase.delete(id,getBaseContext());
            }
        });

        deleteSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idDel.getText().toString();

                if(!db.checkUser(id)){
                    Toasty.error(getBaseContext(), "No such user", Toast.LENGTH_SHORT, true).show();
                }
                else{
                    db.Delete(id);
                    Toasty.success(getBaseContext(), "Deleted from SQL Successfully", Toast.LENGTH_SHORT, true).show();
                }


            }
        });
    }
}