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
        void DataIsLoaded(List<Chore> chores, List<String> users);
        void DataInserter();
        void DataUpdated();
        void DataDeleted();
    }

    //UPDATE: Changing the database structure to get the usernames first then the keys
    public FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance("https://mydumbjavaapp-default-rtdb.europe-west1.firebasedatabase.app/");
        mReferenceChores = mDatabase.getReference("chores");
    }

    public void readChores(final DataStatus dataStatus){

        mReferenceChores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chores.clear();
                List<String> users = new ArrayList<>();
                List<String> keys = new ArrayList<>();

                //gets the info of the user from the database
                for(DataSnapshot UserNode : snapshot.getChildren()){
                    users.add(UserNode.getKey()); // [Fabian , James, Mark]
                    System.out.println("### "+UserNode.getKey());
                    for(DataSnapshot keyNode : snapshot.child(UserNode.getKey()).getChildren()){
                        keys.add(keyNode.getKey());//[1, 2, 1, 1, 2]
                        Chore chore = keyNode.getValue(Chore.class);
                        chores.add(chore);
                        System.out.println("###> key: "+keyNode.getKey()+" > "+ keyNode.getValue());
                    }
                }
                dataStatus.DataIsLoaded(chores, users);
                System.out.println(">>> users: "+users);
                System.out.println(">>> keys: "+keys);//Todo: Organise the keys
                System.out.println(">>> chores: "+chores.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
