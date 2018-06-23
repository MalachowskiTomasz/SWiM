package com.company.swim6a.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.company.swim6a.R;

public class CheckableRelativeLayout extends android.widget.RelativeLayout implements Checkable {
    CheckedTextView view;

    public CheckableRelativeLayout(Context context) {
        super(context);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setChecked(boolean b) {
        view = findViewById(R.id.somthing);
        view.setChecked(b);
    }

    @Override
    public boolean isChecked() {
        view = findViewById(R.id.somthing);
        return  view.isChecked();
    }

    @Override
    public void toggle() {
        view = findViewById(R.id.somthing);
        view.toggle();
    }
}
