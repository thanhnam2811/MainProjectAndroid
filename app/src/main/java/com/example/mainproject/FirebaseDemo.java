package com.example.mainproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mainproject.adapter.ContactAdapter;
import com.example.mainproject.model.Contact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseDemo extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DatabaseReference mDatabase;
    private final String firebase_url = "https://mainproject-ec380-default-rtdb.asia-southeast1.firebasedatabase.app/";
    EditText id, name, phone;
    List<Contact> contacts;
    RecyclerView recyclerView;
    ContactAdapter adapter;
    private final AdapterView.OnItemClickListener onItemClickListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_demo);

        // Binding
        id = findViewById(R.id.txt_id);
        name = findViewById(R.id.txt_name);
        phone = findViewById(R.id.txt_phone);
        recyclerView = findViewById(R.id.rv_list);

        mDatabase = FirebaseDatabase.getInstance(firebase_url).getReference("contacts");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                GenericTypeIndicator<List<Contact>> t = new GenericTypeIndicator<List<Contact>>() {
                };
                contacts = dataSnapshot.getValue(t);
                contacts.removeIf(contact -> contact == null);

                adapter = new ContactAdapter(contacts, FirebaseDemo.this, onItemClickListener);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FirebaseDemo.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(v -> {
            String id_text = id.getText().toString();
            String name_text = name.getText().toString();
            String phone_text = phone.getText().toString();
            Contact contact = new Contact(Integer.parseInt(id_text), name_text, phone_text);
            mDatabase.child(id_text).setValue(contact);
            Toast.makeText(this, "Add Success", Toast.LENGTH_SHORT).show();
            resetText();
        });

        Button btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(v -> {
            int id_int = Integer.parseInt(id.getText().toString());
            mDatabase.child(String.valueOf(id_int)).removeValue();
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            resetText();
        });
    }

    public int getNextId(List<Contact> contacts) {
        int id = 0;
        for (Contact contact : contacts) {
            if (contact.getId() > id) {
                id = contact.getId();
            }
        }
        return id + 1;
    }

    public void resetText() {
        id.setText(String.valueOf(getNextId(contacts)));
        name.setText("");
        phone.setText("");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Contact contact = contacts.get(i);
        id.setText(String.valueOf(contact.getId()));
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
    }
}