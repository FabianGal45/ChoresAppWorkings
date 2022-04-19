package com.example.choresapp;

public class ChoreWithID extends Chore{

    private String id;

    public ChoreWithID(){

    }

    public ChoreWithID(String name, int priority, String date, String id) {
        super(name, priority, date);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
