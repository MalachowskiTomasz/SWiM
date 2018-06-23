package com.company.swim3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AppListView extends NightModeActivity {

    String[] opcje = new String[]{"Opcja 1", "Opcja 2", "Opcja 3", "Opcja 4", "Opcja 5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list_view);

        isNightModeActivated();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opcje);
        ListView v = findViewById(R.id.listView);
        v.setAdapter(adapter);
        v.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, opcje[i], Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onNightMode(boolean value) {
        ListView v = findViewById(R.id.listView);
        if (value) {
            v.setBackgroundColor(Color.DKGRAY);
        } else {
            v.setBackgroundColor(Color.WHITE);
        }
    }
}

