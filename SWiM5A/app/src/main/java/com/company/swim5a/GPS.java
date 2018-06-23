package com.company.swim5a;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GPS extends AppCompatActivity implements LocationListener{

    private LocationManager locationManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        TextView txt_data = findViewById(R.id.txt_status);
        txt_data.setText("Czekam na dane GPS...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10 , this);
        else
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView txt_data = findViewById(R.id.txt_data);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Altitude: ");
        stringBuilder.append(location.getAltitude());
        stringBuilder.append(" m\nBearing: ");
        stringBuilder.append(location.getBearing());
        stringBuilder.append("\u00B0\nLatitude: ");
        stringBuilder.append(location.getLatitude());
        stringBuilder.append("\nLongtitude: ");
        stringBuilder.append(location.getLongitude());
        stringBuilder.append("\nSpeed: ");
        stringBuilder.append(location.getSpeed());
        stringBuilder.append(" m/s");
        txt_data.setText(stringBuilder);

        TextView txt_status = findViewById(R.id.txt_status);
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Accuracy: ");
        stringBuilder1.append(location.getAccuracy());
        stringBuilder1.append(" m");
        txt_status.setText(stringBuilder1);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        if(LocationManager.GPS_PROVIDER.contentEquals(s))
            finish();
    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
