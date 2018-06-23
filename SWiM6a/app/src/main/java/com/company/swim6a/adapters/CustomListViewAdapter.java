package com.company.swim6a.adapters;

import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.swim6a.R;
import com.company.swim6a.entity.Note;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.company.swim6a.MainActivity.player;

public class CustomListViewAdapter extends BaseAdapter {
    private List<Note> notes;
    private LayoutInflater inflater;
    private @LayoutRes int layoutResource;
    private AppCompatActivity activity;

    private View lastNote;

    public CustomListViewAdapter(List<Note> notes, LayoutInflater inflater, @LayoutRes int layoutResource, AppCompatActivity activity) {
        this.notes = notes;
        this.inflater = inflater;
        this.layoutResource = layoutResource;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(layoutResource, null);

        TextView title = view.findViewById(R.id.noteTitleTextView);
        TextView author = view.findViewById(R.id.noteAuthorTextView);
        TextView date = view.findViewById(R.id.noteDateTextView);
        TextView description = view.findViewById(R.id.noteDescriptionTextView);
        ImageView playButton = view.findViewById(R.id.notePlayButtonImage);
        ImageView pauseButton = view.findViewById(R.id.notePauseButtonImage);

        Note note = notes.get(i);
        title.setText(note.getTitle());
        author.setText(note.getAuthor());
        description.setText(note.getDescription());

        Date datetime = note.getDate();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        date.setText(df.format(datetime));

        player.setOnCompletionListener(x -> {
            playButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.INVISIBLE);
        });

        // TODO: 11.05.2018 On click add sound
        View finalView = view;
        playButton.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Odtworz", Toast.LENGTH_SHORT).show();
            if (player.isPlaying() || player.getCurrentPosition() > 1) {
                player.stop();
                player.reset();
            }
            v.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            try {
                player.setDataSource(finalView.getContext(), Uri.parse(note.getFile().getPath()));
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        pauseButton.setOnClickListener(v -> {
            v.setVisibility(View.INVISIBLE);
            playButton.setVisibility(View.VISIBLE);
            Toast.makeText(v.getContext(), "Pauza", Toast.LENGTH_SHORT).show();
            player.pause();
        });

        return view;
    }
}
