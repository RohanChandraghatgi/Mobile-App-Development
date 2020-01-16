package com.example.gpsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    int MY_PERMISSIONS;
    TextView textViewLocation;
    Boolean permissionGrant = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewLocation=findViewById(R.id.id_textView_location);
        locationManager = (LocationManager)(getSystemService(Context.LOCATION_SERVICE));

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(permissionGrant)
                        textViewLocation.setText(location.getLatitude() + " " + location.getLongitude());
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS);
//            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
    }@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
       if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
           permissionGrant = true;

       }
       else
           permissionGrant = false;
    }
}
