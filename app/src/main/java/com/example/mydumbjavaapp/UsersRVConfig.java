package com.example.mydumbjavaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersRVConfig {
    private Context mContext;

    private UserAdapter mUserAdapter;
    public void setUsersConfig(RecyclerView recyclerView, Context context, List<User> users){
        mContext = context;
        mUserAdapter = new UserAdapter(users);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mUserAdapter);
    }


    //The View holder for the recyclerview
    class UserItemView extends RecyclerView.ViewHolder{
        private TextView mUserName;
        private RecyclerView choresRV;

        public UserItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false));
            mUserName = (TextView) itemView.findViewById(R.id.userNameTV);
            choresRV = (RecyclerView) itemView.findViewById(R.id.choresRV);
        }

        //displays info into the row layout(chore_item.xml)
        public void bind(User user){
            mUserName.setText(user.getName());

            //Calls upon the chores rv config to print data
            ChoresRVConfig mChoresRVConfig = new ChoresRVConfig();
            mChoresRVConfig.setChoresConfig(choresRV,mContext, user.getChoreList());
        }
    }

    //The adapter class for the recyclerview
    class UserAdapter extends RecyclerView.Adapter<UserItemView> {
        private List<User> mUserList;

        public UserAdapter(List<User> mUserList) {
            this.mUserList = mUserList;
        }

        @NonNull
        @Override
        public UserItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull UserItemView holder, int position) {
            holder.bind(mUserList.get(position));
        }

        @Override
        public int getItemCount() {
            return mUserList.size();
        }
    }
}
