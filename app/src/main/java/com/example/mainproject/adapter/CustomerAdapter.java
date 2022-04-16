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
import com.example.mainproject.model.Customer;
import com.example.mainproject.model.helper.DbHelper;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    List<Customer> customers;
    Context context;
    private static AdapterView.OnItemClickListener onItemClickListener;

    public CustomerAdapter(List<Customer> customers, Context context, AdapterView.OnItemClickListener onItemClickListener) {
        this.customers = customers;
        this.context = context;
        CustomerAdapter.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.txt_id_customer.setText(String.valueOf(customer.getId()));
        holder.txt_name_customer.setText(customer.getName());
        holder.txt_email_customer.setText(customer.getEmail());
        holder.txt_phone_customer.setText(customer.getPhone());
        holder.txt_address_customer.setText(customer.getAddress());
    }

    @Override
    public int getItemCount() {
        if (customers != null)
            return customers.size();
        return 0;
    }

    public void reloadData(DbHelper dbHelper) {
        customers = dbHelper.getAllCustomers();
        notifyDataSetChanged();
    }

    public Customer getCustomer(int childAdapterPosition) {
        return customers.get(childAdapterPosition);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_id_customer, txt_name_customer, txt_email_customer, txt_phone_customer, txt_address_customer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id_customer = itemView.findViewById(R.id.txt_id_customer);
            txt_name_customer = itemView.findViewById(R.id.txt_name_customer);
            txt_email_customer = itemView.findViewById(R.id.txt_email_customer);
            txt_phone_customer = itemView.findViewById(R.id.txt_phone_customer);
            txt_address_customer = itemView.findViewById(R.id.txt_address_customer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }

}
