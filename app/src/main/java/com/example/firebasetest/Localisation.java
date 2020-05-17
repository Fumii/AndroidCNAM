package com.example.firebasetest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Localisation extends AppCompatActivity {
    LocationManager locationManager = null;
    LocationListener locationListener = null;
    Context mContext;
    TextView latitude;
    TextView longitude;

    protected Localisation(Context context, Activity act) {
        this.mContext = context;
        Log.d("ici","lboclk");
        latitude = act.findViewById(R.id.currentLatitude);
        Log.d("find",latitude.toString());
        longitude = act.findViewById(R.id.currentLongitude);
        initGPS();
    }

    public LocationListener getLocationListener(){
        return this.locationListener;
    }

    public LocationManager getLocationManager(){
        return this.locationManager;
    }

    public void initGPS() {

        this.locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        Log.d("test", "init");
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.d("change", "location: ");
                latitude.setText(String.valueOf(location.getLatitude()));
                longitude.setText(String.valueOf(location.getLongitude()));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("test", "Scha");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("test", "Pena");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("test", "Pdis");
            }
        };
/*
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; }
*/
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("connect", "initGPS: ");
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
        Log.d("test","mana");
    }
}
