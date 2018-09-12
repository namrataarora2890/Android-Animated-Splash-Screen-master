package com.tricktekno.animatedsplash;

/**
 * Created by root on 29/11/17.
 */

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

import static java.lang.Boolean.FALSE;

public class QualitativePerformance extends AppCompatActivity {
    RequestQueue requestQueue;
    private static final String TAG = "your activity name :::";
    ArrayList<Spaceship> spaceships=new ArrayList<>();
    ListView lv;
    String baseUrl ;
    String ip;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNetworkConnectionAvailable();
        setContentView(R.layout.activity_main);
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        //////////////////////////////////////////////////////////
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        ////////////////////////////////////////////////////////////
        //getActionBar().setIcon(R.drawable.my_icon);
        setupWindowAnimations();
        Intent intent = getIntent();
        ip = getString(R.string.ip);
        /*****************************************************/
        String circle = intent.getStringExtra("circle");
        String technology = intent.getStringExtra("technology");
        baseUrl= "http://"+ip+"/star_rating?"+"circle="+circle+"&tech="+technology ;
        getstarrating();//if it's a string you stored.
        String st= circle+" "+technology;
        //setTitle(st);
        setupToolbar(st);

        /*****************************************************/
        lv= (ListView) findViewById(R.id.lv);
        //Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        //this.getActionBar().setDisplayShowCustomEnabled(true);
        //this.getActionBar().setDisplayShowTitleEnabled(false);



        //lv.setAdapter(new CustomAdapter(this,SpaceshipCollection.getSpaceships() )); // create an array list of spaceships and pass here
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String circle = intent.getStringExtra("circle");
        String technology = intent.getStringExtra("technology");
        String st = circle + " " + technology;
        //setTitle(st);
        setupToolbar(st);
    }
    @Override
    public void onPause() {
        super.onPause();
        Intent intent = getIntent();
        String circle = intent.getStringExtra("circle");
        String technology = intent.getStringExtra("technology");
        String st = circle + " " + technology;
        //setTitle(st);
        setupToolbar(st);
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



                                //arrayList.add(map);
                                Spaceship s=new Spaceship();
                                // Get current json object
                                JSONObject student = response.getJSONObject(i);
                                // Get the current student (json object) data
                                String operator_name = student.getString("circle_operator");
                                s.setName(operator_name );
                                JSONObject qoe = student.getJSONObject("QOE");
                                String latency = qoe.getString("latency");
                                s.setLatency(Float.parseFloat(latency));
                                String droprate = qoe.getString("droprate");
                                s.setDroprate(Float.parseFloat(droprate));
                                String speed  = qoe.getString("speed");
                                s.setMin_download_speed(Float.parseFloat(speed));
                                String throughput = qoe.getString("throughput");
                                s.setAvg_throughput(Float.parseFloat(throughput));


                                JSONObject score = student.getJSONObject("score");

                                String whatsapp_mos = score.getString("whatsapp_mos");
                                s.setWhatsapp_rating(Float.parseFloat(whatsapp_mos));

                                String facebook_mos = score.getString("facebook_mos");
                                s.setFb_rating(Float.parseFloat(facebook_mos));

                                String gmaps_mos = score.getString("gmaps_mos");
                                s.setGmpas_rating(Float.parseFloat(gmaps_mos));

                                String webbrowsing_mos = score.getString("webbrowsing_mos");
                                s.setWebbrowsing_rating(Float.parseFloat(webbrowsing_mos));

                                String youtube_mos = score.getString("youtube_mos");
                                s.setYoutube_rating(Float.parseFloat(youtube_mos));

                                String hdvideo_mos = score.getString("hdvideo_mos");
                                s.setHd_rating(Float.parseFloat(hdvideo_mos));

                                spaceships.add(s);


                                //arrayList.add(map);
                                //Log.d(TAG,operator_name);




                            }
                        }catch (JSONException e){
                            Log.e("oh my god exception  ::", e.toString());
                            e.printStackTrace();
                        }

                        lv.setAdapter(new CustomAdapter(getApplicationContext(),spaceships));
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getBaseContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        Log.e("Hell you Volley ::", error.toString());
                    }
                }
        );

        arrReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(arrReq);

        //Log.d(TAG,"after set adapter .....");

    }
    private void setupWindowAnimations() {
        boolean x=FALSE;
        Slide slide = new Slide();
        slide.setDuration(1000);
        //Slide slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
        //Fade fade = TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);

        Fade fade = new Fade();
        fade.setDuration(1000);
        //getWindow().setEnterTransition(slide);
        //getWindow().setReenterTransition(slide);
        getWindow().setReturnTransition(slide);
        //getWindow().setAllowReturnTransitionOverlap(x);
        //getWindow().setBackgroundDrawableResource(R.drawable.logo);


    }
    public static Context getAppContext() {
        return getAppContext();
    }
    private void setupToolbar(CharSequence s) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(s);
    }
    @Override
    public void onBackPressed() {

        Intent myIntent = new Intent(QualitativePerformance.this, SecondActivity.class);
        QualitativePerformance.this.startActivity(myIntent,bundle);
        //moveTaskToBack(true);
        //finish();
    }

    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }


}


