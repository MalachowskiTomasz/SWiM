package com.company.swim42.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.company.swim42.R;

public class RadioGroupFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    OnOptionChangedListener changedListener;

    public RadioGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changedListener = (OnOptionChangedListener) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RadioGroup radioGroup = getActivity().findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_radio_group, container, false);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.f1button:
                changedListener.onOptionChanged(0);
                break;
            case R.id.f2button:
                changedListener.onOptionChanged(1);
                break;
        }
    }

    public interface OnOptionChangedListener {
        void onOptionChanged(int option);
    }
}
