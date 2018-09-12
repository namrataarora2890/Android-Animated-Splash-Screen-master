package com.tricktekno.animatedsplash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/*public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

*/
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
  /*  @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}*/

/*
 * Copyright (c) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.



 JSON EXAMPLE

[
    {
        "name": "John Doe",
        "latlng": [
            62.457434,
            17.349869
        ],
        "population": "123"
    },
    {
        "name": "Jane Doe",
        "latlng": [
            62.455102,
            17.345599
        ],
        "population": "132"
    },
    {
        "name": "James Bond",
        "latlng": [
            62.458287,
            17.356306
        ],
        "population": "123"
    }
]
 */


/**
 * @author saxman
 */
public class MapsActivity2 extends FragmentActivity  {
    private static final String LOG_TAG = "ExampleApp";
    Bundle bundle;
    String circle;
    String technology;
    String operator;
     String SERVICE_URL;
    int color;
    //private static final String SERVICE_URL = "http://10.0.2.2:8000/loc_data/";
    protected GoogleMap map;
    int height = 100;
    int width = 100;
    private static final LatLng DELHI = new LatLng(28.7041,77.1025);
    String ip;


    //BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.ic_reddot);
    //Bitmap b=bitmapdraw.getBitmap();
    //Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        ip = getString(R.string.ip);
        setUpMapIfNeeded();
        Intent intent = getIntent();

        /*****************************************************/
        circle = intent.getStringExtra("circle");
        technology = intent.getStringExtra("technology");

        if (technology=="2G")
            color=Color.GREEN;
        else if (technology=="3G")
            color=Color.RED;
        else
            color=Color.BLUE;

        operator = intent.getStringExtra("operator");
        SERVICE_URL = "http://"+ip+"/loc_data?"+ "circle=" + circle + "&tech=" + technology + "&operator=" + operator;
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
       // map.moveCamera(CameraUpdateFactory.newLatLngZoom(DELHI,5));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            //map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        // Retrieve the city data from the web service
        // In a worker thread since it's a network operation.
        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddCities();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Cannot retrive cities", e);
                    return;
                }
            }
        }).start();
    }

    protected void retrieveAndAddCities() throws IOException {
        HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            // Connect to the web service
            URL url = new URL(SERVICE_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Read the JSON data into the StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to service", e);
            throw new IOException("Error connecting to service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createMarkersFromJson(json.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });
    }

   void createMarkersFromJson(String json) throws JSONException {
        // De-serialize the JSON string into an array of city objects
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            // Create a marker for each city in the JSON data.
            JSONObject jsonObj = jsonArray.getJSONObject(i);

                    map.addCircle(new CircleOptions()
                            .center(new LatLng(
                                    Float.parseFloat(jsonObj.getString("latitude")), Float.parseFloat(jsonObj.getString("longitude"))
                                    //jsonObj.getJSONArray("latlng").getDouble(1)
                                    //jsonObj.getJSONArray("latlng").getDouble(0),
                                    //jsonObj.getJSONArray("latlng").getDouble(1)
                            ))
                            .radius(1000)
                            .strokeColor(color)
                            .fillColor(color));

            /*
            map.addMarker(new MarkerOptions()
                    .title(jsonObj.getString("circle"))
                    //.snippet(Integer.toString(jsonObj.getInt("population")))
                    .snippet(jsonObj.getString("operator"))
                    .position(new LatLng(
                            jsonObj.getJSONArray("latlng").getDouble(1),
                            jsonObj.getJSONArray("latlng").getDouble(0)
                    ))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.blackdot))
                    //.icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
            );*/
           // map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(jsonObj.getJSONArray("latlng").getDouble(0),
             //       jsonObj.getJSONArray("latlng").getDouble(1))));
            //Toast.makeText(getBaseContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {

        finish();

        Intent myIntent = new Intent(MapsActivity2.this, SecondActivity.class);
        MapsActivity2.this.startActivity(myIntent,bundle);
        //moveTaskToBack(true);
        //finish();
    }


}
