package com.example.gpsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    int MY_PERMISSIONS;
    TextView textViewLocation, textViewAddress, textViewDistance;
    List<Address> list;
    Geocoder geocoder;
    double distance = 0;
    Location firstLocation;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewLocation = findViewById(R.id.id_textView_location);
        textViewAddress = findViewById(R.id.id_textView_address);
        textViewDistance = findViewById(R.id.id_textView_distance);
        locationManager = (LocationManager)(getSystemService(Context.LOCATION_SERVICE));
        geocoder = new Geocoder(this, Locale.US);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    counter++;
                    if(counter == 1)
                        firstLocation = location;
                    textViewLocation.setText(location.getLatitude() + " " + location.getLongitude());
                    Log.d("HI", ""+location.distanceTo(firstLocation));
                    distance+=location.distanceTo(firstLocation);
                    firstLocation = location;
                    try {
                        list = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        textViewAddress.setText(list.get(0).getAddressLine(0));
                        textViewDistance.setText(""+distance);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

}
