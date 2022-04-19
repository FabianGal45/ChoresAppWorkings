package com.example.choresapp;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<ChoreWithID> choreList;

    //Constructors
    public User() {
        choreList = new ArrayList<>();
        name = "";
    }

    public User(String name, ChoreWithID chore) {
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

    //I realized that when data wipes from the array of chores in the database helper they wipe in here too. Because of that, I have created this method which allows the program to add a chore.
    public void addToChores(ChoreWithID chore){
        choreList.add(chore);
    }

    //This method is used to get the chores from the priority queue.
    public void setChoreList(ArrayList<ChoreWithID> choreArrayList){
        for(ChoreWithID chore:choreArrayList){
            addToChores(chore);
        }
    }


    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChoreWithID> getChoreList() {
        return choreList;
    }

}
