package com.example.se328_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class ChangeCity extends AppCompatActivity {

    EditText cityET;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        cityET = (EditText)findViewById(R.id.city);
        Button change = (Button)findViewById(R.id.change);
        Button defCity = (Button)findViewById(R.id.defaultCity);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityET.getText().toString();

                url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=37fa9bf93e0e5b312fb5f787d6c28112&units=metric";
                JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toasty.success(getBaseContext(), "Changed Successfully", Toast.LENGTH_SHORT, true).show();
                        editor = sp.edit();
                        editor.putString("city",city.toLowerCase());
                        editor.commit();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(getBaseContext(), "No Such city, Change it Please", Toast.LENGTH_SHORT, true).show();
                    }
                });
                RequestQueue queue = Volley.newRequestQueue(ChangeCity.this);
                queue.add(jsonObj);

                //Toasty.success(getBaseContext(), "Changed Successfully", Toast.LENGTH_SHORT, true).show();
            }
        });

        defCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                Toasty.success(getBaseContext(), "Ok", Toast.LENGTH_SHORT, true).show();
            }
        });
    }
}