package com.example.mydumbjavaapp;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LocalDate date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = LocalDate.now();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewChores);
        new FirebaseDatabaseHelper().readChores(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Chore> chores, List<String> keys) {
                new RecyclerViewConfig().setConfig(mRecyclerView, MainActivity.this, chores, keys);
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