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

import com.example.mainproject.adapter.CustomerAdapter;
import com.example.mainproject.model.Customer;
import com.example.mainproject.model.helper.DbHelper;

import java.util.List;

public class CustomerManager extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText id, name, email, phone, address;
    DbHelper dbHelper = new DbHelper(this);
    CustomerAdapter customerAdapter;
    RecyclerView recyclerView;
    private final AdapterView.OnItemClickListener onItemClickListener = this;
    int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_manager);

        // Binding
        id = findViewById(R.id.txt_id_customer);
        name = findViewById(R.id.txt_name_customer);
        email = findViewById(R.id.txt_email_customer);
        phone = findViewById(R.id.txt_phone_customer);
        address = findViewById(R.id.txt_address_customer);

        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {
            int idCustomer = -1;
            try {
                idCustomer = Integer.parseInt(id.getText().toString());
                String nameStr = name.getText().toString();
                String emailStr = email.getText().toString();
                String phoneStr = phone.getText().toString();
                String addressStr = address.getText().toString();
                Customer customer = new Customer(idCustomer, nameStr, emailStr, phoneStr, addressStr);
                try {
                    dbHelper.insertCustomer(customer);
                    reloadData();
                    Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "id is not a number", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(v -> {
            int idCustomer = -1;
            try {
                idCustomer = Integer.parseInt(id.getText().toString());
                String nameStr = name.getText().toString();
                String emailStr = email.getText().toString();
                String phoneStr = phone.getText().toString();
                String addressStr = address.getText().toString();
                Customer customer = new Customer(idCustomer, nameStr, emailStr, phoneStr, addressStr);
                try {
                    dbHelper.updateCustomer(customer);
                    reloadData();
                    Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "id is not a number", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> {
            int idCustomer = -1;
            try {
                idCustomer = Integer.parseInt(id.getText().toString());
                try {
                    dbHelper.deleteCustomer(idCustomer);
                    reloadData();
                    Toast.makeText(this, "Delete success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "id is not a number", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        if (dbHelper.getAllCustomers() == null) {
            dbHelper.insertCustomer(new Customer(1, "customer1", "customer1@gmail.com", "0123456789", "address1"));
            dbHelper.insertCustomer(new Customer(2, "customer2", "customer2@gmail.com", "0123456789", "address2"));
            dbHelper.insertCustomer(new Customer(3, "customer3", "customer3@gmail.com", "0123456789", "address3"));
        }

        List<Customer> customers = dbHelper.getAllCustomers();
        recyclerView = findViewById(R.id.recyclerView);
        customerAdapter = new CustomerAdapter(customers, this, onItemClickListener);
        recyclerView.setAdapter(customerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        reloadData();
    }

    public void reloadData() {
        customerAdapter.reloadData(dbHelper);
        System.out.println("RELOAD DATA!!!");
        System.out.println("Next ID: " + dbHelper.getNextCustomerId());
        id.setText(String.valueOf(dbHelper.getNextCustomerId()));
        name.setText("");
        email.setText("");
        phone.setText("");
        address.setText("");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Customer customer = customerAdapter.getCustomer(i);
        id.setText(String.valueOf(customer.getId()));
        name.setText(customer.getName());
        email.setText(customer.getEmail());
        phone.setText(customer.getPhone());
        address.setText(customer.getAddress());

        currentPosition = i;
    }
}