package com.company.swim3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class AuthorActivity extends NightModeActivity {

    private Boolean nightMode;
    private boolean showImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        isNightModeActivated();

        SharedPreferences sharedPref = getApplication().getSharedPreferences("settings", Context.MODE_PRIVATE);
        showImage(sharedPref.getBoolean("showImage", true));

        ToggleButton button = findViewById(R.id.toggleButton);
        button.setOnClickListener(v -> onNightMode(button.isChecked()));

        Switch swi = findViewById(R.id.hideSwitch);
        swi.setOnClickListener(v -> showImage(swi.isChecked()));

        Button callButton = findViewById(R.id.callButton);
        callButton.setOnClickListener(this::onOpenCLVButtonClick);

    }

    private void showImage(boolean value) {
        showImage = value;
        ImageView img = findViewById(R.id.obrazek);
        Switch swi = findViewById(R.id.hideSwitch);
        if (value) {
            img.setVisibility(View.VISIBLE);
            swi.setChecked(true);
        } else {
            img.setVisibility(View.INVISIBLE);
            swi.setChecked(false);
        }
    }

    public void onOpenCLVButtonClick(View view) {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        startActivity(callIntent);
    }

    @Override
    protected void onNightMode(boolean value) {
        nightMode = value;

        ToggleButton button = findViewById(R.id.toggleButton);
        button.setChecked(nightMode);

        View layout = findViewById(R.id.layout);
        ArrayList<TextView> list = new ArrayList<>(4);
        list.add(findViewById(R.id.textView));
        list.add(findViewById(R.id.textView2));
        list.add(findViewById(R.id.textView3));
        list.add(findViewById(R.id.hideSwitch));

        if (value) {
            layout.setBackgroundColor(Color.DKGRAY);
            for (TextView v : list)
                v.setTextColor(Color.WHITE);
        } else {
            layout.setBackgroundColor(Color.WHITE);
            for (TextView v : list)
                v.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPref = getApplication().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("nightMode", nightMode);
        editor.putBoolean("showImage", showImage);
        editor.commit();
        super.onBackPressed();
    }
}
