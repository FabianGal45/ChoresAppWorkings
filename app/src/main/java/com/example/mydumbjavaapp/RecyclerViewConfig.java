package com.example.mydumbjavaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewConfig {
    private Context mContext;

    private ChoresAdapter mChoresAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Chore> chores, List<String> keys){
        mContext = context;
        mChoresAdapter = new ChoresAdapter(chores, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mChoresAdapter);
    }

    class ChoreItemView extends RecyclerView.ViewHolder{
        private CheckBox mCheckbox;

        private String key;

        public ChoreItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.chore_item, parent, false));

            mCheckbox = (CheckBox) itemView.findViewById(R.id.itemCheckBox);

        }

        public void bind(Chore chore, String key){
            mCheckbox.setText(chore.getName());
            this.key = key;
        }
    }

    class ChoresAdapter extends RecyclerView.Adapter<ChoreItemView>{
        private List<Chore> mChoresList;
        private List<String> mKeysList;

        public ChoresAdapter(List<Chore> mChoresList, List<String> mKeysList) {
            this.mChoresList = mChoresList;
            this.mKeysList = mKeysList;
        }

        @NonNull
        @Override
        public ChoreItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ChoreItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ChoreItemView holder, int position) {
            holder.bind(mChoresList.get(position), mKeysList.get(position));
        }

        @Override
        public int getItemCount() {
            return mChoresList.size();
        }
    }


}
