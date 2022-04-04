package com.example.mydumbjavaapp;

public class Chore {
    private String name;
    private String assigned_to;

    public Chore() {
    }

    public Chore(String name, String assignedTo) {
        this.name = name;
        this.assigned_to = assignedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(String assigned_to) {
        this.assigned_to = assigned_to;
    }
}
