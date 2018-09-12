package com.tricktekno.animatedsplash;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.lukle.clickableareasimage.OnClickableAreaClickedListener;

public class SecondActivity extends AppCompatActivity implements OnItemSelectedListener , OnClickableAreaClickedListener {
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    String url; // This will hold the full URL which will include the username entered in the etGitHubUser.
    List<String> list1 ;
    List<String> list2 ;
    Bundle bundle;
    private BoomMenuButton bmb;
    ImageView image;
    ImageView image_invisible;

    ArrayAdapter<String> dataAdapter;
    String ip ;
    String circle_input, technology_input;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isNetworkConnectionAvailable();
        setContentView(R.layout.activity_second);
        //TextView textView1 = (TextView)findViewById(R.id.textView1);
        Typewriter writer = (Typewriter)findViewById(R.id.textView1);
        writer.setCharacterDelay(35);
        writer.animateText(getResources().getString(R.string.xyz));
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        TextView textView4 = (TextView)findViewById(R.id.textView4);

        Animation a=AnimationUtils.loadAnimation(this, R.anim.translate);
        a.setRepeatCount(50);
        //a.setRepeatMode(Animation.INFINITE);
        a.setRepeatMode(Animation.REVERSE);
        textView4.startAnimation(a);
        //a.setRepeatMode(Animation.REVERSE);
        //textView2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
        //textView3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
        //Add a character every 150ms

        ip = getString(R.string.ip);
        requestQueue = Volley.newRequestQueue(this);
        setupToolbar();
        //setupWindowAnimations();
        addItemsOnSpinner1local();
       // addItemsOnSpinner2();
        addListenerOnSpinnerItemSelection();
        //addListenerOnButton();
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        image = (ImageView) findViewById(R.id.img_india);
        image_invisible = (ImageView) findViewById(R.id.newindia);
        // image.setImageResource(R.drawable.newindia);
        image_invisible.setOnTouchListener(handleTouch);

        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        //for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {


                HamButton.Builder builder1 = new HamButton.Builder()
                        .normalImageRes(R.drawable.butterfly)
                        .normalText("QUANTITATIVE PERFORMANCE ")
                        .subNormalText("Quantitative Performance of Operators")
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {

                                Intent myIntent = new Intent(SecondActivity.this, BarActivity.class);
                                if (spinner1!=null && spinner1.getSelectedItem() !=null)
                                {myIntent.putExtra("circle", String.valueOf(spinner1.getSelectedItem()));}
                                else
                                {myIntent.putExtra("circle","Delhi" );}


                                if (spinner2!=null && spinner2.getSelectedItem() !=null)
                                {myIntent.putExtra("technology", String.valueOf(spinner2.getSelectedItem()));}
                                else
                                {myIntent.putExtra("technology","2G" );}//Optional parameters
                                SecondActivity.this.startActivity(myIntent,bundle);

                                // When the boom-button corresponding this builder is clicked.
                               // Toast.makeText(SecondActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                            }
                        });

                bmb.addBuilder(builder1);





        HamButton.Builder builder2 = new HamButton.Builder()
                .normalImageRes(R.drawable.butterfly)
                .normalText("COVERAGE MAPS")
                .subNormalText("See Coverage Maps ")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Intent myIntent = new Intent(SecondActivity.this, CellCoverageImage.class);
                        if (spinner1!=null && spinner1.getSelectedItem() !=null)
                        {myIntent.putExtra("circle", String.valueOf(spinner1.getSelectedItem()));}
                        else
                        {myIntent.putExtra("circle","Delhi" );}


                        if (spinner2!=null && spinner2.getSelectedItem() !=null)
                        {myIntent.putExtra("technology", String.valueOf(spinner2.getSelectedItem()));}
                        else
                        {myIntent.putExtra("technology","2G" );}

                        //Optional parameters
                        SecondActivity.this.startActivity(myIntent,bundle);
                        //Toast.makeText(SecondActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                    }
                });


        bmb.addBuilder(builder2);


        HamButton.Builder builder3 = new HamButton.Builder()
                .normalImageRes(R.drawable.butterfly)
                .normalText("QUALITATIVE PERFORMANCE ")
                .subNormalText("Qualitative Performance of Network Operators")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        //Intent myIntent = new Intent(SecondActivity.this, SmileyRating.class);
                        Intent myIntent = new Intent(SecondActivity.this, QualitativePerformance.class);
                        if (spinner1!=null && spinner1.getSelectedItem() !=null)
                        {myIntent.putExtra("circle", String.valueOf(spinner1.getSelectedItem()));}
                        else
                        {myIntent.putExtra("circle","Delhi" );}


                        if (spinner2!=null && spinner2.getSelectedItem() !=null)
                        {myIntent.putExtra("technology", String.valueOf(spinner2.getSelectedItem()));}
                        else
                        {myIntent.putExtra("technology","2G" );}

                        SecondActivity.this.startActivity(myIntent,bundle);

                        // Toast.makeText(SecondActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                    }
                });


        bmb.addBuilder(builder3);



        HamButton.Builder builder4 = new HamButton.Builder()
                .normalImageRes(R.drawable.butterfly)
                .normalText("BroadBand Label survey ")
                .subNormalText("Broadband Label survey form  ")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        //Intent myIntent = new Intent(SecondActivity.this, SmileyRating.class);
                        Intent myIntent = new Intent(SecondActivity.this, ScreenSlidePagerActivity.class);

                        SecondActivity.this.startActivity(myIntent,bundle);

                        // Toast.makeText(SecondActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                    }
                });


        bmb.addBuilder(builder4);


        HamButton.Builder builder5 = new HamButton.Builder()
                .normalImageRes(R.drawable.butterfly)
                .normalText("Operator Feedback Form ")
                .subNormalText("Network performance feedback form ")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        //Intent myIntent = new Intent(SecondActivity.this, SmileyRating.class);
                        Intent myIntent = new Intent(SecondActivity.this, SmileyRating.class);
                        if (spinner1!=null && spinner1.getSelectedItem() !=null)
                        {myIntent.putExtra("circle", String.valueOf(spinner1.getSelectedItem()));}
                        else
                        {myIntent.putExtra("circle","Delhi" );}


                        if (spinner2!=null && spinner2.getSelectedItem() !=null)
                        {myIntent.putExtra("technology", String.valueOf(spinner2.getSelectedItem()));}
                        else
                        {myIntent.putExtra("technology","2G" );}

                        SecondActivity.this.startActivity(myIntent,bundle);

                        // Toast.makeText(SecondActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                    }
                });


        bmb.addBuilder(builder5);

        // }



    }
    @Override
    public void onClickableAreaTouched(Object item) {
        if (item instanceof State) {
            String text = ((State) item).getName();
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }
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
                               spinner1.setAdapter(dataAdapter); // here set the adapter on rcving response
                               //addItemsOnSpinner2(); // now set the items on spinner 2 based on prev result
                               //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                               //spinner1.setOnItemSelectedListener(this);
                            }


                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                Log.e("hell you Volley ::", error.toString());
                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                    Log.e("Timeout /No conn ::", error.toString());

                                } else if (error instanceof AuthFailureError) {
                                    Log.e("Auth Failure ::", error.toString());
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    Log.e("Server Error ::", error.toString());
                                } else if (error instanceof NetworkError) {
                                    //TODO

                                    Log.e("Network Error ::", error.toString());
                                } else if (error instanceof ParseError) {
                                    //TODO
                                    Log.e("Parse  Error ::", error.toString());
                                }
                            }


                        }
                );
        arrReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(arrReq);
        
    }
    public void addItemsOnSpinner1local()
    {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        list1 = new ArrayList<String>();
        list1= Arrays.asList(getResources().getStringArray(R.array.circle_names));
        //ArrayAdapter<CharSequence> dataAdapterr= ArrayAdapter.createFromResource(this, R.array.circle_names , android.R.layout.simple_spinner_item);
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner1.setAdapter(dataAdapter);

    }

    public void addItemsOnSpinner2local()
    {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        list2 = new ArrayList<String>();
        list2= Arrays.asList(getResources().getStringArray(R.array.technology));
        //ArrayAdapter<CharSequence> dataAdapterr= ArrayAdapter.createFromResource(this, R.array.circle_names , android.R.layout.simple_spinner_item);
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner2.setAdapter(dataAdapter);

    }



    public void addItemsOnSpinner2() {

        final  ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(SecondActivity.this);
        progressDialog.setMessage( getString(R.string.data_fetch));
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        String baseUrl = "http://"+ip+"/technology_detail?circle="+ String.valueOf(spinner1.getSelectedItem());


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
                                progressDialog.dismiss();
                                try{
                                    // Loop through the array elements
                                    for(int i=0;i<response.length();i++){
                                        // Get current json object
                                        JSONObject tech = response.getJSONObject(i);
                                        // Get the current student (json object) data
                                        String technology = tech.getString("technology");
                                        list2.add(technology);
                                        Log.e("technology\n", technology);
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

                                progressDialog.dismiss();
                                //Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                error.printStackTrace();
                                if (error instanceof TimeoutError || error instanceof NoConnectionError ) {
                                    Log.e("Timeout /No conn ::", error.toString());
                                    Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();

                                } else if (error instanceof AuthFailureError) {
                                    Log.e("Auth Failure ::", error.toString());
                                    Toast.makeText(getBaseContext() , getString(R.string.auth_error), Toast.LENGTH_LONG).show();
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    Log.e("Server Error ::", error.toString());
                                    Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();
                                } else if (error instanceof NetworkError ) {
                                    //TODO

                                    Log.e("Network Error ::", error.toString());
                                    Toast.makeText(getBaseContext() , getString(R.string.network_error), Toast.LENGTH_LONG).show();
                                } else if (error instanceof ParseError) {
                                    //TODO
                                    Log.e("Parse  Error ::", error.toString());
                                    Toast.makeText(getBaseContext() , getString(R.string.parse_error), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
        arrReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



        //arrReq.setShouldRetryServerErrors(true);

        arrReq.setShouldCache(false);
        requestQueue.add(arrReq);
        progressDialog.show();

        //spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        
    }
    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner1.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
            long arg3) {

            addItemsOnSpinner2();
            }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Log.e("hell you item ::","");
        // TODO Auto-generated method stub
    }
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        //Slide slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
        //Fade fade = TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);

        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(slide);
        //getWindow().setAllowReturnTransitionOverlap(TRUE);
        //getWindow().setAllowEnterTransitionOverlap(TRUE);

    }
    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onBackPressed() {
        //this.startActivity(new Intent(SecondActivity.this, SecondActivity.class));

        this.finish();
        System.exit(0);

        return;


    }



        private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int x = (int) event.getX();
            int y = (int) event.getY();
            //Log.i("TAG", "touch entered");
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i("TAG", "touched down");
                    getColour(x,y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("TAG", "moving: (" + x + ", " + y + ")");
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("TAG", "touched up");
                    break;
            }

            //Log.i("Touch Event........", "image clicked" +String.valueOf(x)+"     "+String.valueOf(y)+"     "+String.valueOf(getColour(x,y)));

            return true;
        }
    };
    private int getColour( int x, int y)
    {
        ImageView img = (ImageView) findViewById(R.id.newindia);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots=Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        int pixel= hotspots.getPixel(x, y);

        int r=Color.red(pixel);
        int g=Color.green(pixel);
        int b=Color.blue(pixel);
        Log.i("RGB", "image clicked" +String.valueOf(r)+"     "+String.valueOf(g)+"  "+String.valueOf(b));
        closeMatch(pixel,10);
        return pixel;


    }

    public boolean closeMatch (int color1, int tolerance) {
        //if ((int) Math.abs (Color.red (color1) - Color.red (color2)) > tolerance ) return false;
        //if ((int) Math.abs (Color.green (color1) - Color.green (color2)) > tolerance ) return false;
        //if ((int) Math.abs (Color.blue (color1) - Color.blue (color2)) > tolerance ) return false;

        //"Jammu and Kashmir"
        if ( (Math.abs(Color.red (color1) - 127) < tolerance)  && (Math.abs (Color.green (color1) - 127) < tolerance)&&(Math.abs (Color.blue (color1) - 127 )< tolerance) )
        {
            Toast.makeText(this ,
                    "jammu  and Kashmir", Toast.LENGTH_LONG).show();
            String compareValue="Jammu and Kashmir";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //HP
        if ( (Math.abs(Color.red (color1) - 136) < tolerance)  && (Math.abs (Color.green (color1) - 0) < tolerance)&&(Math.abs (Color.blue (color1) - 21 )< tolerance) )
        {
            Toast.makeText(this ,
                    "Himachal Pradesh", Toast.LENGTH_LONG).show();
            String compareValue="HP";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }


        //Punjab
        if ( (Math.abs(Color.red (color1) - 237) < tolerance)  && (Math.abs (Color.green (color1) - 27) < tolerance)&&(Math.abs (Color.blue (color1) - 36 )< tolerance) )
        {
            Toast.makeText(this ,
                    "Punjab", Toast.LENGTH_LONG).show();
            String compareValue="Punjab";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Delhi
        if ( (Math.abs(Color.red (color1) - 254) < tolerance)  && (Math.abs (Color.green (color1) - 242) < tolerance)&&(Math.abs (Color.blue (color1) - 0 )< tolerance) )
        {
            Toast.makeText(this ,
                    "Delhi", Toast.LENGTH_LONG).show();
            String compareValue="Delhi";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Haryana
        if ( (Math.abs(Color.red (color1) - 250) < tolerance)  && (Math.abs (Color.green (color1) - 127) < tolerance)&&(Math.abs (Color.blue (color1) - 33 )< tolerance) )
        {
            Toast.makeText(this ,
                    "Haryana", Toast.LENGTH_LONG).show();
            String compareValue="Haryana";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }



        //North East
        if ( (Math.abs(Color.red (color1) - 239) < tolerance)  && (Math.abs (Color.green (color1) - 227) < tolerance)&&(Math.abs (Color.blue (color1) - 175 )< tolerance) )
        {
            Toast.makeText(this ,
                    "North East", Toast.LENGTH_LONG).show();
            String compareValue="North East";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Kolkata
        //if ( (Math.abs(Color.red (color1) - 255) < tolerance)  && (Math.abs (Color.green (color1) - 200) < tolerance)&&(Math.abs (Color.blue (color1) - 13 )< tolerance) )
            if ( (Math.abs(Color.red (color1) - 64) < tolerance)  && (Math.abs (Color.green (color1) - 0) < tolerance)&&(Math.abs (Color.blue (color1) - 63 )< tolerance) )
        {
            Toast.makeText(this ,
                    "Kolkata", Toast.LENGTH_LONG).show();
            String compareValue="Kolkata";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Uttar Pradesh (West)
        if ( (Math.abs(Color.red (color1) - 62) < tolerance)  && (Math.abs (Color.green (color1) - 71) < tolerance)&&(Math.abs (Color.blue (color1) - 200)< tolerance) )
        {
            Toast.makeText(this ,
                    "Uttar Pradesh (West)", Toast.LENGTH_LONG).show();
            String compareValue="Uttar Pradesh (West)";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }
        //Uttar Pradesh (East)
        if ( (Math.abs(Color.red (color1) - 163) < tolerance)  && (Math.abs (Color.green (color1) - 73) < tolerance)&&(Math.abs (Color.blue (color1) - 163)< tolerance) )
        {
            Toast.makeText(this ,
                    "Uttar Pradesh (East)", Toast.LENGTH_LONG).show();
            String compareValue="Uttar Pradesh (East)";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Rajasthan
        if ( (Math.abs(Color.red (color1) - 35) < tolerance)  && (Math.abs (Color.green (color1) - 177) < tolerance)&&(Math.abs (Color.blue (color1) - 77)< tolerance) )
        {
            Toast.makeText(this ,
                    "Rajasthan", Toast.LENGTH_LONG).show();
            String compareValue="Rajasthan";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }


        //Madhya Pradesh
        if ( (Math.abs(Color.red (color1) - 0) < tolerance)  && (Math.abs (Color.green (color1) - 163) < tolerance)&&(Math.abs (Color.blue (color1) - 232)< tolerance) )
        {
            Toast.makeText(this ,
                    "Madhya Pradesh", Toast.LENGTH_LONG).show();
            String compareValue="Madhya Pradesh";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Gujarat
        if ( (Math.abs(Color.red (color1) - 154) < tolerance)  && (Math.abs (Color.green (color1) - 217) < tolerance)&&(Math.abs (Color.blue (color1) - 234)< tolerance) )
        {
            Toast.makeText(this ,
                    "Gujarat", Toast.LENGTH_LONG).show();
            String compareValue="Gujarat";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Mumbai
        if ( (Math.abs(Color.red (color1) - 253) < tolerance)  && (Math.abs (Color.green (color1) - 237) < tolerance)&&(Math.abs (Color.blue (color1) - 224)< tolerance) )
        {
            Toast.makeText(this ,
                    "Mumbai", Toast.LENGTH_LONG).show();
            String compareValue="Mumbai";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }


        //Maharashtra
        if ( (Math.abs(Color.red (color1) - 112) < tolerance)  && (Math.abs (Color.green (color1) - 146) < tolerance)&&(Math.abs (Color.blue (color1) - 191)< tolerance) )
        {
            Toast.makeText(this ,
                    "Maharashtra", Toast.LENGTH_LONG).show();
            String compareValue="Maharashtra";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Andhra Pradesh
        if ( (Math.abs(Color.red (color1) - 255) < tolerance)  && (Math.abs (Color.green (color1) - 255) < tolerance)&&(Math.abs (Color.blue (color1) - 255)< tolerance) )
        {
            Toast.makeText(this ,
                    "Andhra Pradesh", Toast.LENGTH_LONG).show();
            String compareValue="Andhra Pradesh";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }


        //Karnataka
        if ( (Math.abs(Color.red (color1) - 195) < tolerance)  && (Math.abs (Color.green (color1) - 195) < tolerance)&&(Math.abs (Color.blue (color1) - 195)< tolerance) )
        {
            Toast.makeText(this ,
                    "Karnataka", Toast.LENGTH_LONG).show();
            String compareValue="Karnataka";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Kerala
        if ( (Math.abs(Color.red (color1) - 127) < tolerance)  && (Math.abs (Color.green (color1) - 128) < tolerance)&&(Math.abs (Color.blue (color1) - 0)< tolerance) )
        {
            Toast.makeText(this ,
                    "Kerala", Toast.LENGTH_LONG).show();
            String compareValue="Kerala";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Tamilnadu
        if ( (Math.abs(Color.red (color1) - 0) < tolerance)  && (Math.abs (Color.green (color1) - 255) < tolerance)&&(Math.abs (Color.blue (color1) - 255)< tolerance) )
        {
            Toast.makeText(this ,
                    "Tamilnadu", Toast.LENGTH_LONG).show();
            String compareValue="Tamilnadu";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }


        //Chennai
        //if ( (Math.abs(Color.red (color1) - 255) < tolerance)  && (Math.abs (Color.green (color1) - 0) < tolerance)&&(Math.abs (Color.blue (color1) - 128)< tolerance) )
        if ( (Math.abs(Color.red (color1) - 0) < tolerance)  && (Math.abs (Color.green (color1) - 0) < tolerance)&&(Math.abs (Color.blue (color1) - 0)< tolerance) )
        {
            Toast.makeText(this ,
                    "Chennai", Toast.LENGTH_LONG).show();
            String compareValue="Tamilnadu";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }


        //Orissa
        if ( (Math.abs(Color.red (color1) -178) < tolerance)  && (Math.abs (Color.green (color1) - 230) < tolerance)&&(Math.abs (Color.blue (color1) - 25)< tolerance) )
        {
            Toast.makeText(this ,
                    "Orissa", Toast.LENGTH_LONG).show();
            String compareValue="Orissa";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }

        //Bihar and Jharkhand
        if ( (Math.abs(Color.red (color1) - 184) < tolerance)  && (Math.abs (Color.green (color1) - 122) < tolerance)&&(Math.abs (Color.blue (color1) - 87)< tolerance) )
        {
            Toast.makeText(this ,
                    "Bihar and Jharkhand", Toast.LENGTH_LONG).show();
            String compareValue="Bihar and Jharkhand";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }


        //West Bengal
        if ( (Math.abs(Color.red (color1) - 253) < tolerance)  && (Math.abs (Color.green (color1) - 174) < tolerance)&&(Math.abs (Color.blue (color1) - 200)< tolerance) )
        {
            Toast.makeText(this ,
                    "West Bengal", Toast.LENGTH_LONG).show();
            String compareValue="West Bengal";
            if (compareValue != null) {
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner1.setSelection(spinnerPosition);
            }
        }
        return true;
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



 class Typewriter extends AppCompatTextView {

    private CharSequence mText;
    private int mIndex;
    private long mDelay = 500; //Default 500ms delay


    public Typewriter(Context context) {
        super(context);
    }

    public Typewriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));
            if(mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;

        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }
}

