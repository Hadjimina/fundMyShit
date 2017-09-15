package com.example.philipp.fundmyshit.HelperClass;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.philipp.fundmyshit.JavaClasses.Challenges;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HelperClass {



    //helperfunction to retrive all current challenges
   public void loadArray(Context mCont, ArrayList<Challenges> challenges, String arrayName)
    {
        SharedPreferences sp = mCont.getSharedPreferences(arrayName, MODE_PRIVATE);
        challenges.clear();
        int size = sp.getInt(arrayName+" Status_size", 0);

        for(int i=0;i<size;i++)
        {
            challenges.add(new Challenges(mCont,sp.getString("Status_" + i,null), arrayName));
        }

    }

    //helper function to save to current challenges
    public boolean saveArray(Context mCont, ArrayList<Challenges> challenges, String arrayName)
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
}
