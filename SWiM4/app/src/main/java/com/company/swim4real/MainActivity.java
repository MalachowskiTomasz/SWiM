package com.company.swim4real;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button javaMenuButton = findViewById(R.id.javaMenuButton);
        final Intent javaMenuIntent = new Intent(this, JavaMenuActivity.class);
        javaMenuButton.setOnClickListener(v -> startActivity(javaMenuIntent));

        Button xmlMenuButton = findViewById(R.id.xmlMenuButton);
        final Intent xmlMenuIntent = new Intent(this, XMLMenuActivity.class);
        xmlMenuButton.setOnClickListener(v -> startActivity(xmlMenuIntent));

        Button listButton = findViewById(R.id.listButton);
        final Intent listIntent = new Intent(this, ListActivity.class);
        listButton.setOnClickListener(v -> startActivity(listIntent));
    }
}
