package com.company.swim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import javax.xml.datatype.Duration;

public class Aktywnosc2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktywnosc2);
    }

    public void powiadomienie(View view) {
        Toast.makeText(getApplicationContext(), "Zapisano (taki zarcik, po co nam twoje dane?)", Toast.LENGTH_SHORT).show();
    }

    public void wroc(View view) {
        onBackPressed();
    }
}
