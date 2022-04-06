package com.example.mydumbjavaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersRVConfig {
    private Context mContext;

    private ChoresRVConfig.ChoresAdapter mChoresAdapter;
    public void setUsersConfig(RecyclerView recyclerView, Context context, List<Chore> chores){
        mContext = context;
        mChoresAdapter = new ChoresRVConfig.ChoresAdapter(chores);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mChoresAdapter);//Sets the adapter
    }


    //The View holder for the recyclerview
    class ChoreItemView extends RecyclerView.ViewHolder{
        private CheckBox mCheckbox;

        public ChoreItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.chore_item, parent, false));
            mCheckbox = (CheckBox) itemView.findViewById(R.id.itemCheckBox);
        }

        //displays info into the row layout(chore_item.xml)
        public void bind(Chore chore){
            mCheckbox.setText(chore.getName());
        }
    }

    //The adapter class for the recyclerview
    class ChoresAdapter extends RecyclerView.Adapter<ChoresRVConfig.ChoreItemView>{
        //private Activity activity;
        private List<Chore> mChoresList;
        private List<User> mUserList;

        public ChoresAdapter(List<Chore> mChoresList) {
            this.mChoresList = mChoresList;
        }

        @NonNull
        @Override
        public ChoresRVConfig.ChoreItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ChoresRVConfig.ChoreItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ChoresRVConfig.ChoreItemView holder, int position) {
            holder.bind(mChoresList.get(position));//This will get the Chore object from the array and use the bind method to display it on the row layout(chore_item.xml)
        }

        @Override
        public int getItemCount() {
            return mChoresList.size();
        }
    }
}
