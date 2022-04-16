package com.example.mainproject.model.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mainproject.adapter.UserAdapter;
import com.example.mainproject.model.Customer;
import com.example.mainproject.model.User;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "db_test", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS customers (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, phone TEXT, address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS customers");
        onCreate(db);
    }


    // Insert User
    public void insertUser(User user) throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        try {
            if (user.getId() == -1)
                db.execSQL("INSERT INTO users (name, email) VALUES ('" + user.getName() + "', '" + user.getEmail() + "')");
            else
                db.execSQL("INSERT INTO users (id, name, email) VALUES (" + user.getId() + ", '" + user.getName() + "', '" + user.getEmail() + "')");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while inserting user");
        }
    }

    // Update User
    public void updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("UPDATE users SET name = '" + user.getName() + "', email = '" + user.getEmail() + "' WHERE id = " + user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating user");
        }
    }

    // Delete User
    public void deleteUser(int id) {
        SQLiteDatabase db = getWritableDatabase();
        if (getUser(id) != null) {
            System.out.println(getUser(id).getId());
            db.execSQL("DELETE FROM users WHERE id = " + id);
        } else {
            throw new RuntimeException("User with id " + id + " not found");
        }
    }

    // Get User
    public User getUser(int id) {
        SQLiteDatabase db = getReadableDatabase();
        User user = new User();
        String query = "SELECT * FROM users WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            return user;
        } else
            return null;
    }

    // Get next User ID
    public int getNextUserId() {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;
        String query = "SELECT MAX(id) FROM users";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        return id + 1;
    }

    // Get All Users
    public List<User> getAllUsers() {
        SQLiteDatabase db = getReadableDatabase();
        List<User> users = null;
        String query = "SELECT * FROM users";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            users = new ArrayList<>();
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                users.add(user);
            } while (cursor.moveToNext());
        }
        return users;
    }

    // Insert Customer
    public void insertCustomer(Customer customer) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            if (customer.getId() == -1)
                db.execSQL("INSERT INTO customers (name, email, phone, address) VALUES ('" + customer.getName() + "', '" + customer.getEmail() + "', '" + customer.getPhone() + "', '" + customer.getAddress() + "')");
            else
                db.execSQL("INSERT INTO customers (id, name, email, phone, address) VALUES (" + customer.getId() + ", '" + customer.getName() + "', '" + customer.getEmail() + "', '" + customer.getPhone() + "', '" + customer.getAddress() + "')");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while inserting customer");
        }
    }

    // Update Customer
    public void updateCustomer(Customer customer) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("UPDATE customers SET name = '" + customer.getName() + "', email = '" + customer.getEmail() + "', phone = '" + customer.getPhone() + "', address = '" + customer.getAddress() + "' WHERE id = " + customer.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating customer");
        }
    }

    // Delete Customer
    public void deleteCustomer(int id) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            if (getCustomer(id) != null)
                db.execSQL("DELETE FROM customers WHERE id = " + id);
            else
                throw new RuntimeException("Customer with id " + id + " not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while deleting customer");
        }
    }

    // Get next Customer ID
    public int getNextCustomerId() {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;
        String query = "SELECT MAX(id) FROM customers";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        return id + 1;
    }

    // Get Customer
    public Customer getCustomer(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Customer customer = new Customer();
        String query = "SELECT * FROM customers WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            customer.setId(cursor.getInt(0));
            customer.setName(cursor.getString(1));
            customer.setEmail(cursor.getString(2));
            customer.setPhone(cursor.getString(3));
            customer.setAddress(cursor.getString(4));
        }
        return customer;
    }

    // Get All Customers
    public List<Customer> getAllCustomers() {
        SQLiteDatabase db = getReadableDatabase();
        List<Customer> customers = null;
        String query = "SELECT * FROM customers";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            customers = new ArrayList<>();
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(0));
                customer.setName(cursor.getString(1));
                customer.setEmail(cursor.getString(2));
                customer.setPhone(cursor.getString(3));
                customer.setAddress(cursor.getString(4));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        return customers;
    }
}
