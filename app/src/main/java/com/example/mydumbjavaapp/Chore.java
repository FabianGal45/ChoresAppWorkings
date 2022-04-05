package com.example.mydumbjavaapp;

import java.time.LocalDate;

public class Chore {
    private String name;
    private int priority;
    private LocalDate date;

    //Constructors
    public Chore() {
    }

    public Chore(String name, int priority, LocalDate date) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
