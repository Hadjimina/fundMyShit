package com.example.philipp.fundmyshit.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
=======
>>>>>>> e340dd161a16e97de43e6a833473ca90bd61dafd

import com.example.philipp.fundmyshit.R;

public class SettingsActivity extends AppCompatActivity {

    //private Toolbar myChildToolbar;
    private String beerString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
<<<<<<< HEAD

=======
/*
        CheckBox beer = (CheckBox)findViewById(R.id.checkBox);
>>>>>>> e340dd161a16e97de43e6a833473ca90bd61dafd

        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        TextView name = (TextView) findViewById(R.id.username);
<<<<<<< HEAD

=======
        name.setText("Matt");
>>>>>>> e340dd161a16e97de43e6a833473ca90bd61dafd
        ab.setDisplayHomeAsUpEnabled(true);


        TextView balance = (TextView) findViewById(R.id.balance);
        balance.setText(100);



    }
}
