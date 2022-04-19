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

import java.time.LocalDate;
import java.util.List;

public class UpdateChoreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button mUpdateBtn;
    private Button mCancelBtn;
    private EditText mChoreName;
    private int mChorePriority;
    private LocalDate newDate;

    private String oldName;
    private String oldPriority;
    private String oldDate;
    private String choreID;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chore);

        oldName = getIntent().getStringExtra("name");
        oldPriority = getIntent().getStringExtra("priority");
        oldDate = getIntent().getStringExtra("date");
        choreID = getIntent().getStringExtra("id");


        mChoreName = (EditText) findViewById(R.id.choreNameED);
        mChoreName.setText(oldName);
        newDate = LocalDate.now();

        mCancelBtn = (Button) findViewById(R.id.cancelBtn);
        mUpdateBtn = (Button) findViewById(R.id.updateChoreBtn);

        //Spinner setup
        Spinner prioritySpinner = findViewById(R.id.prioritySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.prioritySpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);
        prioritySpinner.setOnItemSelectedListener(this);
//        prioritySpinner.setSelection(getIndexSpinnerItem(prioritySpinner));

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoreWithID chore = new ChoreWithID();//TODO: complete tutorial from here
                chore.setDate(newDate.toString());
                chore.setName(mChoreName.toString());
                chore.setPriority(mChorePriority);
                chore.setId(choreID);

                new FirebaseDatabaseHelper().updateChore("Fabian", chore.getId(), chore, new FirebaseDatabaseHelper.DataStatus() {//TODO make sure to grab the actual values of the user and key.
                    @Override
                    public void DataIsLoaded(List<User> users) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataUpdated() {
                        Toast.makeText(UpdateChoreActivity.this, "Chore has been successfully updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataDeleted() {

                    }
                });
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();return;
            }
        });

    }



    private int getIndexSpinnerItem(Spinner spinner){
        int index = 0;
        for(int i=0; i<spinner.getCount(); i++){
            System.out.println("YYYY> Bottom: "+ spinner.getBottom()+", Baseline: "+spinner.getBaseline());
//            if(spinner.getItemAtPosition(i).equals(item)){
//                index = i;
//                break;
//            }
        }
        return index;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}