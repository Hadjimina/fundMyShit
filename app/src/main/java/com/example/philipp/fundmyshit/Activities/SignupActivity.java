package com.example.philipp.fundmyshit.Activities;

/**
 * Created by david on 16.09.17.
 */

//STILL THE OLD SIGNUP

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.philipp.fundmyshit.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    private TextView textView;
    private TextView errorForename;
    private TextView errorSurname;
    private TextView errorPassword1;
    private TextView errorPassword2;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText checkPassword;
    private Button submit;
    public Boolean noErrors;




    //Dave do your thing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //setup of all components
        textView = (TextView) findViewById(R.id.new_account);

        name = (EditText) findViewById(R.id.editName);

        password = (EditText) findViewById(R.id.editPassword);

        checkPassword = (EditText) findViewById(R.id.editCheckPassword);

        email = (EditText) findViewById(R.id.editEmail);

        errorForename = (TextView) findViewById(R.id.errorForename);

        errorSurname = (TextView) findViewById(R.id.errorSurname);

        errorPassword1 = (TextView) findViewById(R.id.errorPassword);

        errorPassword2 = (TextView) findViewById(R.id.errorPassword2);


        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //set all error messages invisible
                errorForename.setVisibility(View.INVISIBLE);
                errorPassword1.setVisibility(View.INVISIBLE);
                errorPassword2.setVisibility(View.INVISIBLE);
                errorSurname.setVisibility(View.INVISIBLE);
                noErrors = true;

                //get user input
                String nameString = name.getText().toString();
                String passwordString = password.getText().toString();
                String checkPasswordString = checkPassword.getText().toString();
                final String emailString = email.getText().toString();

                try {
                    URL url = new URL("https://fundmyshit.herokuapp.com/challenges/users(.:format)");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    String postJsonData = "{\"username\":"+nameString+",\"email\":"+emailString+",\"password\":"+passwordString+" }";
                    wr.writeBytes(postJsonData);
                    wr.flush();
                    wr.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

                //input verification and error message displaying
                if(passwordString.length() > 20){
                    errorPassword2.setVisibility(View.VISIBLE);
                    noErrors = false;
                }

                if(isNameTooLong(nameString)){
                    errorForename.setVisibility(View.VISIBLE);
                    noErrors = false;
                }


                //Load the data to the database.
                if(noErrors){


                    startActivity(new Intent(v.getContext(), LoginActivity.class));

                }



            }
        });





    }

    //Check if inputs aren't too long
    private boolean isNameTooLong(String s){
        return s.length() > 30;
    }


}
