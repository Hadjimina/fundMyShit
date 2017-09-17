package com.example.philipp.fundmyshit.HelperClass;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.philipp.fundmyshit.Activities.MainActivity;
import com.example.philipp.fundmyshit.JavaClasses.Challenges;

import org.apache.http.NameValuePair;
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



    public static String doPostRequest(String url,List<NameValuePair> params ){
        String returnString = "";
        try {
            String paramString = getQuery(params);

            returnString = new postData().execute(url, paramString).get();

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public static String doGetRequest(String url){
        String returnString = "";
        try {
            returnString =  new getData().execute(url).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return  returnString;
    }




    public ArrayList<Challenges> getFeedChallenges(){
        String url = "https://fundmyshit.herokuapp.com/challenges";
        String typeOfReq = "GET";
        try {
            String returnString = new getData().execute(url,typeOfReq).get();
            System.out.println("RETURNSTRING: "+returnString);

            JSONArray jsonArray = new JSONArray(returnString);
            ArrayList<Challenges> feedChallenges = parseChallenges(jsonArray);

            for (Challenges c : feedChallenges){
                Log.i("asdf1", String.valueOf(c.challengeID));
            }


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
        int sessionUserID = MainActivity.getSessionUserID();
        System.out.println("FUNDED sessionID: "+sessionUserID);
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
        int sessionUserID = MainActivity.getSessionUserID();
        String url = "https://fundmyshit.herokuapp.com/users/" + sessionUserID + "/challenges";
        String typeOfReq = "GET";

        try {
            String returnString = new getData().execute(url,typeOfReq).get();
            System.out.println("RETURNSTRING: "+returnString);
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
                Challenges c = new Challenges(o.getString("title"), o.getInt("challenger_id"), o.getInt("price"), o.getString("description"));
                c.currentPrice = o.getInt("current_price");
                c.setChallengeID(o.getInt("id"));
                c.setVideoLink(o.getString("video"));


                ch.add(c);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return ch;
    }


    private static class getData extends AsyncTask<String, String, String> {

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

                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();

            }catch (Exception e){
                e.printStackTrace();
                Log.e("ERROR", "error in get");
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

    public static class postData extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            String urlValue = params[0];
            String urlParameters = params[1];
            Log.i("params",urlParameters);

            StringBuilder result= new StringBuilder();
            try {

                URL url = new URL(urlValue);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

                writer.write(urlParameters);
                writer.flush();

                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                writer.close();
                reader.close();


            }catch (Exception e){
                e.printStackTrace();
                Log.e("ERROR","error in post");
            }

            return result.toString();
            }

        // the onPostexecute method receives the return type of doInBackGround()
        protected void onPostExecute(String result) {

        }
    }

    public static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
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
