package com.tricktekno.animatedsplash;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BarActivity extends ActionBarActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {
    Bundle bundle;
    String parameter;
    RequestQueue requestQueue;
    String baseUrl ;
    BarChart lchart;
    BarChart tchart;
    BarChart schart;
    BarChart dchart;
    BarChart chart;
    String circle;
    String technology;
    ArrayList<Performance_parameters> performance_parameters=new ArrayList<>();

    ArrayList<IBarDataSet> latencyBar =new ArrayList<>();
    ArrayList<IBarDataSet> throughputBar =new ArrayList<>();
    ArrayList<IBarDataSet> speedBar =new ArrayList<>();
    ArrayList<IBarDataSet> droprateBar =new ArrayList<>();

    //Array list of bar dataset (Multiple possible)
    ArrayList<BarEntry> throughputVal = new ArrayList<>();
    ArrayList<BarEntry> droprateval = new ArrayList<>();
    ArrayList<BarEntry>  speedval= new ArrayList<>();
    ArrayList<BarEntry> latencyVal = new ArrayList<>();//array list of bar entry
    ArrayList<String> operatorxAxis = new ArrayList<>();
    String ip;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNetworkConnectionAvailable();
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        Intent intent = getIntent();
        ip = getString(R.string.ip);
        /*****************************************************/
        circle = intent.getStringExtra("circle");
        technology = intent.getStringExtra("technology");
        baseUrl= "http://"+ip+"/performance_parameters?"+"circle="+circle+"&tech="+technology ;
        getPerformanceParameters();//if it's a string you stored.
        String st= circle+" "+technology;
        //setTitle(st);

        setContentView(R.layout.bar_activity);

        final TextView textViewToChange = (TextView) findViewById(R.id.tvx);
        textViewToChange.setText(
                circle+" "+technology);


        //BarChart chart = (BarChart) findViewById(R.id.chart);
        //BarData data = new BarData(getXAxisValues(), getDataSet());
        //BarData data = new BarData(latencyxAxis, latencyBar);
        //chart.setData(data);
        //chart.setDescription("My Chart");
        //chart.animateXY(2000, 2000);
        //chart.invalidate();
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }


    public void getPerformanceParameters() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(BarActivity.this);
        progressDialog.setMessage(getString(R.string.data_fetch));
        requestQueue = Volley.newRequestQueue(this);

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

                                Performance_parameters p=new Performance_parameters();
                                // Get current json object
                                JSONObject student = response.getJSONObject(i);
                                // Get the current student (json object) data
                                String operator_name = student.getString("circle_operator");
                                p.setName(operator_name );
                                operatorxAxis.add(operator_name);
                                JSONObject score = student.getJSONObject("score");

                                String latency = score.getString("latency");
                                p.setLatency(Float.parseFloat(latency));
                                BarEntry v = new BarEntry(Float.parseFloat(latency), i);
                                latencyVal.add(v );

                                String droprate = score.getString("droprate");
                                p.setDroprate(Float.parseFloat(droprate));
                                BarEntry v1 = new BarEntry(Float.parseFloat(droprate), i);
                                droprateval.add(v1 );

                                String speed = score.getString("speed");
                                p.setSpeed(Float.parseFloat(speed));
                                BarEntry v2 = new BarEntry(Float.parseFloat(speed), i);
                                speedval.add(v2 );

                                String throughput = score.getString("throughput");
                                p.setSpeed(Float.parseFloat(throughput));
                                BarEntry v3 = new BarEntry(Float.parseFloat(throughput), i);
                                throughputVal.add(v3);
                                //s.setImage(R.drawable.spitzer);
                                performance_parameters.add(p);


                            }
                            BarDataSet barDataSet1 = new BarDataSet(latencyVal, "Latency (ms) Q4 2017 ");
                            barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
                            latencyBar.add(barDataSet1);
                            BarDataSet barDataSet2 = new BarDataSet(throughputVal, "Throughput (Mbps) Q4 2017");
                            barDataSet2.setColors(ColorTemplate.PASTEL_COLORS);
                            throughputBar.add(barDataSet2);
                            BarDataSet barDataSet3 = new BarDataSet(speedval, "Speed (kbps) Q4 2017");
                            barDataSet3.setColors(ColorTemplate.LIBERTY_COLORS);
                            speedBar.add(barDataSet3);
                            BarDataSet barDataSet4 = new BarDataSet(droprateval, "Droprate Q4 2017");
                            barDataSet4.setColors(ColorTemplate.JOYFUL_COLORS);
                            droprateBar.add(barDataSet4);

                        }catch (JSONException e){
                            Log.e("on my god exception  ::", e.toString());
                            e.printStackTrace();
                        }
                        set_latency_bar_chart();
                        set_speed_bar_chart();
                        set_throughput_bar_chart();
                        set_droprate_bar_chart();
                        //lv.setAdapter(new CustomAdapter(getApplicationContext(),spaceships));
                        }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        progressDialog.dismiss();
                        //Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.e("Timeout /No conn ::", error.toString());
                            Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();


                        } else if (error instanceof AuthFailureError) {
                            Log.e("Auth Failure ::", error.toString());
                            Toast.makeText(getBaseContext() , getString(R.string.auth_error), Toast.LENGTH_LONG).show();

                        } else if (error instanceof ServerError) {

                            Log.e("Server Error ::", error.toString());
                            Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError ) {


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
        progressDialog.show();

        //Log.d(TAG,"after set adapter .....");

    }

    public void set_latency_bar_chart()
    {
        lchart = (BarChart) findViewById(R.id.chart1);
        //BarData data = new BarData(getXAxisValues(), getDataSet());
        BarData data = new BarData(operatorxAxis,latencyBar );
        lchart.setData(data);
        lchart.setDescription("Latency");
        lchart.setContentDescription("Latency");
        lchart.animateXY(2000, 2000);
        lchart.invalidate();
        lchart.getXAxis().setLabelsToSkip(0);
        Legend l = lchart.getLegend();

        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(2f);
       // parameter="latency";
        //lchart.setOnChartGestureListener(this);
       // chart.setOnChartValueSelectedListener(this);
        lchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent myIntent = new Intent(BarActivity.this, LineChartActivity.class);
                myIntent.putExtra("circle", circle);
                myIntent.putExtra("technology", technology);
                myIntent.putExtra("operator", lchart.getXValue(e.getXIndex()));
                myIntent.putExtra("description", lchart.getContentDescription());
                BarActivity.this.startActivity(myIntent,bundle);
            }
            @Override
            public void onNothingSelected(){}
        });
    }

    public void set_speed_bar_chart()
    {
        schart = (BarChart) findViewById(R.id.chart2);
        //BarData data = new BarData(getXAxisValues(), getDataSet());
        BarData data = new BarData(operatorxAxis,speedBar );
        schart.setData(data);
        schart.setDescription("Speed");
        schart.setContentDescription("Speed");
        schart.animateXY(2000, 2000);
        schart.invalidate();
        schart.getXAxis().setLabelsToSkip(0);
        Legend l = schart.getLegend();

        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        //schart.setOnChartGestureListener(this);
        //chart.setOnChartValueSelectedListener(this);
        schart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent myIntent = new Intent(BarActivity.this, LineChartActivity.class);
                myIntent.putExtra("circle", circle);
                myIntent.putExtra("technology", technology);
                myIntent.putExtra("operator", schart.getXValue(e.getXIndex()));
                myIntent.putExtra("description", schart.getContentDescription());
                BarActivity.this.startActivity(myIntent,bundle);
            }
            @Override
            public void onNothingSelected(){}
        });
    }
    public void set_throughput_bar_chart()
    {
        tchart = (BarChart) findViewById(R.id.chart3);
        //BarData data = new BarData(getXAxisValues(), getDataSet());
        BarData data = new BarData(operatorxAxis,throughputBar );
        tchart.setData(data);
        tchart.setDescription("Throughput");
        tchart.setContentDescription("Throughput");
        tchart.animateXY(2000, 2000);
        tchart.invalidate();
        tchart.getXAxis().setLabelsToSkip(0);
        Legend l = tchart.getLegend();

        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        //tchart.setOnChartGestureListener(this);
        //tchart.setOnChartValueSelectedListener(this);
        tchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent myIntent = new Intent(BarActivity.this, LineChartActivity.class);
                myIntent.putExtra("circle", circle);
                myIntent.putExtra("technology", technology);
                myIntent.putExtra("operator", tchart.getXValue(e.getXIndex()));
                myIntent.putExtra("description", tchart.getContentDescription());
                BarActivity.this.startActivity(myIntent,bundle);
            }
            @Override
            public void onNothingSelected(){}
        });
    }

    public void set_droprate_bar_chart()
    {
      dchart = (BarChart) findViewById(R.id.chart4);
        //BarData data = new BarData(getXAxisValues(), getDataSet());
        BarData data = new BarData(operatorxAxis,droprateBar );
        dchart.setData(data);
        dchart.setDescription("Droprate");
        dchart.setContentDescription("Droprate");
        dchart.animateXY(2000, 2000);
        dchart.invalidate();
        dchart.getXAxis().setLabelsToSkip(0);
        Legend l = dchart.getLegend();

        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        //dchart.setOnChartGestureListener(this);
       // dchart.setOnChartValueSelectedListener(this);
        dchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent myIntent = new Intent(BarActivity.this, LineChartActivity.class);
                myIntent.putExtra("circle", circle);
                myIntent.putExtra("technology", technology);
                myIntent.putExtra("operator", dchart.getXValue(e.getXIndex()));
                myIntent.putExtra("description", dchart.getContentDescription());
                BarActivity.this.startActivity(myIntent,bundle);
            }
            @Override
            public void onNothingSelected(){}
        });
    }

    @Override
    public void onChartGestureStart(MotionEvent me,
                                    ChartTouchListener.ChartGesture
                                            lastPerformedGesture) {

        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me,
                                  ChartTouchListener.ChartGesture
                                          lastPerformedGesture) {

        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            // or highlightTouch(null) for callback to onNothingSelected(...)
            chart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

        Log.i("DoubleTap", "Chart double-tapped.");

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2,
                             float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: "
                + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + chart.getLowestVisibleXIndex()
                + ", high: " + chart.getHighestVisibleXIndex());

        Log.i("MIN MAX", "xmin: " + chart.getXChartMin()
                + ", xmax: " + chart.getXChartMax()
                + ", ymin: " + chart.getYChartMin()
                + ", ymax: " + chart.getYChartMax()
                + ", Xindex: " +chart.getXValue(e.getXIndex())
        );
        //final String x = chart.getXAxis().getValueFormatter().getFormattedValue(e.getX(), chart.getXAxis());


        //Log.i("set description ",chart.getContentDescription());
        Intent myIntent = new Intent(BarActivity.this, LineChartActivity.class);
        myIntent.putExtra("circle", circle);
        myIntent.putExtra("technology", technology);
        myIntent.putExtra("operator", chart.getXValue(e.getXIndex()));
        myIntent.putExtra("description", chart.getContentDescription());
        BarActivity.this.startActivity(myIntent,bundle);
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onBackPressed() {

        Intent myIntent = new Intent(BarActivity.this, SecondActivity.class);


        BarActivity.this.startActivity(myIntent,bundle);
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



class Performance_parameters
{
    private String name;
    double latency;
    double droprate;
    double speed;
    double avg_throughput;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatency() {
        return latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public double getDroprate() {
        return droprate;
    }

    public void setDroprate(double droprate) {
        this.droprate = droprate;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAvg_throughput() {
        return avg_throughput;
    }

    public void setAvg_throughput(double avg_throughput) {
        this.avg_throughput = avg_throughput;
    }



}