package com.example.philipp.fundmyshit.HelperClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.philipp.fundmyshit.JavaClasses.Challenges;

import java.util.ArrayList;

public class HelperClass extends Activity{



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

    public static ArrayList<Challenges> getFeedChallenges(){
        //TODO do get request

        int dummyID = 4;
        int dummyPrice = 50;
        String dummyTitle = "Dummy Title";
        String dummyDesc = "THIS IS A DESCRIPTION";
        Challenges dummyChallenge1 = new Challenges(dummyTitle,dummyID,dummyPrice,dummyDesc);
        Challenges dummyChallenge2 = new Challenges(dummyTitle,dummyID+1,dummyPrice,dummyDesc);

        ArrayList<Challenges> feedChallenges = new ArrayList<>();
        feedChallenges.add(dummyChallenge1);
        feedChallenges.add(dummyChallenge2);

        return feedChallenges;
    }
}
