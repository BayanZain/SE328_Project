package com.example.se328_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class SQLiteList extends ListActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    ArrayList<String> arrayList = new ArrayList<>();
    Cursor cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cur = db.getListContents();
        while (cur.moveToNext()) {
            arrayList.add("\nUser ID: " + cur.getString(0) + "\nFirst Name: " + cur.getString(1) + "\nLast Name: "
                    + cur.getString(2) + "\nPhone Number: " + cur.getString(3) + "\nEmail Address: " + cur.getString(4) + "\n");
            setListAdapter(new ArrayAdapter<String>(SQLiteList.this, R.layout.activity_s_q_lite_list, R.id.itemlist2, arrayList));
        }
    }

    protected void onListItemClick (ListView l, View v,int position, long id){
        super.onListItemClick(l,v,position,id);
        int row = 0;
        cur = db.getListContents();

        while(row<=position){
            cur.moveToNext();
            row++;
        }

        Toasty.normal(getBaseContext(), "First Name: " + cur.getString(1) + "\nLast Name: "
                + cur.getString(2), Toast.LENGTH_SHORT).show();
    }
}
