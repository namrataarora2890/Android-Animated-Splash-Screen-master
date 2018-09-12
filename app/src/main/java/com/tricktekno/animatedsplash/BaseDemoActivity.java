/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tricktekno.animatedsplash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDemoActivity extends FragmentActivity implements OnMapReadyCallback , AdapterView.OnItemSelectedListener {
    private GoogleMap mMap;
    String ip;
    Bundle bundle;
    String circle;
    String technology;
    String operator;
    String SERVICE_URL;
    int color;
    private Spinner spinner1, spinner2,spinner3;
    private Button btnSubmit;
    String url; // This will hold the full URL which will include the username entered in the etGitHubUser.
    List<String> list1,list2,list3 ;

    ArrayAdapter<String> dataAdapter;
    RequestQueue requestQueue;


    protected int getLayoutId() {
        return R.layout.map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Intent intent = getIntent();
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();


        /******************************************************/


        /*****************************************************/
        ip = getString(R.string.ip);
        requestQueue = Volley.newRequestQueue(this);
        circle = intent.getStringExtra("circle");
        technology = intent.getStringExtra("technology");
        operator = intent.getStringExtra("operator");
        SERVICE_URL = "http://"+ip+"/loc_data?"+ "circle=" + circle + "&tech=" + technology + "&operator=" + operator;
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        if (technology=="2G")
            color= Color.GREEN;
        else if (technology=="3G")
            color=Color.RED;
        else
            color=Color.BLUE;
        /*******************************************************/
        addItemsOnSpinner1();
        addListenerOnSpinnerItemSelection();
        set_button_click_listner();

        setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            return;
        }
        mMap = map;
        startDemo();
    }

    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapc)).getMapAsync(this);
    }


    protected abstract void startDemo();

    protected GoogleMap getMap() {
        return mMap;
    }


/**************************************************************/

public void addItemsOnSpinner1() {

    spinner1 = (Spinner) findViewById(R.id.spinner1);
    String baseUrl = "http://"+ip+"/circle_detail/";

    this.url = baseUrl ;
    list1 = new ArrayList<String>();

    dataAdapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, list1);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    dataAdapter.notifyDataSetChanged();

    JsonArrayRequest arrReq = new JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try{
                        // Loop through the array elements
                        for(int i=0;i<response.length();i++){
                            // Get current json object
                            JSONObject student = response.getJSONObject(i);
                            // Get the current student (json object) data
                            String circle_name = student.getString("circle_name");
                            list1.add(circle_name);
                            Log.e("circle_name\n", circle_name);
                        }
                    }catch (JSONException e){
                        Log.e("on my god exception  ::", e.toString());
                        e.printStackTrace();
                    }
                    spinner1.setAdapter(dataAdapter);
                    if (circle != null) {
                        int spinnerPosition = dataAdapter.getPosition(circle);
                        spinner1.setSelection(spinnerPosition);
                    }
                    // here set the adapter on rcving response
                    //addItemsOnSpinner2(); // now set the items on spinner 2 based on prev result
                    //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                    //spinner1.setOnItemSelectedListener(this);
                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(getBaseContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
                    Log.e("hell you Volley ::", error.toString());
                }
            }
    );
    arrReq.setRetryPolicy(new DefaultRetryPolicy(5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    requestQueue.add(arrReq);

}



    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        String baseUrl = "http://"+ip+"/operator_detail?circle="+ String.valueOf(spinner1.getSelectedItem());


        this.url = baseUrl ;
        list2 = new ArrayList<String>();

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();

        JsonArrayRequest arrReq = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject tech = response.getJSONObject(i);
                                // Get the current student (json object) data
                                String operator = tech.getString("circle_operator");
                                list2.add(operator);
                                Log.e("operator\n", operator);
                            }
                        }catch (JSONException e){
                            Log.e("on my god exception  ::", e.toString());
                            e.printStackTrace();
                        }
                        spinner2.setAdapter(dataAdapter); // here set the adapter on rcving response
                        //spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getBaseContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        Log.e("hell you Volley ::", error.toString());
                    }
                }
        );
        arrReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        requestQueue.add(arrReq);

        //spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }
    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);


    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        switch (arg0.getId()){
            case R.id.spinner1:
                //Do something
                addItemsOnSpinner2();
                break;
            case R.id.spinner2:
                //Do another thing
                addItemsOnSpinner3();
                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Log.e("hell you item ::","");
        // TODO Auto-generated method stub
    }
/**************************************************************/
public void addItemsOnSpinner3() {

    spinner3 = (Spinner) findViewById(R.id.spinner3);
    String baseUrl = "http://"+ip+"/operator_technology_detail?circle="+ String.valueOf(spinner1.getSelectedItem())+"&operator="+String.valueOf(spinner2.getSelectedItem());


    //this.url = baseUrl ;
    list3 = new ArrayList<String>();

    final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, list3);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    dataAdapter.notifyDataSetChanged();

    JsonArrayRequest arrReq = new JsonArrayRequest(
            Request.Method.GET,
            baseUrl,
            null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try{
                        // Loop through the array elements
                        for(int i=0;i<response.length();i++){
                            // Get current json object
                            JSONObject tech = response.getJSONObject(i);
                            // Get the current student (json object) data
                            String technology = tech.getString("technology");
                            list3.add(technology);
                            Log.e("technology\n", technology);
                        }
                    }catch (JSONException e){
                        Log.e("on my god exception  ::", e.toString());
                        e.printStackTrace();
                    }
                    spinner3.setAdapter(dataAdapter); // here set the adapter on rcving response
                    //spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){

                    Toast.makeText(getBaseContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
                    Log.e("hell you Volley ::", error.toString());
                }
            }
    );
    arrReq.setRetryPolicy(new DefaultRetryPolicy(5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    requestQueue.add(arrReq);

    //spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());

}
public void set_button_click_listner(){

    Button button= (Button) findViewById(R.id.butt);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mMap.clear();
            SERVICE_URL = "http://"+ip+"/loc_data?"+ "circle=" + String.valueOf(spinner1.getSelectedItem()) + "&tech=" + String.valueOf(spinner3.getSelectedItem()) + "&operator=" + String.valueOf(spinner2.getSelectedItem());
            startDemo();
        }
    });


}

    @Override
    public void onBackPressed() {

        Intent myIntent = new Intent(BaseDemoActivity.this, SecondActivity.class);


      BaseDemoActivity.this.startActivity(myIntent,bundle);
        //moveTaskToBack(true);
        //finish();
    }

}
