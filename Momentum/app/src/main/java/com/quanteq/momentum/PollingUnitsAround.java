package com.quanteq.momentum;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.location.Location;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.quanteq.momentum.network.Communicator;


public class PollingUnitsAround extends ActionBarActivity implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener{

    ListView lv;
    TextView headerText;
    ListAdapter la = null;
    ImageView loadingImage;
    TextView loadingText;
    ProgressBar loadingBar;
    ImageView quanteqLogo;
    static boolean isTextClickable;
    boolean isSafeCancel;

    GoogleApiClient mGoogleApiClient;
    static Location mLastLocation;
    static Location mActiveLocation; //whether last known or current location, this represents the active location in use on the screen
    LocationRequest mLocationRequest;
    static AsyncTask getPollingUnitTasks;
    String[] colours = {"#559955", "#555599", "#995555", "#995599"};
    boolean headerImageSet = false;
    static List<PollingUnit> currentPus = new ArrayList<PollingUnit>(); //represents the pus we currently have at any point in time

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling_units_around);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        List<PollingUnit> pus = new ArrayList<PollingUnit>();
        PollingUnit pu = new PollingUnit();
        PollingUnit pu1 = new PollingUnit();
        PollingUnit pu2 = new PollingUnit();
        PollingUnit pu3 = new PollingUnit();
        PollingUnit pu4 = new PollingUnit();
        PollingUnit pu5 = new PollingUnit();
        PollingUnit pu6 = new PollingUnit();
        PollingUnit pu7 = new PollingUnit();
        PollingUnit pu8 = new PollingUnit();
        PollingUnit pu9 = new PollingUnit();
        PollingUnit pu10 = new PollingUnit();
        PollingUnit pu11 = new PollingUnit();
        PollingUnit puImage = new PollingUnit();
        Location dummyLoc = new Location("");
        dummyLoc.setLatitude(0.0);
        dummyLoc.setLongitude(0.0);
        pu.setLocationId("FCT0101000");
        pu.setDesc("located near the National Hospital");
        pu.setDistance(0.0f);
        pu.setLocation(dummyLoc);

        pu1.setLocationId("FCT0101001");
        pu1.setDesc("located near the National Hospital");
        pu1.setDistance(0.0f);
        pu1.setLocation(dummyLoc);

        pu2.setLocationId("FCT0101002");
        pu2.setDesc("located near the National Hospital");
        pu2.setDistance(0.0f);
        pu2.setLocation(dummyLoc);

        pu3.setLocationId("FCT0101003");
        pu3.setDesc("located near the National Hospital");
        pu3.setDistance(0.0f);
        pu3.setLocation(dummyLoc);

        pu4.setLocationId("FCT0101004");
        pu4.setDesc("located near the National Hospital");
        pu4.setDistance(0.0f);
        pu4.setLocation(dummyLoc);

        pu5.setLocationId("FCT0101005");
        pu5.setDesc("located near the National Hospital");
        pu5.setDistance(0.0f);
        pu5.setLocation(dummyLoc);

        pu6.setLocationId("FCT0101006");
        pu6.setDesc("located near the National Hospital");
        pu6.setDistance(0.0f);
        pu6.setLocation(dummyLoc);

        pu7.setLocationId("FCT0101007");
        pu7.setDesc("located near the National Hospital");
        pu7.setDistance(0.0f);
        pu7.setLocation(dummyLoc);

        pu8.setLocationId("FCT0101008");
        pu8.setDesc("located near the National Hospital");
        pu8.setDistance(0.0f);
        pu8.setLocation(dummyLoc);

        pu9.setLocationId("FCT0101009");
        pu9.setDesc("located near the National Hospital");
        pu9.setDistance(0.0f);
        pu9.setLocation(dummyLoc);

        pu10.setLocationId("FCT0101010");
        pu10.setDesc("located near the National Hospital");
        pu10.setDistance(0.0f);
        pu10.setLocation(dummyLoc);

        pu11.setLocationId("FCT0101011");
        pu11.setDesc("located near the National Hospital");
        pu11.setDistance(0.0f);
        pu11.setLocation(dummyLoc);

        puImage.setLocationId("image");
        puImage.setDesc("located near the National Hospital");
        puImage.setDistance(0.0f);
        puImage.setLocation(dummyLoc);
        pus.add(puImage);
        pus.add(pu);
        pus.add(pu1);
        pus.add(pu2);
        pus.add(pu3);
        pus.add(pu4);
        pus.add(pu5);
        pus.add(pu6);
        pus.add(pu7);
        pus.add(pu8);
        pus.add(pu9);
        pus.add(pu10);
        pus.add(pu11);
        mActiveLocation = dummyLoc;
        currentPus = pus;
        getSupportActionBar().hide();
        lv = (ListView) findViewById(R.id.pua);
        headerText = (TextView) findViewById(R.id.headerText);
        loadingImage = (ImageView) findViewById(R.id.loadingImage);
        loadingText = (TextView) findViewById(R.id.loadingText);
        loadingBar = (ProgressBar) findViewById(R.id.progressBar);
        quanteqLogo = (ImageView) findViewById(R.id.quanteqLogo);


        lv.setVisibility(View.GONE);
        headerText.setVisibility(View.GONE);

        //la = new ListAdapter(PollingUnitsAround.this, R.layout.pu_item, pus);
        //lv.setAdapter(la);


        checkPlayServices();
        buildGoogleApiClient();
    }

    public void initLocationRequest(){
        //loadingBar.setIndeterminate(true);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onStart(){
        super.onStart();
         mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_polling_units_around, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_map) {
            //lets be sure we have polling units to show on the map
            if(currentPus != null){
                Intent mapActivityIntent = new Intent(this, Map_Polling_Units.class);
                startActivity(mapActivityIntent);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private class ListAdapter extends ArrayAdapter<PollingUnit> {

        private List<PollingUnit> pus;
        private int colourPositionCounter = 0;

        public ListAdapter(Context context, int textViewResourceId, List<PollingUnit> pus) {
            super(context, textViewResourceId, pus);
            this.pus = pus;
            computeDistances(this.pus);
            Collections.sort(this.pus, PollingUnit.PollingUnitComparator);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PollingUnit pu = pus.get(position);
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.pu_item, null);


            }

            TextView puId = (TextView) v.findViewById(R.id.puid);
            TextView puDetails = (TextView) v.findViewById(R.id.pudetails);
            TextView puDistance = (TextView) v.findViewById(R.id.distance);


            if(colourPositionCounter >= colours.length){
                colourPositionCounter = 0;
            }
            puId.setTextColor(Color.parseColor(colours[colourPositionCounter]));



            puId.setText(pu.getLocationId());
            puDetails.setText(pu.getDesc());
            puDistance.setText(pu.getDistance().toString()+"km");
            colourPositionCounter++;









            //v.setBackgroundColor(Color.WHITE);

            return v;
        }

        private void computeDistances(List<PollingUnit> pus){
            for(PollingUnit pu : pus){
                Float distance = round(mActiveLocation.distanceTo(pu.getLocation())/1000, 1);
                pu.setDistance(distance);
            }
        }

        public float round(float value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.floatValue();
        }

    }

    public void getPollingUnitsForLastLocation(){
        Log.e("PULL", "getting polling units for last location");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        mActiveLocation = mLastLocation;
        if (mLastLocation != null) {
            Log.e("PULL", "last location found");
            String lat = String.valueOf(mLastLocation.getLatitude());
            String lng = String.valueOf(mLastLocation.getLongitude());
            Log.i("USER LAST LAT", lat);
            Log.i("USER LAST LONG", lng);
            if(isNetworkAvailable()) {
                Log.e("PULL", "network is available");
                getPollingUnitTasks = new GetPollingUnitsTask(PollingUnitsAround.this).execute(lat, lng);
                initLocationRequest();
            } else{
                Log.e("PULL", "network not available");
                showNotification("Network unavailable. Tap to retry.", true, View.INVISIBLE);
            }

        } else{
            //no last location, get a fresh one
            Log.e("PULL", "getting a fresh location");
            //restart listening for location updates
            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
                Log.d("LOC UPDATES", "restarting listening");
            }
            //initLocationRequest();
        }

    }

    public void showNotification(String message, boolean clickable,
                                 int isBarVisible){
        isTextClickable = clickable;
        loadingBar.setVisibility(isBarVisible);
        loadingText.setText(message);
        loadingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTextClickable) {
                    isTextClickable = false;
                    loadingBar.setVisibility(View.VISIBLE);
                    loadingText.setText(R.string.loading);
                    getPollingUnitsForLastLocation();
                }
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.e("OC", "api client connected");
        getPollingUnitsForLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        String lat = String.valueOf(location.getLatitude());
        String lng = String.valueOf(location.getLongitude());
        Log.d("Location Update", lat +
                " " + lng + " acc " + location.getAccuracy());
        //let us make sure we have at least an accuracy of 100m
        if(location.getAccuracy() <= 100.0){
            //stop listening for location updates
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
                Log.d("LOC UPDATES", "stopped listening");
            }
            mActiveLocation = location;
            //update our list view
            //stop any running tasks
            if(getPollingUnitTasks != null) {
                isSafeCancel = true;
                getPollingUnitTasks.cancel(true);
            }
            getPollingUnitTasks = new GetPollingUnitsTask(PollingUnitsAround.this).execute(lat, lng);

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected class GetPollingUnitsTask extends AsyncTask<String, Void, List<PollingUnit>> {
        private String specifiedLocation;

        public GetPollingUnitsTask(Context context) {
            super();
        }

        @Override
        protected List<PollingUnit> doInBackground(String... params) {
            String lat = params[0];
            String lng = params[1];
            try {
                return new Communicator().getPollingUnitsAround(lat, lng);
            } catch(Exception e){
                this.cancel(true);
                return new ArrayList<PollingUnit>();
            }
        }

        @Override
        protected void onCancelled(){
            if(isSafeCancel){
                isSafeCancel = false;
                showNotification("Found some! Hold on a bit...", false, View.VISIBLE);
            } else{
                showNotification("Something went wrong. Tap to retry.", true, View.INVISIBLE);
            }

        }

        @Override
        protected void onPostExecute(List<PollingUnit> pus) {
            //setup the locations spinner
            ListAdapter puAdapter = new ListAdapter(
                    PollingUnitsAround.this, R.layout.pu_item, pus);
            lv.setAdapter(puAdapter);
            currentPus = pus;
            //loadingBar.setIndeterminate(false);
            loadingBar.setVisibility(View.GONE);
            loadingImage.setVisibility(View.GONE);
            loadingText.setVisibility(View.GONE);
            quanteqLogo.setVisibility(View.GONE);
            getSupportActionBar().show();
            headerText.setVisibility(View.VISIBLE);
            headerText.setText(R.string.pua_heading); //to clear all previous setText() calls
            if(pus.size() == 1){
                headerText.setText(pus.size() + " " + headerText.getText().toString().replace("s", ""));
            } else{
                headerText.setText(pus.size() + " " + headerText.getText());
            }
            lv.setVisibility(View.VISIBLE);
        }
    }


    protected synchronized void buildGoogleApiClient() {
        Log.e("GAP", "building google api client");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private boolean checkPlayServices() {
        Log.e("CPS", "checking play services");
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("Viewer", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
