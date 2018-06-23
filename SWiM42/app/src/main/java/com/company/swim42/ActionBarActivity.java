package com.company.swim42;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.company.swim42.Fragments.Fragment1;
import com.company.swim42.Fragments.Fragment2;

public class ActionBarActivity extends AppCompatActivity implements ActionBar.TabListener {
    Fragment1 fragment1;
    Fragment2 fragment2;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout, fragment1);
        transaction.detach(fragment1);
        transaction.add(R.id.frameLayout, fragment2);
        transaction.detach(fragment2);
        transaction.commit();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab1 = actionBar.newTab()
                .setText("Fragment 1")
                .setTabListener(this);
        actionBar.addTab(tab1);

        ActionBar.Tab tab2 = actionBar.newTab()
                .setText("Fragment 2")
                .setTabListener(this);
        actionBar.addTab(tab2);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment1);
        transaction.remove(fragment2);
        transaction.commit();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                transaction.detach(fragment2);
                transaction.attach(fragment1);
                break;
            case 1:
                transaction.detach(fragment1);
                transaction.attach(fragment2);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
