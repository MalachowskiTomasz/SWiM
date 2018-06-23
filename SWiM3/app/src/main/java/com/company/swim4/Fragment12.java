package com.company.swim4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment12 extends Fragment {
    public Fragment12() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment12, container, false);
        Button b = view.findViewById(R.id.actionBarButton);

        b.setOnClickListener(v -> startActivity(new Intent(this.getContext(), TabActivity.class)));

        return view;
    }
}
