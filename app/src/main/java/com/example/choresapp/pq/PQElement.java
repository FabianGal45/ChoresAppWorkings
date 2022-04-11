package com.example.choresapp.pq;

import com.example.choresapp.Chore;

public class PQElement {
    private int key;
    private Chore chore;

    public PQElement(int key, Chore chore) {
        this.key = key;
        this.chore = chore;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Chore getChore() {
        return chore;
    }

    public void setChore(Chore chore) {
        this.chore = chore;
    }
}
