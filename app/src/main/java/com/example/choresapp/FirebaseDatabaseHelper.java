package com.example.choresapp;

import androidx.annotation.NonNull;

import com.example.choresapp.pq.MyPriorityQueue;
import com.example.choresapp.pq.PQInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<User> users = new ArrayList<>();
//    private String UserID="userID";

    public interface DataStatus{
        void DataIsLoaded(List<User> users);
        void DataIsInserted();
        void DataUpdated();
        void DataDeleted();
    }

    //UPDATE: Changing the database structure to get the usernames first then the keys
    public FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance("https://houseshare-2ddd0-default-rtdb.europe-west1.firebasedatabase.app/");
        mReference = mDatabase.getReference();
    }

    public void readData(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {//House/chores
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();//Clears the previous users from the list to replace them with the new ones
                System.out.println("##Clear<>");

                DataSnapshot tenantsSnapshot = snapshot.child("homes").child("homeID").child("tenants");//I made a variable so that I would not have to provide all the children each time.

                for(DataSnapshot UserNode : tenantsSnapshot.getChildren()){//Loops through all the children (users) of the chores parent in the database.
                    System.out.println("### "+UserNode.getKey());
                    User user = new User();
                    PQInterface mPQ = new MyPriorityQueue();//Creates instance of the priority queue. I need a new priority queue for each user to only stores one users chores.

                    //Getting the name from the users list
                    String userId = UserNode.getKey();//gets the user ID
                    String userName = snapshot.child("users").child(userId).child("name").getValue(String.class);//grabs the name of the user e.g. Fabian; Mark; James.

                    user.setName(userName);//Sets the user name
                    users.add(user); //Adds to the user list

                    for(DataSnapshot keyNode : tenantsSnapshot.child(UserNode.getKey()).child("chores").getChildren()){//loops through all the child nodes of the users to grab all the chores of the user.
                        if(keyNode.child("priority").exists() && keyNode.child("date").exists() && keyNode.child("date").exists()){//checks to see if the chore the necessary children. Used to prevent the app from crashing.
                            Chore chore = keyNode.getValue(Chore.class);//creates a new chore object for each user.
                            int priority = keyNode.child("priority").getValue(Integer.class);//gets the priority of the chore.
                            mPQ.enqueue(priority, chore);//Adds the chore and the priority to the priority queue to be arranged.
                            System.out.println("###> chores: "+keyNode.getKey()+" > "+ keyNode.getValue());
                        }
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

    public void addChore(String user, Chore chore, final DataStatus dataStatus){
        String key = mReference.child("homes").child("homeID").child("tenants").child(user).push().getKey();
        mReference.child(user).child(key).setValue(chore).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsInserted();
            }
        });

    }

    public void updateChore(String user,String key, Chore chore, final DataStatus dataStatus){
        mReference.child(user).child(key).setValue(chore).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataUpdated();
            }
        });
    }

    public void deleteChore(String user, String key, final DataStatus dataStatus){
        mReference.child(user).child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataDeleted();
            }
        });

    }


}
