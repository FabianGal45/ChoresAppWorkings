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
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.List;

public class AddChore extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button mAddBtn;
    private Button mCancelBtn;
    private EditText mChoreName;
    private int mChorePriority;
    private LocalDate date;

    public void test(List<User> users){

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);

        mChoreName = (EditText) findViewById(R.id.choreNameED);
        date = LocalDate.now();

        mCancelBtn = (Button) findViewById(R.id.cancelBtn);
        mAddBtn = (Button) findViewById(R.id.addChoreBtn);

        //Spinner setup
        Spinner prioritySpinner = findViewById(R.id.prioritySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.prioritySpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);
        prioritySpinner.setOnItemSelectedListener(this);

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("TESTING>> "+mChoreName);
//                Toast.makeText(view.getContext(),String.valueOf(mChoreName)+ " was set!!",Toast.LENGTH_SHORT).show();
                Chore chore = new Chore(mChoreName.getText().toString(),mChorePriority,date.toString());
                new FirebaseDatabaseHelper().setChore("Fabian", chore);
                finish();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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