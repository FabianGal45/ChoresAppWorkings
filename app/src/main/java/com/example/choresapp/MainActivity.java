package com.example.choresapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mUserRV;
    private RecyclerView mChoreRV;
    private FloatingActionButton mAddChoreFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserRV = (RecyclerView) findViewById(R.id.usersRecyclerView);
        new FirebaseDatabaseHelper().readData(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<User> users) {
                new UsersRVConfig().setUsersConfig(mUserRV, MainActivity.this, users);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataUpdated() {

            }

            @Override
            public void DataDeleted() {

            }
        });




        mAddChoreFAB = (FloatingActionButton) findViewById(R.id.addChoresFAB);

        mAddChoreFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddChoreActivity.class);
                startActivity(intent);
            }
        });

/*
        This runs when clicking the big button
        binding.bigButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.bigButton.setText("New items");
            }

        });

        Deletes any checkbox from the list that has been selected
        for(int i=0;i< myCheckBox.size();i++) {
            int finalI = i;
            myCheckBox.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myCheckBox.get(finalI).isChecked()) {
                        myCheckBox.get(finalI).setVisibility(View.GONE);
                    }
                }
            });
        }
*/

    }
}