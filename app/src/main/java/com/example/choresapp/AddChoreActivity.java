package com.example.choresapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.choresapp.pq.PQElement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddChoreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button mAddBtn;
    private Button mCancelBtn;
    private EditText mChoreName;
    private int mChorePriority;
    private LocalDate date;
    private String houseID;
//    private ArrayList<User> users = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);

        mChoreName = (EditText) findViewById(R.id.choreNameED);
        date = LocalDate.now();

        mCancelBtn = (Button) findViewById(R.id.cancelBtn);
        mAddBtn = (Button) findViewById(R.id.updateChoreBtn);

        houseID = getIntent().getStringExtra("houseID");

//        Bundle bundle = getIntent().getExtras();
//        ArrayList<User> users = bundle.getParcelableArrayList("usersList");

        ArrayList<User> users = getIntent().getParcelableArrayListExtra("usersList");

        System.out.println("<><><><>>>>>>> This is the first user: "+users.get(1).getId());
        System.out.println("<><><><>>>>>>> Chore array for the first user: "+users.get(0).getChoreList());
//        System.out.println("//"+ users.get(0).getChoreList().get(0).getId());

        //Spinner setup
        Spinner prioritySpinner = findViewById(R.id.prioritySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.prioritySpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);
        prioritySpinner.setOnItemSelectedListener(this);



        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: create the algorithm in here.

                //calculate the total priorities
                for(User user: users){
                    int totalPriority = 0;
                    List<ChoreWithID> chores = user.getChoreList();
                    for(ChoreWithID chore: chores){
//                        System.out.println("#### User: "+user.getName()+", Priority: "+ chore.getPriority()+", "+chore.getName()+", "+chore.getDate());
                        totalPriority += chore.getPriority();
                    }
                    user.setTotalPriority(totalPriority);
                    System.out.println("#### User: "+user.getName()+", Priority Total: "+ totalPriority);
                }


                //find the lowest and the highest
                ArrayList<User> prioritiesList = new ArrayList<>();
                for(User user: users){
                    addToList(prioritiesList, user, user.getTotalPriority());
                }
                User userLowestPriorityTotal = prioritiesList.get(0);
                User userHighestPriorityTotal = prioritiesList.get(prioritiesList.size()-1);

                //testing
                for(User pl: prioritiesList){
                    System.out.println("####>> User: "+pl.getName()+", > "+ pl.getTotalPriority());
                }
//                prioritiesList.clear();

                //Adding the chore
                String userID;
                if(mChorePriority == 10 || mChorePriority == 9) {
                    userID = userLowestPriorityTotal.getId();
                    Chore chore = new Chore(mChoreName.getText().toString(), mChorePriority, date.toString());//Creates the new chore with the values in the current activity.
                    System.out.println("Chore "+chore.getName()+" > "+chore.getPriority()+" added to: "+ userLowestPriorityTotal.getName());
                    new FirebaseDatabaseHelper().addChore(houseID, userID, chore, new FirebaseDatabaseHelper.DataStatus() {//TODO Always update the username in here to one that exists in order to add chores.
                        @Override
                        public void DataIsLoaded(List<User> users, String houseID) {

                        }

                        @Override
                        public void DataIsInserted() {
                            Toast.makeText(AddChoreActivity.this, "Chore successfully added!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void DataUpdated() {

                        }

                        @Override
                        public void DataDeleted() {

                        }
                    });
                }
                else{
                    userID = userHighestPriorityTotal.getId();
                    Chore chore = new Chore(mChoreName.getText().toString(), mChorePriority, date.toString());//Creates the new chore with the values in the current activity.
                    System.out.println("Chore "+chore.getName()+" > "+chore.getPriority()+" added to: "+ userHighestPriorityTotal.getName());
                    new FirebaseDatabaseHelper().addChore(houseID, userID, chore, new FirebaseDatabaseHelper.DataStatus() {//TODO Always update the username in here to one that exists in order to add chores.
                        @Override
                        public void DataIsLoaded(List<User> users, String houseID) {

                        }

                        @Override
                        public void DataIsInserted() {
                            Toast.makeText(AddChoreActivity.this, "Chore successfully added!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void DataUpdated() {

                        }

                        @Override
                        public void DataDeleted() {

                        }
                    });
                }

                finish();
                return;
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;//stop executing anything else
            }
        });
    }

    //method used to add and sort items in  an array list.
    public void addToList(ArrayList<User> list, User user, int num){
        int userPriority = user.getTotalPriority();
        boolean found = false;
        int position = 0;

        while (position < list.size() && !found) {
            list.get(position);

            if (list.get(position).getTotalPriority() < num) {
                position = position + 1;
            } else {
                found = true;
            }
        }

        if (position == list.size()) {
            list.add(user);
        } else {
            list.add(position, user);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String selection = adapterView.getItemAtPosition(i).toString();
        if(i==0){
            mChorePriority = 10;
        }else if(i==1){
            mChorePriority = 9;
        }else if(i==2){
            mChorePriority = 8;
        }else if(i==3){
            mChorePriority = 7;
        }else if(i==4){
            mChorePriority = 6;
        }else if(i==5){
            mChorePriority = 5;
        }
//        Toast.makeText(adapterView.getContext(),String.valueOf(mChorePriority),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}