package com.example.philipp.fundmyshit.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.philipp.fundmyshit.R;

public class SettingsActivity extends AppCompatActivity {

    //private Toolbar myChildToolbar;
    private String beerString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        TextView name = (TextView) findViewById(R.id.username);

        ab.setDisplayHomeAsUpEnabled(true);




    }
}
