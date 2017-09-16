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

    public void setChallengeID(int challengeID){
        this.challengeID = challengeID;
    }
    //updates current price if someone pledged money
    public void updateCurrentPrice(int pledgedAmount){
        currentPrice = currentPrice + pledgedAmount;


    }
    public void setVideoLink(String link){
        this.videoLink = link;
    }



    public int getChallengeID(){
        return this.challengeID;
    }

}
