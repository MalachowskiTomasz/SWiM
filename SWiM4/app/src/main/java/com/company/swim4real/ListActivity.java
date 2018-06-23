package com.company.swim4real;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {
    private ActionMode mActionMode = null;
    private ActionMode.Callback mContextMenu = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.actioncontextmenu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            TextView textView = findViewById(R.id.tytul);
            switch (menuItem.getItemId()) {
                case R.id.akcja1:
                    textView.setText("Uruchomiono akcje 1.");
                    makeToast("Uruchomiono akcje 1.");
                    break;
                case R.id.akcja2:
                    textView.setText("Uruchomiono akcje 2.");
                    makeToast("Uruchomiono akcje 2.");
                    break;
                case R.id.akcja3:
                    textView.setText("Uruchomiono akcje 3.");
                    makeToast("Uruchomiono akcje 3.");
                    break;
                default:
                    return false;
            }
            actionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.imageView)
                .setOnLongClickListener(v -> {
                    if (mActionMode != null)
                        return false;
                    mActionMode = startActionMode(mContextMenu);
                    return true;
                });
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
