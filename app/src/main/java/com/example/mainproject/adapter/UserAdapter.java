package com.example.mainproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.R;
import com.example.mainproject.model.User;
import com.example.mainproject.model.helper.DbHelper;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<User> users;
    Context context;
    private static AdapterView.OnItemClickListener onItemClickListener;

    public UserAdapter(List<User> users, Context context, AdapterView.OnItemClickListener onItemClickListener) {
        this.users = users;
        this.context = context;
        UserAdapter.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.txt_id_user.setText(String.valueOf(user.getId()));
        holder.txt_name_user.setText(user.getName());
        holder.txt_email_user.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        if (users != null)
            return users.size();
        return 0;
    }

    public void reloadData(DbHelper dbHelper) {
        users = dbHelper.getAllUsers();
        notifyDataSetChanged();
    }

    public User getUser(int childAdapterPosition) {
        return users.get(childAdapterPosition);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_id_user, txt_name_user, txt_email_user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id_user = itemView.findViewById(R.id.txt_id_user);
            txt_name_user = itemView.findViewById(R.id.txt_name_user);
            txt_email_user = itemView.findViewById(R.id.txt_email_user);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }

}
