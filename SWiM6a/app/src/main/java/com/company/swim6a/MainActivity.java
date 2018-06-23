package com.company.swim6a;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.company.swim6a.adapters.CustomListViewAdapter;
import com.company.swim6a.entity.Note;
import com.company.swim6a.listeners.NotesMultiChoiceModeListener;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    public static List<Note> notes;
    public static MediaPlayer player;
    public CustomListViewAdapter noteAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        recreate();

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notes = new ArrayList<>();
        loadNotes();

        player = new MediaPlayer();

        noteAdapter = new CustomListViewAdapter(notes, getLayoutInflater(), R.layout.item_note, this);
        listView = findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setAdapter(noteAdapter);
        listView.setMultiChoiceModeListener(new NotesMultiChoiceModeListener(this, listView));

        FloatingActionButton fab = findViewById(R.id.fab);
        Intent i = new Intent(this, RecordActivity.class);
        fab.setOnClickListener(view -> startActivityForResult(i, 0));
    }

    private void loadNotes() {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/notatek");
        if (myDir.exists()) {
            for (File file : myDir.listFiles()) {
                String[] splittedName = file.getName().split("\\[]");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Note note = null;
                try {
                    note = new Note(
                            splittedName[1],
                            splittedName[2],
                            df.parse(splittedName[0]),
                            splittedName[3].replace(".wav", ""),
                            file);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                notes.add(note);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Cos tam", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
