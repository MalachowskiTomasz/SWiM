package com.company.swim3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListAdapter;

public class AppGridView extends NightModeActivity {
    String[] opcje = new String[]{"Opcja 1", "Opcja 2", "Opcja 3", "Opcja 4", "Opcja 5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_grid_view);

        boolean nightMode = isNightModeActivated();

        ListAdapter adapter;
        if (nightMode)
            adapter = new CustomGridViewAdapter(this, opcje, R.layout.griddview_item_dark);
        else
            adapter = new CustomGridViewAdapter(this, opcje, R.layout.gridview_item);
        GridView v = findViewById(R.id.gridview);
        v.setAdapter(adapter);
    }

    @Override
    protected void onNightMode(boolean value) {
        GridView v = findViewById(R.id.gridview);
        if (value) {
            v.setBackgroundColor(Color.DKGRAY);
        } else {
            v.setBackgroundColor(Color.WHITE);
        }
    }
}
