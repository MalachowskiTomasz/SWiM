package com.company.swim5a;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button lightButton = findViewById(R.id.ASensorLightButton);
        final Intent lightButtonIntent = new Intent(this, ASensor.class);
        lightButtonIntent.putExtra("sensorType", Sensor.TYPE_LIGHT);
        lightButton.setOnClickListener(v -> startActivity(lightButtonIntent));

        Button accButton = findViewById(R.id.ASensorAccelerationButton);
        final Intent accButtonIntent = new Intent(this, ASensor.class);
        accButtonIntent.putExtra("sensorType", Sensor.TYPE_ACCELEROMETER);
        accButton.setOnClickListener(v -> startActivity(accButtonIntent));

        Button gpsButton = findViewById(R.id.GPSButton);
        final Intent gpsButtonIntent = new Intent(this, GPS.class);
        gpsButton.setOnClickListener(v -> startActivity(gpsButtonIntent));
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        boolean enabled = !sm.getSensorList(Sensor.TYPE_LIGHT).isEmpty();
        TextView textView = findViewById(R.id.lightinfo);
        changeStatus(textView, R.string.light_status, enabled);
        findViewById(R.id.ASensorLightButton).setEnabled(enabled);

        enabled = !sm.getSensorList(Sensor.TYPE_ACCELEROMETER).isEmpty();
        textView = findViewById(R.id.accelinfo);
        changeStatus(textView, R.string.accel_status, enabled);
        findViewById(R.id.ASensorAccelerationButton).setEnabled(enabled);

        final LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        textView = findViewById(R.id.gpsinfo);
        changeStatus(textView, R.string.gps_status, enabled);
        findViewById(R.id.GPSButton).setEnabled(enabled);
    }

    private void changeStatus(TextView textView, @StringRes int id, boolean enabled){
        textView.setText(getString(id) + " " + getString(enabled ? R.string.txt_avail : R.string.txt_unavail));
        textView.setTextColor(enabled ? Color.GREEN : Color.RED);
    }
}
