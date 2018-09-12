package com.tricktekno.animatedsplash;

/*public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


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

        View v = getSupportFragmentManager().findFragmentById(R.id.map).getView();
        v.setAlpha(0.5f);


    }
}

*/
/*
public class MapsActivity extends FragmentActivity {

    GoogleMap googleMap;
    SharedPreferences sharedPreferences;
    int locationCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
            // Opening the sharedPreferences object
            sharedPreferences = getSharedPreferences("location", 0);
            // Getting number of locations already stored
            locationCount = sharedPreferences.getInt("locationCount", 0);
            // Getting stored zoom level if exists else return 0
            String zoom = sharedPreferences.getString("zoom", "0");
            // If locations are already saved
            if(locationCount!=0){

                String lat = "";
                String lng = "";

                // Iterating through all the locations stored
                for(int i=0;i<locationCount;i++){

                    // Getting the latitude of the i-th location
                    lat = sharedPreferences.getString("lat"+i,"0");

                    // Getting the longitude of the i-th location
                    lng = sharedPreferences.getString("lng"+i,"0");

                    // Drawing marker on the map
                    drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                }

                // Moving CameraPosition to last clicked position
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));

                // Setting the zoom level in the map on last position  is clicked
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
            }
        }

        googleMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                locationCount++;
                // Drawing marker on the map
                drawMarker(point);
                // Opening the editor object to write data to sharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // Storing the latitude for the i-th location
                editor.putString("lat"+ Integer.toString((locationCount-1)), Double.toString(point.latitude));

                // Storing the longitude for the i-th location
                editor.putString("lng"+ Integer.toString((locationCount-1)), Double.toString(point.longitude));

                // Storing the count of locations or marker count
                editor.putInt("locationCount", locationCount);

                // Storing the zoom level to the shared preferences
                editor.putString("zoom", Float.toString(googleMap.getCameraPosition().zoom));

                // Saving the values stored in the shared preferences
                editor.commit();

                Toast.makeText(getBaseContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();

            }
        });

        googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                // Removing the marker and circle from the Google Map
                googleMap.clear();
                // Opening the editor object to delete data from sharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // Clearing the editor
                editor.clear();
                // Committing the changes
                editor.commit();
                // Setting locationCount to zero
                locationCount=0;

            }
        });
    }

    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        googleMap.addMarker(markerOptions);
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
       // return true;
    //}
}*/