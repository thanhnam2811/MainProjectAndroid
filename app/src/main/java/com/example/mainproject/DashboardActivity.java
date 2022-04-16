package com.example.mainproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {
    TextView userName, password, msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_dashboard);

        // Bind
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        msg = findViewById(R.id.msg);

        Intent intent = getIntent();
        String _msg = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

        // Get the data from the intent
        userName.setText("Username: " + intent.getStringExtra(LoginActivity.EXTRA_USERNAME));
        password.setText("Password: " + intent.getStringExtra(LoginActivity.EXTRA_PASSWORD));
        msg.setText("Message: " + _msg);
    }
}