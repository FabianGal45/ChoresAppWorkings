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

                //System.out.println("### "+ snapshot.child("Fabian").child("1").getValue());
                //snapshot.child("Fabian").child("1").getValue(): {date=2022-04-05, name=Clean the dishes, priority=10}
                //snapshot.child("Fabian"): DataSnapshot { key = Fabian, value = {1={date=2022-04-05, name=Clean the dishes, priority=10}, 2={date=2022-04-06, name=Wash the cars, priority=5}} }
                //snapshot.getValue(): {James=[null, {priority=9, name=Shop for milk, finish_by=2022-04-06}], Fabian=[null, {date=2022-04-05, name=Clean the dishes, priority=10}, {date=2022-04-06, name=Wash the car, priotity=5}], Mark=[null, {date=2022-04-05, name=Clean, priority=9}, {date=2022-04-05, name=clean car, priority=7}]}
                //snapshot.getKey(): chores

                //gets the info of the user from the database
                for(DataSnapshot UserNode : snapshot.getChildren()){
                    users.add(UserNode.getKey()); // [Fabian , James, Mark]
                    System.out.println("### "+UserNode.getKey());
                    for(DataSnapshot keyNode : snapshot.child(UserNode.getKey()).getChildren()){
                        keys.add(keyNode.getKey());//[1, 2, 1, 1, 2]
                        System.out.println("###> key: "+keyNode.getKey()+" > "+ keyNode.getValue());
                        Chore chore = keyNode.getValue(Chore.class);
                        chores.add(chore);

                    }

                    // keyNode:
                    // DataSnapshot { key = Fabian, value = {1={date=2022-04-05, name=Clean the dishes, priority=10}, 2={date=2022-04-06, name=Wash the car, priotity=5}} }
                    // DataSnapshot { key = James, value = {1={name=Shop for milk, finish_by=2022-04-06, priority=9}} }
                    // DataSnapshot { key = Mark, value = {1={date=2022-04-05, name=Clean, priority=9}, 2={date=2022-04-05, name=clean car, priority=7}} }

                    // keyNode.getKey():
                    // Fabian
                    // James
                    // Mark
                }
                System.out.println(">>> users: "+users);
                System.out.println(">>> keys: "+keys);
                System.out.println(">>> chores: "+chores);
//                dataStatus.DataIsLoaded(chores,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
