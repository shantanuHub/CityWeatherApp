package com.example.android.a25_weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {

    String[] cities = new String[]{"Andaman And Nicobar Islands ","Chittoor","Dowlaiswaram","Eluru","Guntur","Kadapa","Kakinaa","Kurnool","Machilipatnam","Nagarjunakoṇḍa","Rajahmundry","Srikakulam","Tirupati","Vijayawada","Visakhapatnam","Vizianagaram","Yemmiganur","Assam","Dhuburi","Dibrugarh","Dispur","Guwahati",
            "bihar","patna","gaya","begusarai","chandigarh","Ambikapur","Bhilai","Bilaspur","Dhamtari","Durg","Jagdalpur","Raipur","Rajnandgaon","Daman","Diu","goa","gujarat","Ahmadabad","Amreli","Porbandar","Rajkot","Surat","haryana","bilaspur","manali","Dalhausie",
            "jammu","shrinagar","ranchi","Karnataka","Bangalore","kerala","bhopal","indore","gwalior","datia","jhasi","mumbai","pune","Odisha","Puducherry (Union Territory)","punjab","rajasthan","Jaipur","Jaisalmer","ajmer","kota","sikkim","tamilnadu","agra","lacknow","meerut","Almora","Dehra Dun","Haridwar","Mussoorie","Nainital","chitranjan","asansol"};

    private TextView result , result2 , result3;
    private AutoCompleteTextView city;
    private Button button;


    //https://api.openweathermap.org/data/2.5/weather?q=gwalior,india&appid=0dbbbee4c776cc582a19c2a75690cb92
    String BaseUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API   = "&appid=0dbbbee4c776cc582a19c2a75690cb92&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1,cities);


        result = findViewById(R.id.textView2);
        city = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        result2 = findViewById(R.id.textView);
        result3 = findViewById(R.id.textView3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = city.getText().toString();
                if(value.length()==0){
                    city.setError("please enter input");}
                    APIHandle();
                    city.setAdapter(arrayAdapter);

            }
        });
    }
        public void APIHandle(){
            String MyUrl = BaseUrl + city.getText().toString() + API;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, MyUrl, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //Log.i("JSON", "JSON: " + response);
                    try {


                        JSONObject main_object = response.getJSONObject("main");
                        JSONArray jsonArray = response.getJSONArray("weather");
                        JSONObject object = jsonArray.getJSONObject(0);
                        String temp = String.valueOf( main_object.getDouble("temp"));
                        String description = object.getString("description");
                        String city = response.getString("name");


                        result.setText(description);
                        result2.setText(String.valueOf(temp) + "\u2103");   //+ for symbol of celcius
                        result3.setText(city);

                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.i("error" ,"onErrorResponse:" + error);

                }
            });
            MySingleton.getInstance(Main2Activity.this).addToRequestQue(jsonObjectRequest);
        }

    }









