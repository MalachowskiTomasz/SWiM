package com.company.swim4real;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

public class JavaMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean output = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, 0, 1, "Pozycja 1");
        SubMenu pozycja2 = menu.addSubMenu(Menu.NONE, 1, 2, "Pozycja 2");
        pozycja2.add(Menu.NONE, 2, 1, "Pozycja 2.1");
        pozycja2.add(Menu.NONE, 3, 2, "Pozycja 2.2");
        pozycja2.add(Menu.NONE, 4, 3, "Pozycja 2.3");
        return output;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final boolean b = super.onOptionsItemSelected(item);
        TextView textView = findViewById(R.id.tytul);
        switch (item.getItemId()){
            case 0:
                textView.setText("Pozycja 1");
                break;
            case 2:
                textView.setText("Pozycja 2.1");
                break;
            case 3:
                textView.setText("Pozycja 2.2");
                break;
            case 4:
                textView.setText("Pozycja 2.3");
                break;
        }
        return b;
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
