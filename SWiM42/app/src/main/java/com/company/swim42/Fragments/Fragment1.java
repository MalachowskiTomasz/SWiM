package com.company.swim42.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.company.swim42.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {
    private final List<String> pies = new ArrayList<>();
    private boolean loaded = false;

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        ListView listView = view.findViewById(R.id.doglist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, pies);
        listView.setAdapter(adapter);

        if (!loaded) {
            loadFromFile(view, adapter);
            loaded = true;
        }

        EditText nameEditText = view.findViewById(R.id.nameEditText);
        EditText ageEditText = view.findViewById(R.id.ageEditText);

        Button button = view.findViewById(R.id.addDogButton);
        button.setOnClickListener(v -> {
            String dog = String.format("Imie: %s, Wiek: %s", nameEditText.getText().toString(), ageEditText.getText().toString());
            adapter.add(dog);
            if (adapter.getCount() > 1)
                saveToFile(view, "\t" + dog);
            else
                saveToFile(view, dog);
        });

        return view;
    }

    private void loadFromFile(View view, ArrayAdapter<String> listView) {
        File file = new File(view.getContext().getFilesDir().toString() + "\\dogs.txt");
        if (!file.exists()) return;
        else {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(view.getContext().openFileInput("dogs.txt")));
                for (String dog : bufferedReader.readLine().split("\t"))
                    listView.add(dog);
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void saveToFile(View view, String string) {
        File f = new File(view.getContext().getFilesDir().toString() + "\\dogs.txt");
        try {
            if (!f.exists())
                f.createNewFile();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(view.getContext().openFileOutput("dogs.txt", Context.MODE_APPEND)));
            bw.write(string);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
