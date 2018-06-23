package com.company.swim42;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.company.swim42.Fragments.Fragment1;
import com.company.swim42.Fragments.Fragment2;
import com.company.swim42.Fragments.RadioGroupFragment;

public class FramesActivity extends AppCompatActivity implements RadioGroupFragment.OnOptionChangedListener {
    private static final String TAG_F1 = "Fragment1";
    private static final String TAG_F2 = "Fragment2";

    Fragment1 fragment1 = new Fragment1();
    Fragment2 fragment2 = new Fragment2();
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_frames_v);
        else
            setContentView(R.layout.activity_frames_h);

        manager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.container, fragment1, TAG_F1);
            transaction.detach(fragment1);
            transaction.add(R.id.container, fragment2, TAG_F2);
            transaction.detach(fragment2);
            transaction.commit();
        } else {
            fragment1 = (Fragment1) manager.findFragmentByTag(TAG_F1);
            fragment2 = (Fragment2) manager.findFragmentByTag(TAG_F2);
        }
    }

    @Override
    public void onOptionChanged(int option) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (option) {
            case 0:
                transaction.detach(fragment2);
                transaction.attach(fragment1);
                break;
            case 1:
                transaction.detach(fragment1);
                transaction.attach(fragment2);
                break;
        }
        transaction.commit();
    }
}
