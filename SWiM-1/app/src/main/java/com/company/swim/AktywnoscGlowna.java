package com.company.swim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AktywnoscGlowna extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktywnoscglowna);
    }

    public void otworz1(View view) {
        final Intent open = new Intent(this, Aktywnosc1.class);
        startActivity(open);
    }

    public void otworz2(View view) {
        final Intent open = new Intent(this, Aktywnosc2.class);
        startActivity(open);
    }

    public void otworz3(View view) {
        final Intent open = new Intent(this, Aktywnosc3.class);
        startActivity(open);
    }
}
