package com.example.se328_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailedWeather extends AppCompatActivity {

    String weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q=athens&appid=37fa9bf93e0e5b312fb5f787d6c28112&units=metric";
    SharedPreferences sp;
    TextView city, humidity, pressure, windSpeed, windDeg, sunrise, sunset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);

        city = (TextView) findViewById(R.id.city2);
        humidity = (TextView) findViewById(R.id.humidity);
        pressure = (TextView) findViewById(R.id.pressure);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.deg);
        sunrise = (TextView) findViewById(R.id.sunrise);
        sunset = (TextView) findViewById(R.id.sunset);

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

                    long sunrisevalue = response.getJSONObject("sys").getLong("sunrise");
                    long sunsetvalue = response.getJSONObject("sys").getLong("sunset");
                    String sunriseF = new java.text.SimpleDateFormat("h:mm a").format(new java.util.Date (sunrisevalue*1000));
                    String sunsetF = new java.text.SimpleDateFormat("h:mm a").format(new java.util.Date (sunsetvalue*1000));
                    sunrise.setText("Sunrise: " + sunriseF);
                    sunset.setText("Sunset: " + sunsetF);

                    int humidityValue = jsonMain.getInt("humidity");
                    humidity.setText("Humidity: "+humidityValue+"%");

                    int pressureValue = jsonMain.getInt("pressure");
                    pressure.setText("Pressure: "+pressureValue+" hPa");

                    double wSpeedValue = response.getJSONObject("wind").getDouble("speed");
                    windSpeed.setText("Wind Speed: " + wSpeedValue+" m/s");

                    double wDegValue = response.getJSONObject("wind").getDouble("deg");
                    windDeg.setText("Wind Degree: " + wDegValue+"°");

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
}