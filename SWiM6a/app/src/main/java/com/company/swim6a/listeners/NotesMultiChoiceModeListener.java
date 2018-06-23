package com.company.swim6a.listeners;

import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.company.swim6a.AudioRecorder;
import com.company.swim6a.MainActivity;
import com.company.swim6a.R;
import com.company.swim6a.entity.Note;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NotesMultiChoiceModeListener implements AbsListView.MultiChoiceModeListener {
    final MainActivity activity;
    final ListView listView;

    public NotesMultiChoiceModeListener(MainActivity activity, ListView listView) {
        this.activity = activity;
        this.listView = listView;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = activity.getMenuInflater();

        inflater.inflate(R.menu.context, menu);
        actionMode.setTitle("Title");

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
//        Toast.makeText(activity, String.valueOf(checkedPositions.keyAt(0) + String.valueOf(checkedPositions.valueAt(0))), Toast.LENGTH_SHORT).show();
        List<Integer> sth = new ArrayList<>();

        for (int i = 0; i < checkedPositions.size(); i++) {
            if (checkedPositions.valueAt(i))
                sth.add(i);
        }

        switch (menuItem.getItemId()) {
            case R.id.connectNotesAction:
                if (sth.size() != 2) {
                    Toast.makeText(activity, "Musisz zaznaczyć 2 notatki", Toast.LENGTH_SHORT).show();
                } else {
                    File toRemove = MainActivity.notes.get(sth.get(1)).getFile();
                    AudioRecorder.connectNotes(MainActivity.notes.get(sth.get(0)).getFile(), toRemove);
                    MainActivity.notes.remove(sth.get(1));
                    toRemove.delete();
                    activity.recreate();
                    actionMode.finish();
                }
                break;
            case R.id.removeNotesAction:
                Collections.sort(sth, (o1, o2) -> o2 - o1);
                for (int pos : sth) {
                    Note n = MainActivity.notes.get(pos);
                    n.getFile().delete();
                    MainActivity.notes.remove(pos);
                }
                actionMode.finish();
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}
