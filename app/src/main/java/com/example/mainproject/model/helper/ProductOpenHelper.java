package com.example.mainproject.model.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mainproject.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductOpenHelper extends SQLiteOpenHelper {

    public ProductOpenHelper(Context context) {
        super(context, "db_test", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }


    // Insert Product
    public void insertProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            if (product.getId() == -1)
                db.execSQL("INSERT INTO products (name, quantity) VALUES ('" + product.getName() + "', " + product.getQuantity() + ")");
            else
                db.execSQL("INSERT INTO products (id, name, quantity) VALUES (" + product.getId() + ", '" + product.getName() + "', " + product.getQuantity() + ")");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while inserting product");
        }
    }

    // Update Product
    public void updateProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("UPDATE products SET name = '" + product.getName() + "', quantity = " + product.getQuantity() + " WHERE id = " + product.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating product");
        }
    }

    // Delete Product
    public void deleteProduct(int id) {
        SQLiteDatabase db = getWritableDatabase();
        if (getProduct(id) != null) {
            System.out.println(getProduct(id).getId());
            db.execSQL("DELETE FROM products WHERE id = " + id);
        } else {
            throw new RuntimeException("Product with id " + id + " not found");
        }
    }

    // Get Product
    public Product getProduct(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Product product = new Product();
        String query = "SELECT * FROM products WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            product.setId(cursor.getInt(0));
            product.setName(cursor.getString(1));
            product.setQuantity(cursor.getInt(2));
            return product;
        } else
            return null;
    }

    // Get next Product ID
    public int getNextProductId() {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;
        String query = "SELECT MAX(id) FROM products";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        return id + 1;
    }

    // Get All Products
    public List<Product> getAllProducts() {
        SQLiteDatabase db = getReadableDatabase();
        List<Product> products = null;
        String query = "SELECT * FROM products";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            products = new ArrayList<>();
            do {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setName(cursor.getString(1));
                product.setQuantity(cursor.getInt(2));
                products.add(product);
            } while (cursor.moveToNext());
        }
        return products;
    }
}
