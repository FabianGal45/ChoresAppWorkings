package com.example.choresapp.pq;

import com.example.choresapp.Chore;
import com.example.choresapp.ChoreWithID;

public class PQElement {
    private int key;
    private ChoreWithID chore;

    public PQElement(int key, ChoreWithID chore) {
        this.key = key;
        this.chore = chore;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public ChoreWithID getChore() {
        return chore;
    }

    public void setChore(ChoreWithID chore) {
        this.chore = chore;
    }
}
