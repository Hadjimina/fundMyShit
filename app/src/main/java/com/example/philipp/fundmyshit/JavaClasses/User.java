package com.example.philipp.fundmyshit.JavaClasses;



public class User {

    private String forename;
    private String surname;
    private String email;

    private long userID;

    //default constructor
    public User(){

    }

    //constructor
    public User(String forename, String surname, String email){
        this.forename = forename;
        this.surname = surname;
        this.email = email;

    }

    public void setUserID(long ID){
        this.userID = ID;
    }

    public long getUserID(){
        return this.userID;
    }
    public String getForename(){
        return this.forename;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getEmail(){
        return this.email;
    }


}
