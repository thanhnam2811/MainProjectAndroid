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

import com.example.mainproject.adapter.ProductAdapter;
import com.example.mainproject.model.Product;
import com.example.mainproject.model.helper.ProductOpenHelper;

import java.util.List;

public class ProductManager extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText id, name, quantity;
    ProductOpenHelper dbHelper = new ProductOpenHelper(this);
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    private final AdapterView.OnItemClickListener onItemClickListener = this;
    int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manager);

        // Binding
        id = findViewById(R.id.txt_id_product);
        name = findViewById(R.id.txt_name_product);
        quantity = findViewById(R.id.txt_quantity_product);

        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {
            int idProduct = -1;
            try {
                idProduct = Integer.parseInt(id.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("idProduct is not a number");
            }
            String nameStr = name.getText().toString();
            int quantityProduct = 0;
            try {
                quantityProduct = Integer.parseInt(quantity.getText().toString());
                Product product = new Product(idProduct, nameStr, quantityProduct);
                try {
                    dbHelper.insertProduct(product);
                    reloadData();
                    Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Quantity is not a number", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(v -> {
            int idProduct = -1;
            try {
                idProduct = Integer.parseInt(id.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("idProduct is not a number");
            }
            String nameStr = name.getText().toString();
            int quantityProduct = 0;
            try {
                quantityProduct = Integer.parseInt(quantity.getText().toString());
                Product product = new Product(idProduct, nameStr, quantityProduct);
                try {
                    dbHelper.updateProduct(product);
                    reloadData();
                    Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Quantity is not a number", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> {
            int idProduct = -1;
            try {
                idProduct = Integer.parseInt(id.getText().toString());
                try {
                    dbHelper.deleteProduct(idProduct);
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

    private void initData() {
        if (dbHelper.getAllProducts() == null) {
            dbHelper.insertProduct(new Product(1, "product1", 10));
            dbHelper.insertProduct(new Product(2, "product2", 10));
            dbHelper.insertProduct(new Product(3, "product3", 10));
            dbHelper.insertProduct(new Product(4, "product4", 10));
        }

        List<Product> products = dbHelper.getAllProducts();
        recyclerView = findViewById(R.id.recyclerView);
        productAdapter = new ProductAdapter(products, this, onItemClickListener);
        recyclerView.setAdapter(productAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        reloadData();
    }

    public void reloadData() {
        productAdapter.reloadData(dbHelper);
        System.out.println("RELOAD DATA!!!");
        System.out.println("Next ID: " + dbHelper.getNextProductId());
        id.setText(String.valueOf(dbHelper.getNextProductId()));
        name.setText("");
        quantity.setText("");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Product product = productAdapter.getProduct(i);
        id.setText(String.valueOf(product.getId()));
        name.setText(product.getName());
        quantity.setText(String.valueOf(product.getQuantity()));

        currentPosition = i;
    }
}