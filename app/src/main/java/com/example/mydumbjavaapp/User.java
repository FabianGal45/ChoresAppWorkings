package com.example.mydumbjavaapp;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Chore> choreList;

    //Constructors
    public User() {
        choreList = new ArrayList<>();
        name = "";
    }

    public User(String name, Chore chore) {
        this.name = name;
        this.choreList.add(chore);
    }

    //constructor
    //Method used for testing
    public void printChores(){
        for(int i = 0; i< choreList.size(); i++){
            System.out.println(">> Chores: "+ choreList.get(i).getName());
        }
    }

    //I realized that when data wipes from the array of chores in the database helper they wipe in here too. Because of that, I have created this method.
    public void addToChores(Chore chore){
        choreList.add(chore);
    }


    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Chore> getChoreList() {
        return choreList;
    }

}
