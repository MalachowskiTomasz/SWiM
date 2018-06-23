package com.company.swim4real;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class XMLMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        registerForContextMenu(findViewById(R.id.imageView));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final boolean output = super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.xmlmenu, menu);
        return output;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final boolean output = super.onOptionsItemSelected(item);
        TextView textView = findViewById(R.id.tytul);
        switch (item.getItemId()) {
            case R.id.pozycja1:
                textView.setText("Pozycja 1");
                break;
            case R.id.pozycja2:
                textView.setText("Pozycja 2");
                break;
            case R.id.pozycja3:
                textView.setText("Pozycja 3");
                break;
        }
        return output;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.floatingcontextmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final boolean output = super.onContextItemSelected(item);
        TextView textView = findViewById(R.id.tytul);
        switch (item.getItemId()) {
            case R.id.akcja1:
                textView.setText("Uruchomiono akcje 1.");
                break;
            case R.id.akcja2:
                textView.setText("Uruchomiono akcje 2.");
                break;
            case R.id.akcja3:
                textView.setText("Uruchomiono akcje 3.");
                break;
        }
        return output;
    }
}
