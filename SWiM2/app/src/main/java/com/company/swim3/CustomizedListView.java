package com.company.swim3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CustomizedListView extends NightModeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customized_list_view);

        boolean nightmode = isNightModeActivated();

        ArrayList<MovieData> movieData = new ArrayList<>();
        movieData.add(new MovieData("W ciemnosci", "Cos tam", R.drawable.ic_launcher_foreground));
        movieData.add(new MovieData("Zagubieni", "Cos tam", R.drawable.ic_launcher_foreground));
        movieData.add(new MovieData("Pieszo przez świat", "Cos tam", R.drawable.ic_launcher_foreground));
        movieData.add(new MovieData("Czarny pies", "Cos tam", R.drawable.ic_launcher_foreground));
        movieData.add(new MovieData("Alo", "Cos tam", R.drawable.ic_launcher_foreground));
        movieData.add(new MovieData("Do śmierci", "Cos tam", R.drawable.ic_launcher_foreground));
        movieData.add(new MovieData("Meja", "Cos tam", R.drawable.ic_launcher_foreground));
        movieData.add(new MovieData("Pisze do ciebie", "Cos tam", R.drawable.ic_launcher_foreground));


        ListAdapter adapter;
        if (nightmode)
            adapter = new CustomListViewAdapter(this, movieData, R.layout.listview_item_dark);
        else
            adapter = new CustomListViewAdapter(this, movieData, R.layout.listview_item);
        ListView view = findViewById(R.id.customized);
        view.setAdapter(adapter);
    }

    @Override
    protected void onNightMode(boolean value) {
        ListView view = findViewById(R.id.customized);

        if (value) {
            view.setBackgroundColor(Color.DKGRAY);
        } else {
            view.setBackgroundColor(Color.WHITE);
        }
    }
}
