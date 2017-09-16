package com.example.philipp.fundmyshit.JavaClasses;



public class User {

    private String email;
    private String name;


    private int userID;

    //default constructor
    public User(){

    }

    //constructor
    public User(String name, String email){
        this.name = name;
        this.email = email;

    }

    public int getUserID(){
        return this.userID;
    }
    public String getName(){return this.name;}
    public String getEmail(){
        return this.email;
    }


}
