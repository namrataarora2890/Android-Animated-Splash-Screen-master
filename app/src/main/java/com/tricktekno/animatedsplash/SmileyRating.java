package com.tricktekno.animatedsplash;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 4/2/18.
 */

public class SmileyRating  extends AppCompatActivity implements SmileRating.OnSmileySelectionListener, SmileRating.OnRatingSelectedListener, View.OnClickListener,AdapterView.OnItemSelectedListener
{
    private static final String TAG = "Smiley Rating";

    private SmileRating sr_availabilty,sr_fluctuation,sr_speed;
    int availabilty,fluctuation,speed;
    SimpleRatingBar Wh,Fb,Gm,Wb,Yt;
    private static  Spinner spinner1, spinner2;
    Bundle bundle;
    EditText etPhone,etEmail;
    Button btnPost;
    OperatorFeedback off;
    String ip;
    String url;
    List<String> list1 ;
    List<String> list2 ;
    String msg="Enter complete data please!";

    ArrayAdapter<String> dataAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNetworkConnectionAvailable();
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        ip = getString(R.string.ip);
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.smiley_rating);
        addItemsOnSpinner1local();
        addListenerOnSpinnerItemSelection();

        btnPost = (Button) findViewById(R.id.btnSu);
        btnPost.setOnClickListener(this);

        etPhone= (EditText) findViewById(R.id.etxtPhone);
        etEmail=(EditText) findViewById(R.id.etxtEmail);

        sr_availabilty=(SmileRating) findViewById(R.id.ratingView2);
        sr_fluctuation=(SmileRating) findViewById(R.id.ratingView3);
        sr_speed=(SmileRating) findViewById(R.id.ratingView4);

        Wh=( SimpleRatingBar )findViewById(R.id.whatsapp_rating);
        Fb=( SimpleRatingBar )findViewById(R.id.facebook_rating);
        Gm=( SimpleRatingBar )findViewById(R.id.gmaps_rating);
        Yt=( SimpleRatingBar )findViewById(R.id.youtube_rating);
        Wb=( SimpleRatingBar )findViewById(R.id.webbrowsing_rating);


    }

    @Override
    public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
        switch (smiley) {
            case SmileRating.BAD:
                Log.i(TAG, "Bad");
                break;
            case SmileRating.GOOD:
                Log.i(TAG, "Good");
                break;
            case SmileRating.GREAT:
                Log.i(TAG, "Great");
                break;
            case SmileRating.OKAY:
                Log.i(TAG, "Okay");
                break;
            case SmileRating.TERRIBLE:
                Log.i(TAG, "Terrible");
                break;
            case SmileRating.NONE:
                Log.i(TAG, "None");
                break;
        }
    }

    @Override
    public void onRatingSelected(int level, boolean reselected) {
        Log.i(TAG, "Rated as: " + level + " - " + reselected);
    }

    @Override
    public void onBackPressed() {

            Intent myIntent = new Intent(SmileyRating.this, SecondActivity.class);

            SmileyRating.this.startActivity(myIntent,bundle);

        }





        private class HttpAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... urls) {

                off = new OperatorFeedback();
                off.setPhone(etPhone.getText().toString());
                off.setEmail(etEmail.getText().toString());

                off.setAvailability(sr_availabilty.getRating());
                off.setFluctuation(sr_fluctuation.getRating());
                off.setSpeed(sr_speed.getRating());

                off.setWhatsapp_qoe(Wh.getRating());
                off.setFacebook_qoe(Fb.getRating());
                off.setGmaps_qoe(Gm.getRating());
                off.setYoutube_qoe(Yt.getRating());
                off.setBrowsing_qoe(Wb.getRating());


                return POST(urls[0],off);

            }
            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute(String result) {

               // Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            }
        }


        public static String POST(String url,OperatorFeedback person){
            InputStream inputStream = null;
            String result = "";
            try {

                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost(url);

                String json = "";

                // 3. build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("circle",String.valueOf(spinner1.getSelectedItem()));
                jsonObject.accumulate("operator",String.valueOf((spinner2.getSelectedItem())));

                jsonObject.accumulate("phone", person.getPhone());
                jsonObject.accumulate("email", person.getEmail());

                jsonObject.accumulate("availability", person.getAvailability());
                jsonObject.accumulate("fluctuation", person.getFluctuation());
                jsonObject.accumulate("speed", person.getSpeed());

                jsonObject.accumulate("whatsapp", person.getWhatsapp_qoe());
                jsonObject.accumulate("facebook", person.getFacebook_qoe());
                jsonObject.accumulate("browsing", person.getBrowsing_qoe());
                jsonObject.accumulate("gmaps", person.getGmaps_qoe());
                jsonObject.accumulate("youtube", person.getYoutube_qoe());

                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();

                // ** Alternative way to convert Person object to JSON string usin Jackson Lib
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person);

                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json);

                // 6. set httpPost Entity
                httpPost.setEntity(se);

                // 7. Set some headers to inform server about the type of the content
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httpPost);

                // 9. receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // 10. convert inputstream to string
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            // 11. return result
            return result;
        }

        private static String convertInputStreamToString(InputStream inputStream) throws IOException{
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        @Override
        public void onClick(View view) {

            switch(view.getId()){
                case R.id.btnSu:
                    if(!validate())
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                    else{
                    // call AsynTask to perform network operation on separate thread
                    new HttpAsyncTask().execute("http://"+ip+"/opfeedback/");
                    Toast.makeText(getBaseContext(), "Thank You for Your Feedback",
                            Toast.LENGTH_LONG).show();
                    final Intent intent = new Intent(SmileyRating.this, SecondActivity.class);

                    //startActivity(intent);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            getBaseContext().startActivity(intent);

                        }
                    }, 1500);

            }}

        }

        private boolean validate(){

            if(etEmail.getText().toString().trim().equals(""))
                return false;
           else if(!isValidPhone(etPhone.getText().toString().trim())) {
                msg = "Enter Correct Phone number please";
                return false;
            }
            else
                return true;
        }


    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spcircle);
        //String baseUrl = "http://10.0.2.2:8000/circle_detail/";
        //String baseUrl = "http://192.168.1.106:8000/circle_detail/";
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
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.e("Timeout /No conn ::", error.toString());
                            Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();


                        } else if (error instanceof AuthFailureError) {
                            Log.e("Auth Failure ::", error.toString());
                            Toast.makeText(getBaseContext() , getString(R.string.auth_error), Toast.LENGTH_LONG).show();

                        } else if (error instanceof ServerError) {

                            Log.e("Server Error ::", error.toString());
                            Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {


                            Log.e("Network Error ::", error.toString());
                            Toast.makeText(getBaseContext() , getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {

                            Log.e("Parse  Error ::", error.toString());
                            Toast.makeText(getBaseContext() , getString(R.string.parse_error), Toast.LENGTH_LONG).show();
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
        spinner1 = (Spinner) findViewById(R.id.spcircle);
        list1 = new ArrayList<String>();
        list1= Arrays.asList(getResources().getStringArray(R.array.circle_names));
        //ArrayAdapter<CharSequence> dataAdapterr= ArrayAdapter.createFromResource(this, R.array.circle_names , android.R.layout.simple_spinner_item);
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner1.setAdapter(dataAdapter);

    }
    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spcircle);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner1.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        addItemsOnSpinner2();

    }

    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spoperator);
        //String baseUrl = "http://10.0.2.2:8000/technology_detail/"+ String.valueOf(spinner1.getSelectedItem());
        //String baseUrl = "http://10.0.2.2:8000/technology_detail?circle="+ String.valueOf(spinner1.getSelectedItem());
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
                                String technology = tech.getString("circle_operator");
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
                        Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        Log.e("hell you Volley ::", error.toString());
                    }
                }
        );
        arrReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(arrReq);


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Log.e("hell you item ::","");
        // TODO Auto-generated method stub
    }

    public static boolean isValidPhone(String s)
    {

        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);

        return (m.find() && m.group().equals(s));
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
