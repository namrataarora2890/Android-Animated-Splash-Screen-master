package com.tricktekno.animatedsplash;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPolygon;
import com.tricktekno.animatedsplash.model.MyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BigClusteringDemoActivity extends BaseDemoActivity {
    private static final String LOG_TAG ="ClusterMap" ;
    private ClusterManager<MyItem> mClusterManager;
    private GoogleMap mMap;
    KmlLayer kmlLayer;

    Polygon clickedPolygon;
    LatLng clickedpoint;

    @Override
    protected void startDemo() {
        mMap = getMap();
        new AsyncCaller().execute();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.7041,77.1025), 5));
        mClusterManager = new ClusterManager<MyItem>(this, mMap);
        CustomClusterRenderer customRenderer = new CustomClusterRenderer(this, mMap,mClusterManager);

        mClusterManager.setRenderer(customRenderer);
        customRenderer.setMarkersToCluster(false);

        //mClusterManager.setAnimation(false);
        mMap.setOnCameraIdleListener(mClusterManager);

        /*mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                mMap.addMarker(new MarkerOptions().position(point).title("Custom location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                clickedpoint=point;
            }
        });*/

        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                //Toast.makeText(context,position.latitude+" : "+position.longitude,Toast.LENGTH_SHORT).show();

                mMap.addMarker(new MarkerOptions().position(point).title("Custom location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                clickedpoint=point;
            }
        });*/

        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                clickedPolygon=polygon;
                // Flip the red, green and blue components of the polygon's stroke color.
                polygon.setStrokeColor(polygon.getStrokeColor() ^ 0x00ffffff);
                Toast.makeText(BigClusteringDemoActivity.this,
                        "Polygon clicked: " ,
                        Toast.LENGTH_SHORT).show();

                Log.d("cool ","pollyyy "+polygon.getPoints());


            }
        });

            /*try {
                retrieveAndAddCities();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Cannot retrive cities", e);
                return;
            }*/

        //new AsyncCaller().execute();

       // new DownloadKmlFile(getString(R.string.kml_url)).execute();

    }




    protected void retrieveAndAddLocations() throws IOException {
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

            double lat = Float.parseFloat(jsonObj.getString("latitude"));
            double lng =  Float.parseFloat(jsonObj.getString("longitude"));
            MyItem offsetItem = new MyItem(lat, lng);

           // boolean contains = PolyUtil.containsLocation(new LatLng(lat, lng), clickedPolygon.getPoints(), false);
            //if (contains)
            mClusterManager.addItem(offsetItem);


        }
    }



    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pdLoading = new ProgressDialog(BigClusteringDemoActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

            try {
                retrieveAndAddLocations();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Cannot retrive cell tower locations", e);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread

            pdLoading.dismiss();
        }

    }


    /***  Another Class ********/
    private class DownloadKmlFile extends AsyncTask<String, Void, byte[]> {
        private final String mUrl;

        public DownloadKmlFile(String url) {
            mUrl = url;
        }

        protected byte[] doInBackground(String... params) {

            try {
               // KmlLayer
                kmlLayer = new KmlLayer(mMap, R.raw.campus, getApplicationContext());
               // kmlLayer.addLayerToMap();
              //  moveCameraToKml(kmlLayer);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(byte[] byteArr) {
            try {
               // KmlLayer kmlLayer = new KmlLayer(mMap, new ByteArrayInputStream(byteArr),
                       // getApplicationContext());
                kmlLayer.addLayerToMap();
               /* kmlLayer.setOnFeatureClickListener(new KmlLayer.OnFeatureClickListener() {
                    @Override
                    public void onFeatureClick(Feature feature) {

                        //List<LatLng> coordinates = ((GeoJsonLineString) feature.getGeometry()).getCoordinates();

                        Toast.makeText(BigClusteringDemoActivity.this,
                                "Feature clicked: " + feature.getId() ,
                                Toast.LENGTH_SHORT).show();
                    }
                });*/
                //moveCameraToKml(kmlLayer);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    private void moveCameraToKml(KmlLayer kmlLayer) {
        //Retrieve the first container in the KML layer
        KmlContainer container = kmlLayer.getContainers().iterator().next();
        //Retrieve a nested container within the first container
        container = container.getContainers().iterator().next();
        //Retrieve the first placemark in the nested container
        KmlPlacemark placemark = container.getPlacemarks().iterator().next();
        //Retrieve a polygon object in a placemark
        KmlPolygon polygon = (KmlPolygon) placemark.getGeometry();
        //Create LatLngBounds of the outer coordinates of the polygon
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polygon.getOuterBoundaryCoordinates()) {
            builder.include(latLng);
        }

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), width, height, 1));
    }



    public class CustomClusterRenderer extends DefaultClusterRenderer<MyItem> {

        private final Context mContext;
        private boolean shouldCluster = true;
        private static final int MIN_CLUSTER_SIZE = 1;


        public CustomClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);

            mContext = context;

        }

        public void setMarkersToCluster(boolean toCluster)
        {
            this.shouldCluster = toCluster;
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item,
                                                   MarkerOptions markerOptions) {

           // Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
            //BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            //markerOptions.icon(descriptor);//.anchor(0.5f, 0.5f);
            //final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.
                    //.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            //****markerOptions.icon(BitmapDescriptorFactory.
                   //*** fromResource(R.drawable.ic_reddot)).anchor(0.5f, 0.5f);
            //markerOptions.zIndex(0).flat(true);

            //final Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
           // markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));


            Bitmap markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_reddot);
            markerBitmap = this.scaleBitmap(markerBitmap, 10, 10);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(markerBitmap));


        }
        public  Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
            Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

            float scaleX = newWidth / (float) bitmap.getWidth();
            float scaleY = newHeight / (float) bitmap.getHeight();
            float pivotX = 0;
            float pivotY = 0;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            Paint  paint = new Paint(Paint.FILTER_BITMAP_FLAG);
            //Paint  paint = new Paint();
            paint.setColor(Color.GREEN);
            canvas.drawBitmap(bitmap, 0, 0, paint);

            return scaledBitmap;
        }
        @Override
        protected boolean shouldRenderAsCluster(Cluster<MyItem> cluster)
        {
            if (shouldCluster)
            {
                return cluster.getSize() > MIN_CLUSTER_SIZE;
            }

            else
            {
                return shouldCluster;
            }
        }
    }


}





