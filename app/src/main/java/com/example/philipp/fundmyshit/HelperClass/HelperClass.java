package com.example.philipp.fundmyshit.HelperClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.philipp.fundmyshit.JavaClasses.Challenges;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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


    //EXAMPLE get function, probably not needed anymore
    public void testFunctionGet(){
    String url = "https://fundmyshit.herokuapp.com/challenges";

        try {
            String returnString = new getData().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    //EXAMPLE post function, probably not needed anymore
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

            new postData().execute(url, paramString);

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPostRequest(String url,List<NameValuePair> params ){
        try {
            String paramString = getQuery(params);

            new postData().execute(url, paramString);

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetRequest(String url){
        try {
            String returnString = new getData().execute(url).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
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



    public ArrayList<Challenges> getFeedChallenges(){
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

    public ArrayList<Challenges> getPersonalChallenges(Activity a){
        SharedPreferences sharedPref = a.getPreferences(Context.MODE_PRIVATE);
        int sessionUserID = sharedPref.getInt("sessionUserID", 1);
        System.out.println("USERID: "+sessionUserID);

        String url = "https://fundmyshit.herokuapp.com/users/" + sessionUserID + "/challenges";
        String typeOfReq = "GET";

        try {
            String returnString = new getData().execute(url,typeOfReq).get();
            JSONArray jsonArray = new JSONArray(returnString);
            ArrayList<Challenges> personalChallenges = parseChallenges(jsonArray);
            return personalChallenges;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
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

        String res = result.toString().replaceAll("\\s","+");
        return res;
    }
}
