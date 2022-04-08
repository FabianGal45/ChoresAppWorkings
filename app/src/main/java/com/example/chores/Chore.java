package com.example.chores;

public class Chore {
    private String name;
    private int priority;
    private String date;

    //Constructors
    public Chore() {
    }

    public Chore(String name, int priority, String date) {
        this.name = name;
        this.priority = priority;
        this.date = date;
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
