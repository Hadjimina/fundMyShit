package com.example.philipp.fundmyshit.Activities;

        import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.philipp.fundmyshit.R;


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
                errorEmail.setVisibility(View.INVISIBLE);
                errorPassword.setVisibility(View.INVISIBLE);
                Integer userID = 0; // dummyFunction(stringEmail, stringPassword);
                if (userID != null) {
                    sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(getString(R.string.sessionUserID), userID); //write a method to obtain current user ID
                    editor.commit();
                } else {
                    errorPassword.setVisibility(View.VISIBLE);
                }


            }
        });
    }

}