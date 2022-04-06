package com.example.mydumbjavaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewConfig {
    private Context mContext;

    private ChoresAdapter mChoresAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Chore> chores, List<String> users){
        mContext = context;
        mChoresAdapter = new ChoresAdapter(chores, users);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mChoresAdapter);
    }

    class ChoreItemView extends RecyclerView.ViewHolder{
        private CheckBox mCheckbox;
        private TextView mTextView;

        public ChoreItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.chore_item, parent, false));

            mCheckbox = (CheckBox) itemView.findViewById(R.id.itemCheckBox);
            mTextView = (TextView) itemView.findViewById(R.id.userTV);

        }

        public void bind(Chore chore, String user){
            mCheckbox.setText(chore.getName());
            mTextView.setText(user);
        }
    }

    class ChoresAdapter extends RecyclerView.Adapter<ChoreItemView>{
        private List<Chore> mChoresList;
        private List<String> mUsers;

        public ChoresAdapter(List<Chore> mChoresList, List<String> mUsers) {
            this.mChoresList = mChoresList;
            this.mUsers = mUsers;
        }

        @NonNull
        @Override
        public ChoreItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ChoreItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ChoreItemView holder, int position) {
            holder.bind(mChoresList.get(position), mUsers.get(position));
            System.out.println("{}{}{} "+position);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }


}
