package com.company.swim3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends NightModeActivity {

    private String[] spinnerValues = new String[]{"ListView", "Customized ListView", "GridView"};

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            isNightModeActivated();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isNightModeActivated();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValues);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        final Intent aboutAuthorActivity = new Intent(this, AuthorActivity.class);
        Button authorButton = findViewById(R.id.authorButton);
        authorButton.setOnClickListener(x -> startActivityForResult(aboutAuthorActivity, 1));

        Button goButton = findViewById(R.id.goButton);
        goButton.setOnClickListener(this::onClickGoButton);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ImageView view = findViewById(R.id.imageView);
                view.setAlpha((float) i / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final Intent formOpen = new Intent(this, FormActivity.class);
        Button formButton = findViewById(R.id.formButton);
        formButton.setOnClickListener(v -> startActivity(formOpen));

        final Intent savedOpen = new Intent(this, SavedActivity.class);
        Button button = findViewById(R.id.savedButton);
        button.setOnClickListener(v -> startActivity(savedOpen));
    }

    private void onClickGoButton(View view) {
        Spinner spinner = findViewById(R.id.spinner);
        Integer position = spinner.getSelectedItemPosition();

        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(this, AppListView.class);
                break;
            case 1:
                intent = new Intent(this, CustomizedListView.class);
                break;
            default:
                intent = new Intent(this, AppGridView.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onNightMode(boolean value) {
        View layout = findViewById(R.id.layout0);
        TextView opacity = findViewById(R.id.opacityTextView);
        if (value) {
            layout.setBackgroundColor(Color.DKGRAY);
            opacity.setTextColor(Color.WHITE);
        } else {
            layout.setBackgroundColor(Color.WHITE);
            opacity.setTextColor(Color.DKGRAY);
        }
    }
}

