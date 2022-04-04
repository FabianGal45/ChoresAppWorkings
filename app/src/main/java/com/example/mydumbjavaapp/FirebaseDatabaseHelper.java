package com.example.mydumbjavaapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceChores;
    private List<Chore> chores = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Chore> chores, List<String> keys);
        void DataInserter();
        void DataUpdated();
        void DataDeleted();
    }

    public FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance("https://mydumbjavaapp-default-rtdb.europe-west1.firebasedatabase.app/");
        mReferenceChores = mDatabase.getReference("chores");
    }

    public void readChores(final DataStatus dataStatus){
        mReferenceChores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chores.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Chore chore = keyNode.getValue(Chore.class);
                    chores.add(chore);
                }
                dataStatus.DataIsLoaded(chores,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
