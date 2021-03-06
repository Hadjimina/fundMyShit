package com.example.philipp.fundmyshit.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.example.philipp.fundmyshit.HelperClass.HelperClass;
import com.example.philipp.fundmyshit.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {

    //private Toolbar myChildToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTheme(R.style.MyMaterialTheme_Base);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int userID = MainActivity.getSessionUserID();
        String url = "https://fundmyshit.herokuapp.com/users/" + userID;
        try {
            String returnString = HelperClass.doGetRequest(url);
            JSONObject obj = new JSONObject(returnString);
            String balance = obj.getString("balance");
            TextView funds = (TextView) findViewById(R.id.balance);
            funds.setText(balance);
            String name = obj.getString("username");
            TextView nameView = (TextView) findViewById(R.id.username);
            nameView.setText(name);
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
