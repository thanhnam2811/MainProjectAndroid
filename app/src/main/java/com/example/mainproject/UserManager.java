package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mainproject.adapter.UserAdapter;
import com.example.mainproject.model.User;
import com.example.mainproject.model.helper.DbHelper;

import java.util.List;

public class UserManager extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText id, name, email;
    DbHelper dbHelper = new DbHelper(this);
    UserAdapter userAdapter;
    RecyclerView recyclerView;
    private final AdapterView.OnItemClickListener onItemClickListener = this;
    int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        // Binding
        id = findViewById(R.id.txt_id_user);
        name = findViewById(R.id.txt_name_user);
        email = findViewById(R.id.txt_email_user);

        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {
            int idUser = -1;
            try {
                idUser = Integer.parseInt(id.getText().toString());
                String nameStr = name.getText().toString();
                String emailStr = email.getText().toString();
                User user = new User(idUser, nameStr, emailStr);
                try {
                    dbHelper.insertUser(user);
                    reloadData();
                    Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Id is not a number", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(v -> {
            int idUser = -1;
            try {
                idUser = Integer.parseInt(id.getText().toString());
                String nameStr = name.getText().toString();
                String emailStr = email.getText().toString();
                User user = new User(idUser, nameStr, emailStr);
                try {
                    dbHelper.updateUser(user);
                    reloadData();
                    Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Id is not a number", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> {
            int idUser = -1;
            try {
                idUser = Integer.parseInt(id.getText().toString());
                try {
                    dbHelper.deleteUser(idUser);
                    reloadData();
                    Toast.makeText(this, "Delete success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Id is not a number", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() throws Exception {
        if (dbHelper.getAllUsers() == null) {
            dbHelper.insertUser(new User(1, "user1", "user1@gmail.com"));
            dbHelper.insertUser(new User(2, "user2", "user2@gmail.com"));
            dbHelper.insertUser(new User(3, "user3", "user3@gmail.com"));
            dbHelper.insertUser(new User(4, "user4", "user4@gmail.com"));
            dbHelper.insertUser(new User(5, "user5", "user5@gmail.com"));
            dbHelper.insertUser(new User(6, "user6", "user6@gmail.com"));
        }

        List<User> users = dbHelper.getAllUsers();
        recyclerView = findViewById(R.id.recyclerView);
        userAdapter = new UserAdapter(users, this, onItemClickListener);
        recyclerView.setAdapter(userAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        reloadData();
    }

    public void reloadData() {
        userAdapter.reloadData(dbHelper);
        System.out.println("RELOAD DATA!!!");
        System.out.println("Next ID: " + dbHelper.getNextUserId());
        id.setText(String.valueOf(dbHelper.getNextUserId()));
        name.setText("");
        email.setText("");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        User user = userAdapter.getUser(i);
        id.setText(String.valueOf(user.getId()));
        name.setText(user.getName());
        email.setText(user.getEmail());

        currentPosition = i;
    }
}