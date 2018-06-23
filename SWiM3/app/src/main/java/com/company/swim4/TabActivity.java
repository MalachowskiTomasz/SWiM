package com.company.swim4;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class TabActivity extends AppCompatActivity implements ActionBar.TabListener{
    Fragment11 f11;
    Fragment12 f12;
    FragmentTransaction transakcja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        f11 = new Fragment11();
        f12 = new Fragment12();

        transakcja = getSupportFragmentManager().beginTransaction();
        transakcja.add(R.id.kontener2, f11);
        transakcja.detach(f11);
        transakcja.add(R.id.kontener2, f12);
        transakcja.detach(f12);
        transakcja.commit();

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab1 = bar.newTab()
                .setText("F11")
                .setTabListener(this);
        bar.addTab(tab1);
        ActionBar.Tab tab2 = bar.newTab()
                .setText("F12")
                .setTabListener(this);
        bar.addTab(tab2);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        switch (tab.getPosition()) {
            case 0:
                ft.detach(f12);
                ft.attach(f11);
                break;
            case 1:
                ft.detach(f11);
                ft.attach(f12);
                break;
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
