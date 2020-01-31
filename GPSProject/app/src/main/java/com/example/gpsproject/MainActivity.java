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
import android.view.View;
import android.widget.Button;
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
    Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewLocation = findViewById(R.id.id_textView_location);
        textViewAddress = findViewById(R.id.id_textView_address);
        textViewDistance = findViewById(R.id.id_textView_distance);
        reset = findViewById(R.id.id_button_reset);
        locationManager = (LocationManager)(getSystemService(Context.LOCATION_SERVICE));
        geocoder = new Geocoder(this, Locale.US);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance = 0;
                textViewDistance.setText("0.0 m");
            }
        });
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    counter++;
                    textViewLocation.setText(location.getLatitude() + " " + location.getLongitude());
                    if(counter == 1) {
                        firstLocation = location;
                    }
                    else
                        {
                        distance += (double)location.distanceTo(firstLocation);
                        firstLocation = location;
                        textViewDistance.setText(distance + " meters");
                        Log.d("DISTANCE", distance + "");
                        try {
                            list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            textViewAddress.setText(list.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1000, 2,locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 2, locationListener);

    }@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }
}
