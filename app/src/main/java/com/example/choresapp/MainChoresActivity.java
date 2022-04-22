package com.example.choresapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainChoresActivity extends AppCompatActivity {

    private RecyclerView mUserRV;
    private FloatingActionButton mAddChoreFAB;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chores);

        mUserRV = (RecyclerView) findViewById(R.id.usersRecyclerView);
        mAddChoreFAB = (FloatingActionButton) findViewById(R.id.addChoresFAB);
        new FirebaseDatabaseHelper().readData(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<User> users, String houseID) {
                new UsersRVConfig().setUsersConfig(mUserRV, MainChoresActivity.this, users, houseID);

                mAddChoreFAB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainChoresActivity.this, AddChoreActivity.class);
                        Bundle bundle = new Bundle();
                        intent.putExtra("houseID", houseID);
                        bundle.putParcelableArrayList("usersList", (ArrayList<? extends Parcelable>) users);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
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

    }
}