package com.example.philipp.fundmyshit.HelperClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.philipp.fundmyshit.JavaClasses.Challenges;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class HelperClass extends Activity{


    public void testFunctionGet(){
    String url = "https://fundmyshit.herokuapp.com/challenges";


        try {
            String returnString = new getData().execute(url).get();
            Log.i("mystring",returnString);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public void testFunctionPost(){
        String url = "https://fundmyshit.herokuapp.com/challenges";

        //Add all your parameters to be sent here
        //this example is for a challenge
        //ATTENTION TAKE CARE TO PUT AVAILABLE USER_IDs
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", "Dummy"));
        params.add(new BasicNameValuePair("description", "Dummy description"));
        params.add(new BasicNameValuePair("price", "43"));
        params.add(new BasicNameValuePair("user_id", "5"));



        try {
            String paramString = getQuery(params);
            Log.i("Params",paramString);
            new postData().execute(url, paramString);

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //helper function to save to current challenges
    public static boolean saveArray(Context mCont, ArrayList<Challenges> challenges, String arrayName)
    {

        SharedPreferences sp = mCont.getSharedPreferences(arrayName, MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();

        mEdit1.putInt(arrayName+" Status_size", challenges.size());

        for(int i=0;i<challenges.size();i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, challenges.get(i).title);

        }

        return mEdit1.commit();
    }

    public static ArrayList<Challenges> getFeedChallenges(){
        //TODO do get request

        int dummyID = 4;
        int dummyPrice = 50;
        String dummyTitle = "Dummy Challenge";
        String dummyDesc =  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque rhoncus, lacus vel tincidunt ornare, libero ante luctus nunc, ac porttitor nunc enim egestas lorem. Proin auctor turpis eleifend magna ultricies, quis ullamcorper libero tincidunt. Morbi consectetur lectus id aliquet fringilla.";
        Challenges dummyChallenge1 = new Challenges(dummyTitle,dummyID,dummyPrice,dummyDesc);
        Challenges dummyChallenge2 = new Challenges(dummyTitle,dummyID+1,dummyPrice,dummyDesc);

        ArrayList<Challenges> feedChallenges = new ArrayList<>();
        feedChallenges.add(dummyChallenge1);
        feedChallenges.add(dummyChallenge2);

        return feedChallenges;
    }

    public static ArrayList<Challenges> getMyFundedChallenges(){
        //TODO do get request

        int dummyID = 4;
        int dummyPrice = 50;
        String dummyTitle = "Dummy Funded";
        String dummyDesc =  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque rhoncus, lacus vel tincidunt ornare, libero ante luctus nunc, ac porttitor nunc enim egestas lorem. Proin auctor turpis eleifend magna ultricies, quis ullamcorper libero tincidunt. Morbi consectetur lectus id aliquet fringilla.";
        Challenges dummyChallenge1 = new Challenges(dummyTitle,dummyID,dummyPrice,dummyDesc);
        Challenges dummyChallenge2 = new Challenges(dummyTitle,dummyID+1,dummyPrice,dummyDesc);

        ArrayList<Challenges> myFundedChallenges = new ArrayList<>();
        myFundedChallenges.add(dummyChallenge1);
        myFundedChallenges.add(dummyChallenge2);

        return myFundedChallenges;
    }

    public static ArrayList<Challenges> getPersonalChallenges(){
        //TODO do get request

        int dummyID = 4;
        int dummyPrice = 50;
        String dummyTitle = "Personal Challenges";
        String dummyDesc =  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque rhoncus, lacus vel tincidunt ornare, libero ante luctus nunc, ac porttitor nunc enim egestas lorem. Proin auctor turpis eleifend magna ultricies, quis ullamcorper libero tincidunt. Morbi consectetur lectus id aliquet fringilla.";
        Challenges dummyChallenge1 = new Challenges(dummyTitle,dummyID,dummyPrice,dummyDesc);
        Challenges dummyChallenge2 = new Challenges(dummyTitle,dummyID+1,dummyPrice,dummyDesc);

        ArrayList<Challenges> personalChallenges = new ArrayList<>();
        personalChallenges.add(dummyChallenge1);
        personalChallenges.add(dummyChallenge2);

        return personalChallenges;
    }


    private class getData extends AsyncTask<String, String, String> {

        // these Strings / or String are / is the parameters of the task, that can be handed over via the excecute(params) method of AsyncTask
        protected String doInBackground(String... params) {

            String urlValue = params[0];


            URL url;
            HttpsURLConnection connection;
            StringBuilder result= new StringBuilder();
            try{


                url = new URL(urlValue);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                Log.i("Status", String.valueOf(connection.getResponseCode()));

                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();

            }catch (Exception e){
                e.printStackTrace();
                Log.e("ERROR", "error in url conncection");
            }

            return result.toString();
        }

       // this is called whenever you call puhlishProgress(Integer), for example when updating a progressbar when downloading stuff
       /* protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }*/

        // the onPostexecute method receives the return type of doInBackGround()
        protected void onPostExecute(String result) {

        }
    }

    private class postData extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            String urlValue = params[0];
            String urlParameters = params[1];
            Log.i("params",urlParameters);

            try {
                //Log.i("params",urlParameters);
                //String urlParameters = "title=woot&description=b&price=500&user_id=5";
                URL url = new URL(urlValue);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

                writer.write(urlParameters);
                writer.flush();

                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                writer.close();
                reader.close();
            }catch (Exception e){
                e.printStackTrace();
                Log.e("ERROR","error in post");
            }

            return "hello";
            }

        // the onPostexecute method receives the return type of doInBackGround()
        protected void onPostExecute(String result) {

        }
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        String res = result.toString()/*.replaceAll("\\s","a")*/;
        return res;
    }
}
