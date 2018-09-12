package com.tricktekno.animatedsplash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.android.volley.RequestQueue;

/**
 * Created by root on 20/11/17.
 */

public class ThirdActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    private static final String TAG = "your activity name :::";
    ListView simpleListView;
    String baseUrl ;

    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        Intent intent = getIntent();
        simpleListView=(ListView)findViewById(R.id.simpleListView);

        // This is our requests queue to process our HTTP requests.

        String circle = intent.getStringExtra("circle");
        String technology = intent.getStringExtra("technology");
        baseUrl= "http://10.0.2.2:8000/star_rating?"+"circle="+circle+"&tech="+technology ;
        getstarrating();//if it's a string you stored.



    }

    public void getstarrating() {
        requestQueue = Volley.newRequestQueue(this);

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
                                final HashMap<String, String> map = new HashMap<>();

                                // Get current json object
                                JSONObject student = response.getJSONObject(i);
                                // Get the current student (json object) data
                                String circle_name = student.getString("circle");
                                JSONObject score = student.getJSONObject("score");
                                String whatsapp_mos = score.getString("whatsapp_mos");
                                String facebook_mos = score.getString("facebook_mos");
                                String gmaps_mos = score.getString("gmaps_mos");
                                String webbrowsing_mos = score.getString("webbrowsing_mos");
                                map.put("circle",  circle_name);
                                map.put("whatsapp",whatsapp_mos);
                                map.put("facebook",facebook_mos );
                                map.put("gmaps",  gmaps_mos);
                                map.put("webbrowsing",  webbrowsing_mos);



                                arrayList.add(map);
                                Log.d(TAG,circle_name);

                                Log.d(TAG,whatsapp_mos);

                            }
                        }catch (JSONException e){
                            Log.e("on my god exception  ::", e.toString());
                            e.printStackTrace();
                        }
                        ListAdapter adapter = new SimpleAdapter(getApplicationContext(), arrayList,
                                R.layout.list_item, new String[] {"circle", "whatsapp",
                                "gmaps"}, new int[] { R.id.name, R.id.address,
                                R.id.rating });

                        simpleListView.setAdapter(adapter);
                        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(getApplicationContext(), "yeah", Toast.LENGTH_LONG).show();//show the selected image in toast according to position
                            }
                        });

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("hell you Volley ::", error.toString());
                    }
                }
        );

        requestQueue.add(arrReq);

        Log.d(TAG,"after set adapter .....");

    }

}
