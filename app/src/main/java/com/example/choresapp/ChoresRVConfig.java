package com.example.choresapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

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
