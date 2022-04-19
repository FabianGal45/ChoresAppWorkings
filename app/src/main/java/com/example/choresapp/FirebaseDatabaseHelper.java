package com.example.choresapp;

import androidx.annotation.NonNull;

import com.example.choresapp.pq.MyPriorityQueue;
import com.example.choresapp.pq.PQInterface;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private String mHouseID;
    private String userID="userID1";


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
        getHouseID();
    }


    public void readData(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {//House/chores
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();//Clears the previous users from the list to replace them with the new ones
                System.out.println("##Clear<>");

                String houseID = snapshot.child("users").child(userID).child("home").getValue(String.class);//Stores the Id of the house the user is part of. It doesn't have to be an object it can also be a variable and it wil behave the same.
                DataSnapshot tenantsSnapshot = snapshot.child("homes").child(houseID).child("tenants");//I made a variable so that I would not have to provide all the children each time.

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
                            String cName = chore.getName();
                            int cPriority = chore.getPriority();
                            String cDate=chore.getDate();
                            ChoreWithID choreWithID = new ChoreWithID(cName,cPriority,cDate,keyNode.getKey());


                            int priority = keyNode.child("priority").getValue(Integer.class);//gets the priority of the chore.
                            mPQ.enqueue(priority, choreWithID);//Adds the chore and the priority to the priority queue to be arranged.
                            System.out.println("###> chores: "+keyNode.getKey()+" > "+ keyNode.getValue());
                        }
                    }
                    user.setChoreList((ArrayList<ChoreWithID>) mPQ.getChores());//sets the list of chores with the chores that have been arranged based on their priority.
                }
                dataStatus.DataIsLoaded(users);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getHouseID(){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mHouseID = snapshot.child("users").child(userID).child("home").getValue(String.class);//Stores the Id of the house the user is part of. It doesn't have to be an object it can also be a variable and it wil behave the same.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addChore(Chore chore, final DataStatus dataStatus){
        getHouseID();
        System.out.println("???1"+ mHouseID);//Todo: house ID is null again

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mHouseID = snapshot.child("users").child(userID).child("home").getValue(String.class);//Stores the Id of the house the user is part of. It doesn't have to be an object it can also be a variable and it wil behave the same.
                System.out.println("???2"+ mHouseID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        System.out.println("???3"+ mHouseID);
        String key = mReference.child("homes").child(mHouseID).child("tenants").child(userID).child("chores").push().getKey();
        System.out.println("???key: "+ key);
        mReference.child("homes").child("homeID").child("tenants").child(userID).child("chores").child(key).setValue(chore).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsInserted();
            }
        });


    }

    public void updateChore(String userID, String choreID ,Chore chore, final DataStatus dataStatus){
        mReference.child("homes").child("homeID").child("tenants").child(userID).child("chores").child(choreID).setValue(chore).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataUpdated();
            }
        });
    }

    //TODO: get the correct user for each chore that is being clicked or only allow the user that is signed in to tick their own chores.
    public void deleteChore(String user, String key, final DataStatus dataStatus){//TODO: Make it so you can only delete your own chores
        mReference.child("homes").child("homeID").child("tenants").child(userID).child("chores").child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataDeleted();
            }
        });

    }


}
