package com.company.swim3;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T4riS on 18.03.2018.
 */

public class CustomListViewAdapter extends BaseAdapter {
    private ArrayList<MovieData> data;
    private LayoutInflater inflater;
    private int layoutResorce;

    CustomListViewAdapter(Activity a, ArrayList<MovieData> data, @LayoutRes int layoutResource) {
        this.data = data;
        this.inflater = a.getLayoutInflater();
        this.layoutResorce = layoutResource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(layoutResorce, null);
        TextView title = view.findViewById(R.id.movieTitleTextView);
        TextView description = view.findViewById(R.id.movieDescTextView);
        ImageView image = view.findViewById(R.id.movieImageView);

        MovieData movie = data.get(i);
        title.setText(movie.getTitle());
        description.setText(movie.getDescription());
        image.setImageResource(movie.getImagePath());

        return view;
    }
}
