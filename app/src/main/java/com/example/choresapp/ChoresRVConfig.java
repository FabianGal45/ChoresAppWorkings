package com.example.choresapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChoresRVConfig {
    private Context mContext;

    private ChoresAdapter mChoresAdapter;
    public void setChoresConfig(RecyclerView recyclerView, Context context, List<ChoreWithID> chores, String houseID, String userID){
        mContext = context;
        mChoresAdapter = new ChoresAdapter(chores, houseID, userID);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mChoresAdapter);
    }


    //The View holder for the recyclerview
    class ChoreItemView extends RecyclerView.ViewHolder{
        private CheckBox mCheckbox;
        private ChoreWithID chore;//holds the chore object#

        public ChoreItemView(ViewGroup parent, String houseID, String userID) {
            super(LayoutInflater.from(mContext).inflate(R.layout.chore_item, parent, false));
            mCheckbox = (CheckBox) itemView.findViewById(R.id.itemCheckBox);

            mCheckbox.setOnLongClickListener(new View.OnLongClickListener() {//Implements on long click listener to open am edit menu
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(mContext, "I am a chore!", Toast.LENGTH_SHORT).show();
                    System.out.println("INTENT THING> "+chore.getName());
                    Intent intent = new Intent(mContext, UpdateChoreActivity.class);
                    intent.putExtra("name", chore.getName()); //these putExtra methods are sending over the following values into the updateChoreActivity to be displayed.
                    intent.putExtra("priority", Integer.toString(chore.getPriority()));
                    intent.putExtra("date", chore.getDate());
                    intent.putExtra("id", chore.getId());
                    intent.putExtra("houseID", houseID);
                    intent.putExtra("userID", userID);
                    mContext.startActivity(intent);
                    return false;
                }
            });

            mCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCheckbox.isChecked()){
                        Toast.makeText(mContext, "I should have been deleted now", Toast.LENGTH_SHORT).show();
                        new FirebaseDatabaseHelper().deleteChore(houseID, userID, chore.getId(), new FirebaseDatabaseHelper.DataStatus() {//TODO: make sure the user and key are correct
                            @Override
                            public void DataIsLoaded(List<User> users, String houseID) {

                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataUpdated() {

                            }

                            @Override
                            public void DataDeleted() {
                                Toast.makeText(mContext, "Chore successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

        //displays info into the row layout(chore_item.xml)
        public void bind(ChoreWithID chore){
            mCheckbox.setText(chore.getName());
            this.chore=chore;
        }

    }


    //The adapter class for the recyclerview
    class ChoresAdapter extends RecyclerView.Adapter<ChoreItemView>{
        private List<ChoreWithID> mChoresList;
        private String houseID;
        private String userID;

        public ChoresAdapter(List<ChoreWithID> mChoresList, String houseID, String userID) {
            this.mChoresList = mChoresList;
            this.houseID = houseID;
            this.userID = userID;
        }

        @NonNull
        @Override
        public ChoreItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ChoreItemView(parent, houseID, userID);
        }

        @Override
        public void onBindViewHolder(@NonNull ChoreItemView holder, int position) {
            holder.bind(mChoresList.get(position));//This will get the Chore object from the array and use the bind method to display it on the row layout(chore_item.xml)
        }

        @Override
        public int getItemCount() {
            return mChoresList.size();
        }
    }

}
