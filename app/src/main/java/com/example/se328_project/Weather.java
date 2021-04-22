package com.example.se328_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class Weather extends AppCompatActivity {

    String weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q=athens&appid=37fa9bf93e0e5b312fb5f787d6c28112&units=metric";
    ImageView weatherBackground;
    TextView temperature, status, description, minMax, city;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        temperature = (TextView) findViewById(R.id.temperature);
        description = (TextView) findViewById(R.id.description);
        status = (TextView) findViewById(R.id.status);
        minMax = (TextView) findViewById(R.id.minmax);
        city = (TextView) findViewById(R.id.city);
        weatherBackground = (ImageView) findViewById(R.id.weatherbackground);

        Button report = (Button)findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Weather.this, DetailedWeather.class));
            }
        });

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String cityName = sp.getString("city","");

        if(!cityName.isEmpty()){
            weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=37fa9bf93e0e5b312fb5f787d6c28112&units=metric";
        }

        weather(weatherWebserviceURL);

    }

    public void weather(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Bayan",response.toString());
                try{
                    JSONObject jsonMain = response.getJSONObject("main");

                    String town = response.getString("name");
                    city.setText(town);

                    double temp = jsonMain.getDouble("temp");
                    temperature.setText(temp+"°C");

                    double min = jsonMain.getDouble("temp_min");
                    double max = jsonMain.getDouble("temp_max");
                    minMax.setText(min+"° / "+max+"°");

                    String wStatus = response.getJSONArray("weather").getJSONObject(0).getString("main");
                    status.setText(wStatus);

                    String wDesc = response.getJSONArray("weather").getJSONObject(0).getString("description");
                    description.setText("( " + wDesc + " )");

                    weatherPic(wStatus);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Bayan","JSON Error: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Bayan","Error in URL: " + error);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }

    public void weatherPic(String weatherCondition) {
        if (weatherCondition.equals("Clear")) {
            weatherBackground.setImageResource(R.drawable.sunny);
        }
        if (weatherCondition.equals("Clouds")) {
            weatherBackground.setImageResource(R.drawable.clouds);
        }
        if (weatherCondition.equals("Rain")) {
            weatherBackground.setImageResource(R.drawable.rain);
        }
        if (weatherCondition.equals("Snow")) {
            weatherBackground.setImageResource(R.drawable.snowy);
        }
        if (weatherCondition.equals("Fog")) {
            weatherBackground.setImageResource(R.drawable.fog);
        }
        if (weatherCondition.equals("Thunderstorm")) {
            weatherBackground.setImageResource(R.drawable.storm);
        }

    }
}