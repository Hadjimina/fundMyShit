package com.example.philipp.fundmyshit.JavaClasses;

import android.content.Context;

import com.example.philipp.fundmyshit.R;

public class Challenges {
    public String title,description, videoLink;
    public int userID, price, currentPrice;



    public Challenges(String title, int userID, int price, String description){
        this.title = title;
        this.userID = userID;
        this.price = price;
        this.description = description;
        currentPrice = 0;


    }

    //updates current price if someone pledged money
    public void updateCurrentPrice(int pledgedAmount){
        currentPrice = currentPrice + pledgedAmount;
    }


}
