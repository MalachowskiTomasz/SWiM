package com.company.swim3;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by T4riS on 18.03.2018.
 */

public abstract class NightModeActivity extends AppCompatActivity {
    protected boolean isNightModeActivated() {
        SharedPreferences sharedPref = getApplication().getSharedPreferences("settings", Context.MODE_PRIVATE);

        Boolean nightmode = sharedPref.getBoolean("nightMode", false);
        onNightMode(nightmode);
        return nightmode;
    }

    protected abstract void onNightMode(boolean value);
}
