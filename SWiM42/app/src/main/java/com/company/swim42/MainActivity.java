package com.company.swim42;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, FramesActivity.class);
        Button goToFrames = findViewById(R.id.goToFramesButton);
        goToFrames.setOnClickListener(v -> startActivity(intent));

        final Intent intent1 = new Intent(this, ActionBarActivity.class);
        Button goToActionBar = findViewById(R.id.goToActionBarButton);
        goToActionBar.setOnClickListener(v -> startActivity(intent1));
    }
}
