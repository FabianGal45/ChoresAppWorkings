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
    public void setChoresConfig(RecyclerView recyclerView, Context context, List<Chore> chores){
        mContext = context;
        mChoresAdapter = new ChoresAdapter(chores);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mChoresAdapter);
    }


    //The View holder for the recyclerview
    class ChoreItemView extends RecyclerView.ViewHolder{
        private CheckBox mCheckbox;
        private Chore chore;//holds the chore object

        public ChoreItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.chore_item, parent, false));
            mCheckbox = (CheckBox) itemView.findViewById(R.id.itemCheckBox);

            mCheckbox.setOnLongClickListener(new View.OnLongClickListener() {//Implements on long click listener to open am edit menu
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(mContext, "I am a chore!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            mCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("INTENT THING> "+chore.getName());
                    Intent intent = new Intent(mContext, UpdateChoreActivity.class);
                    intent.putExtra("name", chore.getName()); //these putExtra methods are sending over the following values into the updateChoreActivity to be displayed.
                    intent.putExtra("priority", Integer.toString(chore.getPriority()));
                    intent.putExtra("date", chore.getDate());
                    mContext.startActivity(intent);
                }
            });
        }

        //displays info into the row layout(chore_item.xml)
        public void bind(Chore chore){
            mCheckbox.setText(chore.getName());
            this.chore=chore;
        }

    }


    //The adapter class for the recyclerview
    class ChoresAdapter extends RecyclerView.Adapter<ChoreItemView>{
        private List<Chore> mChoresList;

        public ChoresAdapter(List<Chore> mChoresList) {
            this.mChoresList = mChoresList;
        }

        @NonNull
        @Override
        public ChoreItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ChoreItemView(parent);
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
