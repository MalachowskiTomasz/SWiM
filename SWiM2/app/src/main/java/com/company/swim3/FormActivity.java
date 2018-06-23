package com.company.swim3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Button b = findViewById(R.id.saveButton);
        b.setOnClickListener(this::saveToFile);
    }

    private void saveToFile(View view) {
        try {
            FileOutputStream fs = new FileOutputStream("users.txt", true);
            fs.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
            EditText name = findViewById(R.id.nameEditText);
            EditText surname = findViewById(R.id.surnameEditText);
            EditText phone = findViewById(R.id.phoneEditText);

            writer.write(name.getText().toString() + " " + surname.getText().toString() + " " + phone.getText().toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
