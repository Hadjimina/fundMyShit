package com.example.philipp.fundmyshit.HelperClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.philipp.fundmyshit.JavaClasses.Challenges;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class HelperClass extends Activity{


    public void testFunctionGet(){
    String url = "https://fundmyshit.herokuapp.com/challenges";
    String typeOfReq = "GET";

        try {
            String returnString = new getData().execute(url,typeOfReq).get();
            Log.i("mystring",returnString);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /*
    public void testFunctionPost(){
        String url = "https://fundmyshit.herokuapp.com/challenges";
        String typeOfReq = "POST";

        try {

            String returnString = new postData().execute(url,typeOfReq).get();
            Log.i("mystring",returnString);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
*/

    //helperfunction to retrive all current challenges
   public static void loadArray(Context mCont, ArrayList<Challenges> challenges, String arrayName)
    {
        SharedPreferences sp = mCont.getSharedPreferences(arrayName, MODE_PRIVATE);
        challenges.clear();
        int size = sp.getInt(arrayName+" Status_size", 0);

        for(int i=0;i<size;i++)
        {
            //challenges.add(new Challenges(mCont,sp.getString("Status_" + i,null), arrayName));
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



    public ArrayList<Challenges> getFeedChallenges(){
        //TODO do get request
        String url = "https://fundmyshit.herokuapp.com/challenges";
        String typeOfReq = "GET";
        try {
            String returnString = new getData().execute(url,typeOfReq).get();
            JSONArray jsonArray = new JSONArray(returnString);
            ArrayList<Challenges> feedChallenges = parseChallenges(jsonArray);
            return feedChallenges;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;

    }

    public ArrayList<Challenges> getMyFundedChallenges(Activity a){
        //TODO do get request

        SharedPreferences sharedPref = a.getPreferences(Context.MODE_PRIVATE);
        int sessionUserID = sharedPref.getInt("sessionUserID", 1);
        System.out.println("USERID: "+sessionUserID);

        String url = "https://fundmyshit.herokuapp.com/payments/" + sessionUserID + "/payed_challenges";
        String typeOfReq = "GET";
        try {
            String returnString = new getData().execute(url,typeOfReq).get();
            JSONArray jsonArray = new JSONArray(returnString);
            ArrayList<Challenges> myFundedChallenges = parseChallenges(jsonArray);
            return myFundedChallenges;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;

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

    private ArrayList<Challenges> parseChallenges(JSONArray arr){
        ArrayList<Challenges> ch = new ArrayList<>();

        for(int i = 0; i < arr.length(); i++){
            try {
                JSONObject o = arr.getJSONObject(i);
                Challenges c = new Challenges(o.getString("title"), o.getInt("id"), o.getInt("price"), o.getString("description"));
                ch.add(c);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return ch;
    }


    private class getData extends AsyncTask<String, String, String> {

        // these Strings / or String are / is the parameters of the task, that can be handed over via the excecute(params) method of AsyncTask
        protected String doInBackground(String... params) {

            String urlValue = params[0];
            String typeOfReq = params[1];

            URL url;
            HttpsURLConnection connection;
            StringBuilder result= new StringBuilder();
            try{


                url = new URL(urlValue);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setRequestMethod(typeOfReq);
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
/*
    private class postData extends AsyncTask<String, String, String> {

        // these Strings / or String are / is the parameters of the task, that can be handed over via the excecute(params) method of AsyncTask
        protected String doInBackground(String... params) {

            String urlValue = params[0];
            String typeOfReq = params[1];

            URL url;
            HttpsURLConnection connection;
            StringBuilder result= new StringBuilder();
            try{


                url = new URL(urlValue);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setDoInput(true);
                //connection.setDoOutput(true);
                connection.setRequestMethod(typeOfReq);
                connection.setInstanceFollowRedirects(false);


                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("title", "dummy title")
                        .appendQueryParameter("description", "dummy desc")
                        .appendQueryParameter("price", "500")
                        .appendQueryParameter("user_id", "5");
                String query = builder.build().getEncodedQuery();

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                connection.connect();

            }catch (Exception e){
                e.printStackTrace();
                Log.e("ERROR", "error in url conncection");
            }

            return result.toString();
        }



        // the onPostexecute method receives the return type of doInBackGround()
        protected void onPostExecute(String result) {

        }
    }*/
}
