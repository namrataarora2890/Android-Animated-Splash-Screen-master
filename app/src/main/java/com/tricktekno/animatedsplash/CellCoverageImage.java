package com.tricktekno.animatedsplash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.davemorrissey.labs.subscaleview.decoder.DecoderFactory;
import com.davemorrissey.labs.subscaleview.decoder.ImageDecoder;
import com.davemorrissey.labs.subscaleview.decoder.ImageRegionDecoder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CellCoverageImage extends Activity implements AdapterView.OnItemSelectedListener {

    // Set your Image URL into a string
    String URL;
    String circle ;
    String technology;
    String operator;
    String ip;
    Spinner spinner1,spinner2,spinner3;
    List<String> list1,list2,list3 ;
    RequestQueue requestQueue;
    ImageView image;
    Button button;
    ProgressDialog mProgressDialog;
    SubsamplingScaleImageView scaleImageView;
    Bundle bundle;
    //ZoomableDraweeView view;
    SimpleDraweeView image1;
    String img_path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //isNetworkConnectionAvailable();

        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        circle = intent.getStringExtra("circle");
        technology = intent.getStringExtra("technology");
        operator = intent.getStringExtra("operator");
        ip = getString(R.string.ip);
        //URL = "http://"+ip+"/loc_image?"+ "circle=" + circle + "&tech=" + technology + "&operator=" + operator;

        setContentView(R.layout.cell_coverage_image_activity);
        image = (ImageView)findViewById(R.id.coverage);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);

//        scaleImageView = (ImageView)findViewById(R.id.coverage);
//        scaleImageView.setImage(ImageSource.resource(R.drawable.default_ind));
//        scaleImageView.setZ(2);
//        scaleImageView.setQuickScaleEnabled(true);

//        new DownloadImage().execute(URL);
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        addItemsOnSpinner1local();
        addListenerOnSpinnerItemSelection();
        //Fresco.initialize(this);
        //set_button_click_listner();

    }


    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private BitmapFactory.Options options = new BitmapFactory.Options();
        long inTime,completeTime;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Create a progressdialog
            mProgressDialog = new ProgressDialog(CellCoverageImage.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Hold On..");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;


            options.inJustDecodeBounds = false;
            options.inSampleSize = 4;
            try {
                inTime = System.currentTimeMillis();
                java.net.URL url=new java.net.URL("http://agni.iitd.ac.in/static/404_10_2G.jpg");
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                //InputStream input = new java.net.URL(imageURL).openStream();
                //System.out.println("** Input = " + input);
                //bitmap = BitmapFactory.decodeStream(input,null,options);
                //bitmap = BitmapFactory.decodeStream(input);
                System.out.println("** Input = " + bitmap);
                //Base64InputStream base64 = new Base64InputStream(input, URL_SAFE);
                //System.out.println("** Base64 = " + base64);
                //GZIPInputStream data1 = new GZIPInputStream(new Base64InputStream(input, URL_SAFE));
               // bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_ind);

               // Picasso.with(getApplicationContext()).load(imageURL).into(image);
                //final Picasso picasso = Picasso.with(getApplicationContext());
                //loadImageByUrl(imageURL);


            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bitmap !=null)
                return bitmap;
            else
                return BitmapFactory.decodeResource(getResources(),
                        R.drawable.butterfly);

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            System.out.println("** onPostExecute ..."+result);
            // Set the bitmap into ImageView
             image.setImageBitmap(result);
           // imageView.setImage(ImageSource.bitmap(result));

            //scaleImageView.setImage(ImageSource.uri(URL));
            completeTime = System.currentTimeMillis() - inTime;
            mProgressDialog.dismiss();
        }
    }


    public void addItemsOnSpinner1local() {
        ArrayAdapter<String> dataAdapter;
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        list1 = new ArrayList<String>();
        list1 = Arrays.asList(getResources().getStringArray(R.array.circle_names));
        //ArrayAdapter<CharSequence> dataAdapterr= ArrayAdapter.createFromResource(this, R.array.circle_names , android.R.layout.simple_spinner_item);
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner1.setAdapter(dataAdapter);
        if (circle!=null)
        spinner1.setSelection(dataAdapter.getPosition(circle));
        else
            Toast.makeText(getBaseContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();

    }



    public void addItemsOnSpinner2() {
        final  ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CellCoverageImage.this);
        progressDialog.setMessage( getString(R.string.data_fetch));

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        String baseUrl = "http://"+ip+"/technology_detail?circle="+ String.valueOf(spinner1.getSelectedItem());



        list2 = new ArrayList<String>();

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();

        JsonArrayRequest arrReq = new JsonArrayRequest(
                Request.Method.GET,
                baseUrl,
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
                                if (!technology.matches("(.*)CDMA(.*)"))
                                {list2.add(technology);}
                                System.out.println("technology"+technology);


                            }
                        }catch (JSONException e){

                            Log.e(" exception  ::", e.toString());
                            e.printStackTrace();
                        }
                        spinner2.setAdapter(dataAdapter);
                        if (technology!=null)
                            spinner2.setSelection(dataAdapter.getPosition(technology));

                        // here set the adapter on rcving response
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


        requestQueue.add(arrReq);
        progressDialog.show();



        //spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }
    public void addItemsOnSpinner3() {

        spinner3 = (Spinner) findViewById(R.id.spinner3);


        String baseUrl = "http://"+ip+"/operator_technology_detail?circle="+ String.valueOf(spinner1.getSelectedItem())+"&technology="+String.valueOf(spinner2.getSelectedItem());
        final  ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CellCoverageImage.this);
        progressDialog.setMessage( getString(R.string.data_fetch));


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
                        progressDialog.dismiss();
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject tech = response.getJSONObject(i);
                                // Get the current student (json object) data
                                String operator = tech.getString("circle_operator");
                                list3.add(operator);

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

                        progressDialog.dismiss();
                        //Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
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
                        } else if (error instanceof NetworkError) {
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


        requestQueue.add(arrReq);
        progressDialog.show();

        //spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);


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
                if (spinner2.getSelectedItem()!=null)
                {
                    technology=String.valueOf(spinner2.getSelectedItem());
                    addItemsOnSpinner3();
                }
                break;
            case R.id.spinner3:
                Log.i("item", "selected");

                if (spinner2.getSelectedItem()!=null &&  spinner3.getSelectedItem()!=null)

                {
                    Log.i("calling", "URL");

                    URL = "http://"+ip+"/loc_image?"+ "circle=" + String.valueOf(spinner1.getSelectedItem()) + "&tech=" + String.valueOf(spinner2.getSelectedItem()) + "&operator=" + String.valueOf(spinner3.getSelectedItem());
                    loadPicture(URL,true);

                    System.out.println("Hi......");
//                    image1 = (SimpleDraweeView)findViewById(R.id.image);
//                    image1.setImageURI("http://agni.iitd.ac.in/static/404_10_3G.jpg");

//                    view = (ZoomableDraweeView) findViewById(R.id.zoomable);
//
//                    DraweeController ctrl = Fresco.newDraweeControllerBuilder().setUri(
//                            URL.replace(" ", "%20")).setTapToRetryEnabled(true).build();
//                    System.out.println("ctrl**"+ctrl);
//                    GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
//                            .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
//                            .setProgressBarImage(new ProgressBarDrawable())
//                            .build();
//                    System.out.println("Hierarchy**"+hierarchy);
//
//                    view.setController(ctrl);
//                    System.out.println("Dying after ctrl ");
//                    view.setHierarchy(hierarchy);
//                    System.out.println("Dying after hierarchy ");

                     //new DownloadImage().execute("http://agni.iitd.ac.in/static/404_10_3G.jpg");

                    //loadImageByUrl(URL.replace(" ", "%20"));
                 //   Picasso.with(getApplicationContext()).load(URL.replace(" ", "%20")).into(image);
                    //Picasso.with(getApplicationContext()).load(R.drawable.default_ind).into(image);
                    //Picasso.with(getApplicationContext()).load("http://agni.iitd.ac.in/static/404_10_3G.jpg");

                }
                else{

                    Toast.makeText(getBaseContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();}
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Log.e("hell you item ::","");
        // TODO Auto-generated method stub
    }

    public void set_button_click_listner(){

        Button button= (Button) findViewById(R.id.butt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner2.getSelectedItem()!=null &&  spinner3.getSelectedItem()!=null) {
                    URL = "http://" + ip + "/loc_image?" + "circle=" + String.valueOf(spinner1.getSelectedItem()) + "&tech=" + String.valueOf(spinner3.getSelectedItem()) + "&operator=" + String.valueOf(spinner2.getSelectedItem());
                    new DownloadImage().execute(URL);
                }
                else{

                    Toast.makeText(getBaseContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    @Override
    public void onBackPressed() {

        //Intent myIntent = new Intent(CellCoverageImage.this, SecondActivity.class);
        //CellCoverageImage.this.startActivity(myIntent,bundle);
        //moveTaskToBack(true);
        //finish();

        Intent openMainActivity= new Intent(CellCoverageImage.this, SecondActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(openMainActivity, 0);
    }

    public void loadImageByUrl(final String url) {
        final  ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CellCoverageImage.this);
        progressDialog.setMessage( getString(R.string.image_fetch));

        progressDialog.show();
        scaleImageView.setMaxScale(5.0f);
        final Picasso picasso = Picasso.with(getApplicationContext());

        scaleImageView.setBitmapDecoderFactory(new DecoderFactory<ImageDecoder>() {
            @Override
            public ImageDecoder make() throws IllegalAccessException, java.lang.InstantiationException {

                return new PicassoDecoder(url, picasso);
            }
        });

        scaleImageView.setRegionDecoderFactory(new DecoderFactory<ImageRegionDecoder>() {
            @Override
            public ImageRegionDecoder make() throws IllegalAccessException, java.lang.InstantiationException {
                return new PicassoRegionDecoder(new OkHttpClient());
            }
        });

        //scaleImageView.setOnImageEventListener(new SubScalingImageViewListener());
        scaleImageView.setImage(ImageSource.uri(url));
        progressDialog.dismiss();

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


    public void getImagePathxx() {



        final  ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CellCoverageImage.this);
        progressDialog.setMessage( getString(R.string.data_fetch));


        String baseUrl = "http://"+ip+"/loc_image?"+ "circle=" + String.valueOf(spinner1.getSelectedItem()) + "&tech=" + String.valueOf(spinner2.getSelectedItem()) + "&operator=" + String.valueOf(spinner3.getSelectedItem());


        System.out.println("** ** baseUrl = " + baseUrl);


        JsonArrayRequest arrReq = new JsonArrayRequest(
                Request.Method.GET,
                baseUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        try{
                            System.out.println("** ** Response = " + response);
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject tech = response.getJSONObject(i);
                                // Get the current student (json object) data
                                 img_path = tech.getString("image_path");

                                 Log.e("img_path\n", img_path);

                            }
                        }catch (JSONException e){
                            Log.e("on my god exception  ::", e.toString());
                            e.printStackTrace();
                        }

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
        System.out.println("image_path"+img_path);
       // return img_path;
    }

    String getImagePath(String imagePathUrl) {
        System.out.println("** ** imagePathUrl = " + imagePathUrl);
        String imagePath = "";
        try {
            java.net.URL url = new java.net.URL(imagePathUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            System.out.println("** ** content = " + content.toString());

            String jsonString = content.toString();
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            imagePath = jsonObject.getString("image_path");
            System.out.println("** ** imagePath = " + imagePath);
        } catch (Exception e) {
            e.printStackTrace();
            imagePath = "https://www.android.com/static/2016/img/share/andy-lg.png";
        }

        return imagePath;
    }

    private void loadPicture( String photoUrl, final Boolean shouldLoadAgain) {

        final ProgressDialog Dialog = new ProgressDialog(CellCoverageImage.this);
        Dialog.setTitle("Hold On..");
        Dialog.setMessage("Loading...");
        Dialog.setIndeterminate(false);
        Dialog.show();

        Glide.with(this).load(getImagePath(URL.replace(" ", "%20")))
                .thumbnail(0.5f)
                .crossFade().fallback(R.drawable.default_ind)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.butterfly)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (shouldLoadAgain)
                            loadPicture( "https://www.android.com/static/2016/img/share/andy-lg.png", false);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        Dialog.dismiss();
                        return false;
                    }
                })
                .into(image);


    }


    }

