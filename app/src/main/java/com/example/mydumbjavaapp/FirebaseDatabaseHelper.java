package com.example.mydumbjavaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Chore> chores = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Chore> chores, List<User> users);
        void DataInserter();
        void DataUpdated();
        void DataDeleted();
    }

    //UPDATE: Changing the database structure to get the usernames first then the keys
    public FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance("https://mydumbjavaapp-default-rtdb.europe-west1.firebasedatabase.app/");
        mReference = mDatabase.getReference("chores");
    }

    public void readUsers(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot UserNode : snapshot.getChildren()){
                    User user = new User(UserNode.getKey());
                    users.add(user); // [Fabian , James, Mark]
                    System.out.println("### "+UserNode.getKey());
                }
                System.out.println(">>> users: "+users);
                dataStatus.DataIsLoaded(chores, users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Make this readChoresForUser and pass the user
    public void readChores(final DataStatus dataStatus){

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chores.clear();
                List<String> keys = new ArrayList<>();

                //gets the info of the user from the database

                for(DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());//[1, 2, 1, 1, 2]
//                    Chore chore = keyNode.getValue(Chore.class);
//                    chores.add(chore);
                    System.out.println("###> key: "+keyNode.getKey()+" > "+ keyNode.getValue());
                }

//                dataStatus.DataIsLoaded(chores, users);
                System.out.println(">>> keys: "+keys);//Todo: Organise the keys
                System.out.println(">>> chores: "+chores.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
