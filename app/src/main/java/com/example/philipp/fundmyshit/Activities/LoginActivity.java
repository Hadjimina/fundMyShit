package com.example.philipp.fundmyshit.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.philipp.fundmyshit.HelperClass.HelperClass;
import com.example.philipp.fundmyshit.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private Button signup,login;
    private TextView loginText, errorEmail, errorPassword;
    private EditText email, password;
    private String stringEmail, stringPassword;
    private SharedPreferences sharedPref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylogin);

        loginText = (TextView) findViewById(R.id.loginText);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        errorEmail = (TextView) findViewById(R.id.errorEmailLogin);
        errorPassword = (TextView) findViewById(R.id.errorPasswordLogin);

        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SignupActivity.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View x) {
                //get user input
                stringEmail = email.getText().toString();
                stringPassword = password.getText().toString();
                if(stringEmail.equals("") && stringPassword.equals("")){
                    sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("sessionUserID", 1); //write a method to obtain current user ID
                    editor.commit();
                    startActivity(new Intent(x.getContext(), MainActivity.class));
                }
                errorEmail.setVisibility(View.INVISIBLE);
                errorPassword.setVisibility(View.INVISIBLE);

                String url = "https://fundmyshit.herokuapp.com/login?email=" + email +"&password=" + password;

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", stringEmail));
                params.add(new BasicNameValuePair("password", stringPassword));


                if (userID != 0) {
                    sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(getString(R.string.sessionUserID), userID); //write a method to obtain current user ID
                    editor.commit();
                    startActivity(new Intent(x.getContext(), MainActivity.class));
                } else {
                    errorPassword.setVisibility(View.VISIBLE);
                }


            }
        });
    }

}