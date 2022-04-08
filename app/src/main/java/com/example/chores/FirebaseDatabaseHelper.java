package com.example.chores;

import androidx.annotation.NonNull;

import com.example.chores.pq.MyPriorityQueue;
import com.example.chores.pq.PQInterface;
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
    private List<User> users = new ArrayList<>();


    public interface DataStatus{
        void DataIsLoaded(List<User> users);
        void DataInserter();
        void DataUpdated();
        void DataDeleted();
    }

    //UPDATE: Changing the database structure to get the usernames first then the keys
    public FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance("https://mydumbjavaapp-default-rtdb.europe-west1.firebasedatabase.app/");
        mReference = mDatabase.getReference("chores");
    }

    public void readData(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();//Clears the previous users from the list to replace them with the new ones

                //Loops through all the children (users) of the chores parent in the database.
                for(DataSnapshot UserNode : snapshot.getChildren()){
                    System.out.println("### "+UserNode.getKey());
                    User user = new User();
                    PQInterface mPQ = new MyPriorityQueue();//Creates instance of the priority queue. I need a new priority queue for each user to only stores one users chores.
                    user.setName(UserNode.getKey());//Gets the key of the reference e.g Fabian, James, Mark
                    users.add(user); //Adds to the user list

                    //loops through all the child nodes of the users to grab all the chores of the user.
                    for(DataSnapshot keyNode : snapshot.child(UserNode.getKey()).getChildren()){
                        Chore chore = keyNode.getValue(Chore.class);//creates a new chore object for each user.
                        long priority = (long) keyNode.child("priority").getValue();//gets the priority of the chore.
                        mPQ.enqueue((int)priority, chore);//Adds the chore and the priority to the priority queue to be arranged.
                        System.out.println("###> chores: "+keyNode.getKey()+" > "+ keyNode.getValue());
                    }

                    user.setChoreList((ArrayList<Chore>) mPQ.getChores());//sets the list of chores with the chores that have been arranged based on their priority.
                }
                dataStatus.DataIsLoaded(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
