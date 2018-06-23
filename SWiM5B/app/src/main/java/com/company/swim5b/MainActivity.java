package com.company.swim5b;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final int INITIAL_VALUE_OF_BALLS = 5;
    public static final int ACCURACY = 70;

    ImageView player;
    ImageView target;
    ConstraintLayout layout;

    SensorManager sensorManager;
    Sensor accelometer;

    int leftTargets = INITIAL_VALUE_OF_BALLS;
    long time;
    long highscore;
    boolean endOfGame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        layout = findViewById(R.id.layout);
        player = findViewById(R.id.userImageView);
        ((TextView) findViewById(R.id.wynikTextView)).setText(String.format("Pozostalo: %d", INITIAL_VALUE_OF_BALLS));

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.commit();
        highscore = preferences.getLong("highscore", Long.MAX_VALUE);
        ((TextView) findViewById(R.id.highScoreTextView)).setText(String.format("HighScore: %s", highscore != Long.MAX_VALUE ? highscore + " s" : "NaN"));
    }

    private void createNewTarget() {
        Random r = new Random();
        target.setX(r.nextInt(layout.getWidth() - player.getWidth()));
        target.setY(r.nextInt(layout.getHeight() - player.getHeight()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        target = findViewById(R.id.targetImageView);
        time = System.currentTimeMillis();
        createNewTarget();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float deltaX = sensorEvent.values[0];
        float deltaY = sensorEvent.values[1];
        updatePlayerPosition(deltaX, deltaY);

        if (target != null) {
            if (Math.abs(player.getX() - target.getX()) <= ACCURACY && Math.abs(player.getY() - target.getY()) <= ACCURACY) {
                leftTargets--;
                if (leftTargets > 0) {
                    onHitTarget();
                } else {
                    endOfGame = true;
                    long result = (System.currentTimeMillis() - time) / 1000;
                    if (result < highscore)
                        onNewHighScore(result);

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Twoj wynik to " + result + " s")
                            .setMessage("Czy chcesz grac dalej?");
                    builder.setPositiveButton("Tak", ((dialogInterface, i) -> recreate()));
                    builder.setNegativeButton("Nie", (((dialogInterface, i) -> onBackPressed())));
                    builder.create().show();

                    leftTargets = 5; //Musi byc, inaczej przerywa program
                }
            } else {
                double distance = Math.sqrt(Math.pow(player.getX() - target.getX(), 2) + Math.pow(player.getY() - target.getY(), 2));
                double scale = (double) 155 / Math.sqrt(Math.pow(layout.getWidth(), 2) + Math.pow(layout.getHeight(), 2));
                updateDistanceViews(distance, scale);
            }
        }
    }

    private void updateDistanceViews(double distance, double scale) {
        ((TextView) findViewById(R.id.distanceTextView)).setText(String.format("Odleglosc: %s", String.format("%.2f", distance * scale)));

        double colorDifference = 100 + ((distance * scale) <= 155 ? distance * scale : 155);
        layout.setBackgroundColor(Color.rgb((int) colorDifference, (int) colorDifference, 255));
    }

    private void updatePlayerPosition(float deltaX, float deltaY) {
        boolean bandX0 = player.getX() <= 0;
        boolean bandY0 = player.getY() <= 0;
        boolean bandX1 = player.getX() + player.getWidth() >= layout.getWidth();
        boolean bandY1 = player.getY() + player.getHeight() >= layout.getHeight();

        if (!endOfGame && !((bandX0 && deltaX > 0) || (bandX1 && deltaX < 0)))
            player.setX(player.getX() - (5 * deltaX));
        if (!endOfGame && !((bandY0 && deltaY < 0) || (bandY1 && deltaY > 0)))
            player.setY(player.getY() + (5 * deltaY));
    }

    private void onHitTarget() {
        createNewTarget();
        MediaPlayer.create(this, R.raw.blip).start();
        Toast.makeText(this, "Trafiony!", Toast.LENGTH_SHORT).show();
        ((TextView) findViewById(R.id.wynikTextView)).setText(String.format("Pozostalo: %s", leftTargets));
    }

    private void onNewHighScore(long result) {
        ((TextView) findViewById(R.id.highScoreTextView)).setText(String.format("HighScore: %s s", result));
//        highscore = result;
        //time = System.currentTimeMillis();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("highscore", result);
        editor.apply();

        Toast.makeText(this, "Pobiles aktualny rekord. Gratulacje!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
