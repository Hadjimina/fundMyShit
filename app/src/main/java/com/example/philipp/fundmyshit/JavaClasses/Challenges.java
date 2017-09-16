package com.example.philipp.fundmyshit.JavaClasses;

public class Challenges {
    public String title,description, videoLink;
    public int userID, price, currentPrice, challengeID;



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

    //TODO function to push to database & to get challengeID

}
