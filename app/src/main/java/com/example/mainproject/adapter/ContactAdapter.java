package com.example.mainproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.FirebaseDemo;
import com.example.mainproject.R;
import com.example.mainproject.SendActivity;
import com.example.mainproject.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private static final int REQUEST_CALLPHONE_CODE = 1;
    List<Contact> contacts;
    Context context;
    private static AdapterView.OnItemClickListener onItemClickListener;

    public ContactAdapter(List<Contact> contacts, Context context, AdapterView.OnItemClickListener onItemClickListener) {
        this.contacts = contacts;
        this.context = context;
        ContactAdapter.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.firebase_demo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.txt_id.setText(String.valueOf(contact.getId()));
        holder.txt_name.setText(contact.getName());
        holder.txt_phone.setText(contact.getPhone());
        holder.btn_call.setOnClickListener(view -> {
            // Call txt_phone
            if (PermissionChecker.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) == PermissionChecker.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contact.getPhone()));
                context.startActivity(intent);
            } else {
                ActivityCompat.requestPermissions((FirebaseDemo) context, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALLPHONE_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (contacts != null)
            return contacts.size();
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_id, txt_name, txt_phone;
        Button btn_call;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_phone = itemView.findViewById(R.id.txt_phone);
            txt_id = itemView.findViewById(R.id.txt_id);
            btn_call = itemView.findViewById(R.id.btn_call);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }

}
