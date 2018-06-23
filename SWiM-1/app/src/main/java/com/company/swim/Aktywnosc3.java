package com.company.swim;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Aktywnosc3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktywnosc3);
    }

    public void testPowiadomienia(View view) {
        Toast.makeText(getApplicationContext(), "Test powiadomienia", Toast.LENGTH_SHORT).show();
    }

    public void wroc(View view) {
        onBackPressed();
    }

    public void zmienTlo(View view) {
        ToggleButton button = (ToggleButton) view;
        ConstraintLayout v = findViewById(R.id.constraintlayout);
        RadioGroup r = findViewById(R.id.radioGroup);
        TextView t = findViewById(R.id.textView6);

        int color = Color.BLACK;
        if(button.isChecked()) {
            color = Color.WHITE;
            t.setTextColor(Color.WHITE);
            v.setBackgroundColor(Color.BLACK);
        } else{
            t.setTextColor(Color.BLACK);
            v.setBackgroundColor(Color.WHITE);
        }
        for (int i = 0; i < r.getChildCount(); i++) {
            ((RadioButton)r.getChildAt(i)).setTextColor(color);
        }
    }
}
