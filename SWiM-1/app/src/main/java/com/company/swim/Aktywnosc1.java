package com.company.swim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Aktywnosc1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktywnosc1);

        View okno = findViewById(R.id.linearlayout);
        okno.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onBackPressed();
                return false;
            }
        });
    }
}
