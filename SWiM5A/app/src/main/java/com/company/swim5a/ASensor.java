package com.company.swim5a;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ASensor extends AppCompatActivity implements SensorEventListener{

    private SensorManager sm = null;
    private int sensorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asensor);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorType = getIntent().getIntExtra("sensorType", Sensor.TYPE_LIGHT);
        if(sensorType == Sensor.TYPE_LIGHT)
            setTitle(R.string.light_status);
        else if (sensorType == Sensor.TYPE_ACCELEROMETER)
            setTitle(R.string.accel_status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Sensor sens = sm.getSensorList(sensorType).get(0);
        sm.registerListener(this, sens, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        TextView textView = findViewById(R.id.txt_data);
        StringBuilder stringBuilder = new StringBuilder();
        if (sensorType == Sensor.TYPE_LIGHT) {
            stringBuilder.append("Ambient light level: ");
            stringBuilder.append(sensorEvent.values[0]);
            stringBuilder.append(" lux");
        } else if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            stringBuilder.append("X acceleration: ");
            stringBuilder.append(String.format("%7.4f", sensorEvent.values[0]));
            stringBuilder.append(" m/s\u00B2\nY acceleration: ");
            stringBuilder.append(String.format("%7.4f", sensorEvent.values[1]));
            stringBuilder.append(" m/s\u00B2\nZ acceleration: ");
            stringBuilder.append(String.format("%7.4f", sensorEvent.values[2]));
            stringBuilder.append(" m/s\u00B2");
        }
        textView.setText(stringBuilder);

        textView = findViewById(R.id.txt_status);
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("\nAccuracy: ");
        switch (sensorEvent.accuracy) {
            case 3:
                stringBuilder1.append("High");
                break;
            case 2:
                stringBuilder1.append("Medium");
                break;
            default:
                stringBuilder1.append("Low");
                break;
        }
        textView.setText(stringBuilder1);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
