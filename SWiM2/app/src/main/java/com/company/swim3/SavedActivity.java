package com.company.swim3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SavedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String output = "";
            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }
            TextView v = findViewById(R.id.showSaved);
            v.setText(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }


    }
}
