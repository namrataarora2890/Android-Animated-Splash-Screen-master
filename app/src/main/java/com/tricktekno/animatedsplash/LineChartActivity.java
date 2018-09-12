package com.tricktekno.animatedsplash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by root on 31/12/17.
 */




public class LineChartActivity extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {

    private LineChart mChart;
    RequestQueue requestQueue;
    String baseUrl ;
    String circle;
    String technology;
    String operator;
    String description;
    ArrayList<Entry> yVals= new ArrayList<Entry>();
    ArrayList<Float> fyVals= new ArrayList<Float>();
    //ArrayList<String> xVals;
    ArrayList<String> xVals = new ArrayList<String>();
    String label;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To make full screen layout

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.line_chart_activity);
        TextView thisView = (TextView)findViewById(R.id.textViewlc);
        Intent intent = getIntent();
        /*****************************************************/
        circle = intent.getStringExtra("circle");
        technology = intent.getStringExtra("technology");
        operator = intent.getStringExtra("operator");
        description = intent.getStringExtra("description");
        Log.i("description",description);
        label=description;
        ip = getString(R.string.ip);
        baseUrl = "http://"+ip+"/historical_data?" + "circle=" + circle + "&tech=" + technology + "&operator=" + operator;
        getHistoricalData();//if it's a string you stored.
        String st = circle + " " +operator+ " "+technology+ " "+description;
        thisView.setText(st.toString());

    }

    private void SetLineChart()
    {

        mChart = (LineChart) findViewById(R.id.linechart);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        //mChart.clear();
        setData(label);
       // mChart.setDrawGridBackground(false);

        Legend l = mChart.getLegend();
        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        // no description text
        mChart.setDescription(description);
        mChart.setNoDataTextDescription("Data not available");
        // enable touch gestures
        mChart.setTouchEnabled(true);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(true);


        /*LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);

        LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);*/

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        //leftAxis.addLimitLine(upper_limit);
        //leftAxis.addLimitLine(lower_limit);

        //leftAxis.setAxisMaxValue(2f);
        leftAxis.setAxisMaxValue(Collections.max(fyVals)+2f);
        //leftAxis.setAxisMinValue(-1f);
        leftAxis.setAxisMaxValue(Collections.min(fyVals)-2f);

        //leftAxis.setYOffset(20f);
        //leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //leftAxis.setDrawZeroLine(false);
        // limit lines are drawn behind data (and not on top)
        //leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
        leftAxis.setEnabled(true);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setLabelRotationAngle(-45);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getXAxis().setLabelsToSkip(0);
        mChart.setDrawBorders(true);
        mChart.setExtraBottomOffset(10);
        mChart.invalidate();

    }


    public void getHistoricalData() {
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

                                //Performance_parameters p=new Performance_parameters();
                                // Get current json object
                                JSONObject student = response.getJSONObject(i);
                                // Get the current student (json object) data
                                String operator_name = student.getString("circle_operator");
                                if (label.equals("Droprate")) {
                                    JSONObject droprate_score = student.getJSONObject("droprate_score");

                                    Iterator<String> iter1 = droprate_score.keys();
                                    int index = 0;
                                    while (iter1.hasNext()) {
                                        String key = (String)iter1.next();
                                        Log.i("heyyy",key);
                                        xVals.add(key);
                                        //xVals.add("kk");
                                        try {
                                            String  val= droprate_score.getString(key);
                                            Log.i("valllyyy",val);
                                            //Object value = droprate_score.getString(key);
                                           // yVals.add(new Entry(Float.parseFloat(value.getClass().toString()), index));
                                            yVals.add(new Entry(Float.parseFloat(val), index));
                                            fyVals.add(Float.parseFloat(val));
                                        } catch (JSONException e) {
                                            // Something went wrong!
                                        }
                                        index++;
                                    }
                                }

                                if (label.equals("Latency")) {

                                    JSONObject latency_score = student.getJSONObject("latency_score");
                                    int index = 0;
                                    Iterator<String> iter2 = latency_score.keys();
                                    while (iter2.hasNext()) {
                                        String key = (String)iter2.next();
                                        Log.i("heyyy",key);
                                        xVals.add(key);
                                        try {
                                            String val = latency_score.getString(key);
                                            Log.i("valllyyy",val);
                                            yVals.add(new Entry(Float.parseFloat(val), index));
                                            fyVals.add(Float.parseFloat(val));
                                        } catch (JSONException e) {
                                            // Something went wrong!
                                        }
                                        index++;
                                    }
                                }
                                if (label.equals("Speed")) {
                                    int index = 0;
                                    JSONObject speed_score = student.getJSONObject("speed_score");
                                    Iterator<String> iter3 = speed_score.keys();
                                    while (iter3.hasNext()) {
                                        String key = (String)iter3.next();
                                        Log.i("heyyy",key);
                                        xVals.add(key);
                                        try {
                                            String value = speed_score.getString(key);
                                            Log.i("valllyyy",value);
                                            yVals.add(new Entry(Float.parseFloat(value), index));
                                            fyVals.add(Float.parseFloat(value));
                                        } catch (JSONException e) {
                                            // Something went wrong!
                                        }
                                        index++;
                                    }
                                }
                                if (label.equals("Throughput")) {
                                    int index = 0;
                                     JSONObject throughput_score = student.getJSONObject("throughput_score");
                                    Iterator<String> iter4 =  throughput_score.keys();
                                     while (iter4.hasNext()) {
                                    String key = (String) iter4.next();
                                    Log.i("heyyy",key);
                                    xVals.add(key.toString());
                                    try {
                                        String  value = throughput_score.getString(key);
                                        Log.i("valllyyy",value);
                                        yVals.add(new Entry(Float.parseFloat(value), index));
                                        fyVals.add(Float.parseFloat(value));
                                    } catch (JSONException e) {
                                        // Something went wrong!
                                    }
                                         index++;
                                }


                            }

                            }


                        }catch (JSONException e){
                            Toast.makeText(getBaseContext() , getString(R.string.server_error), Toast.LENGTH_LONG).show();
                            Log.e("on my god exception  ::", e.toString());
                            e.printStackTrace();
                        }
                        //set_bar_chart();
                        SetLineChart();
                        //lv.setAdapter(new CustomAdapter(getApplicationContext(),spaceships));
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("hell you Volley ::", error.toString());
                    }
                }
        );
        arrReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(arrReq);

        //Log.d(TAG,"after set adapter .....");

    }

    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("10");
        xVals.add("20");
        xVals.add("30");
        xVals.add("30.5");
        xVals.add("40");

        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(1.3f, 0));
        yVals.add(new Entry(1.2f, 1));
        yVals.add(new Entry(1.5f, 2));
        yVals.add(new Entry(1.3f, 3));
        yVals.add(new Entry(1.9f, 4));

        return yVals;
    }

    private void setData(String label) {
      // ArrayList<String> xVals = setXAxisValues();
      //  ArrayList<Entry> yVals = setYAxisValues();
        LineDataSet set1;
        set1 = new LineDataSet(yVals, label);
        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);
        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(true);
        set1.setValueTextSize(11f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
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
            mChart.highlightValues(null);
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
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex()
                + ", high: " + mChart.getHighestVisibleXIndex());

        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin()
                + ", xmax: " + mChart.getXChartMax()
                + ", ymin: " + mChart.getYChartMin()
                + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}

