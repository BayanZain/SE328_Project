package com.example.se328_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    String[] menu = {"View Weather Report",
            "Change City",
            "View Firebase Database",
            "View SQLite Database",
            "Add User",
            "Update User",
            "Delete User",
            "Search & Copy User"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView grid = (GridView) findViewById(R.id.grid);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.grid,R.id.item,menu);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, Weather.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, ChangeCity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, FirebaseList.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, SQLiteList.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, Insert.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, Update.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this, Delete.class));
                        break;
                    case 7:
                        startActivity(new Intent(MainActivity.this, Search.class));
                        break;
                }
            }
        });

    }

}
