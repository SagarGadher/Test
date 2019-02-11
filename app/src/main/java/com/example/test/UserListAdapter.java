package com.example.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder> {
    private Context context;
    private List<UserListItem> mUserItemList;

    public UserListAdapter(Context context, List<UserListItem> mUserItemList) {
        this.context = context;
        this.mUserItemList = mUserItemList;
    }

    @NonNull
    @Override
    public UserListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_list_item, viewGroup,false);
        return new UserListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListItemViewHolder userListItemViewHolder, int i) {
        String f_name, l_name, email, phone_number;
        UserListItem currentUserItem = mUserItemList.get(i);
        f_name = currentUserItem.getFirstName();
        l_name = currentUserItem.getLastName();
        email = currentUserItem.getEmail();
        phone_number = currentUserItem.getPhoneNumber();

        userListItemViewHolder.tvFirstName.setText(f_name);
        userListItemViewHolder.tvLastName.setText(l_name);
        userListItemViewHolder.tvEmail.setText(email);
        userListItemViewHolder.tvPhoneNumber.setText(phone_number);


    }

    @Override
    public int getItemCount() {
        return mUserItemList.size();
    }

    public class UserListItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvFirstName, tvLastName, tvEmail, tvPhoneNumber;

        public UserListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            tvLastName = itemView.findViewById(R.id.tvLastName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
        }
    }
    public void updateList(List<UserListItem> newItemList){
        mUserItemList = new ArrayList<>();
        mUserItemList.addAll(newItemList);
        notifyDataSetChanged();
    }
}
