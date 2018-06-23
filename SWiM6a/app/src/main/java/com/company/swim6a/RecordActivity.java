package com.company.swim6a;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecordActivity extends AppCompatActivity {

    public static ProgressBar volumeProgressBar;
    public static SeekBar thresholdSeekBar;

    Boolean onAir = false;
    Button startPauseButton;
    AudioRecorder recorder;
    private int thresholdValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        recorder = new AudioRecorder();

        Button backToListButton = findViewById(R.id.backToListButton);
        backToListButton.setOnClickListener(v -> onBackPressed());

        Button removeButton = findViewById(R.id.removeRecordingButton);
        removeButton.setOnClickListener(view -> {
            findViewById(R.id.saveButton).setEnabled(false);
            removeButton.setEnabled(false);
            recorder.clear();
            Snackbar.make(view, "Usuwam nagranie", Snackbar.LENGTH_SHORT).show();
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> {
            Date time = Calendar.getInstance().getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(time);

            String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
            String author = ((EditText) findViewById(R.id.authorEditText)).getText().toString();
            String description = ((EditText) findViewById(R.id.descriptionEditText)).getText().toString();


            recorder.saveToFile(String.format("%s[]%s[]%s[]%s", date, title, author, description));
            findViewById(R.id.saveButton).setEnabled(false);
            findViewById(R.id.removeRecordingButton).setEnabled(false);
            Snackbar.make(view, "Zapisano nagranie", Snackbar.LENGTH_SHORT).show();
        });

        startPauseButton = findViewById(R.id.startstopButton);
        startPauseButton.setOnClickListener(this::onStartButtonClick);

        volumeProgressBar = findViewById(R.id.volumeProgressBar);
        thresholdSeekBar = findViewById(R.id.thresholdSeekBar);

        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);
        thresholdValue = preferences.getInt("thresholdValue", 0);
        thresholdSeekBar.setProgress(thresholdValue);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
    }

    @Override
    public void onBackPressed() {
        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("thresholdValue", thresholdSeekBar.getProgress());
        editor.apply();

        super.onBackPressed();
    }

    private void onStartButtonClick(View view) {
        onAir = !onAir;
        if (onAir) {
            findViewById(R.id.saveButton).setEnabled(false);
            findViewById(R.id.removeRecordingButton).setEnabled(false);
            findViewById(R.id.thresholdSeekBar).setEnabled(false);
            recorder.start();
            startPauseButton.setText("Stop");
        } else {
            findViewById(R.id.saveButton).setEnabled(true);
            findViewById(R.id.removeRecordingButton).setEnabled(true);
            findViewById(R.id.thresholdSeekBar).setEnabled(true);
            recorder.pause();
            startPauseButton.setText("Start");
        }
    }
}
