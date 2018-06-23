package com.company.swim3;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by T4riS on 18.03.2018.
 */

public class CustomGridViewAdapter extends BaseAdapter {
    private String[] data;
    private LayoutInflater inflater;
    private int layoutResorce;

    CustomGridViewAdapter(Activity a, String[] data, @LayoutRes int layoutResource) {
        this.data = data;
        this.inflater = a.getLayoutInflater();
        this.layoutResorce = layoutResource;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(layoutResorce, null);

        TextView text = view.findViewById(R.id.opcja);
        text.setText(data[i]);
        View view1 = view;
        view.setOnClickListener(v ->
                Toast.makeText(view1.getContext(), data[i], Toast.LENGTH_SHORT).show());
        return view;
    }
}
