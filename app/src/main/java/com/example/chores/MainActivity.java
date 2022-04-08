package com.example.chores;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mUserRV;
    private RecyclerView mChoreRV;
//    private LocalDate date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        date = LocalDate.now();

        mUserRV = (RecyclerView) findViewById(R.id.usersRecyclerView);
        new FirebaseDatabaseHelper().readData(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<User> users) {
                new UsersRVConfig().setUsersConfig(mUserRV, MainActivity.this, users);
            }

            @Override
            public void DataInserter() {

            }

            @Override
            public void DataUpdated() {

            }

            @Override
            public void DataDeleted() {

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