package com.example.philipp.fundmyshit.JavaClasses;

import android.content.Context;

import com.example.philipp.fundmyshit.R;

public class Challenges {
    public String title,desc,type;
    public int coverImg;

    private int[] Icons = {
            R.drawable.default_img

    };

    public Challenges(Context c, String title, String type){
        this.title = title;
        String[] Names = c.getResources().getStringArray(R.array.names);

        //Set type of lesson
        this.type = type;

        //automagically sets image
        coverImg = title.equals("Ballett") ? Icons[0]:Icons[1];
    }
}
