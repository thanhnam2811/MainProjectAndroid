package com.example.mainproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    public static String EXTRA_MESSAGE = "extra_message";
    EditText txtUsername, txtPassword;
    public static String EXTRA_USERNAME = "username";
    public static String EXTRA_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
    }

    public void onClick_btnOK(View view) {
        if (txtUsername.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty())
            Toast.makeText(this, "Username và Password không được để trống!", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            intent.putExtra(EXTRA_USERNAME, txtUsername.getText().toString());
            intent.putExtra(EXTRA_PASSWORD, txtPassword.getText().toString());
            intent.putExtra(EXTRA_MESSAGE, "Login Successful!");
            startActivity(intent);
        }
    }

    public void onClick_btnCancel(View view) {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public void onClick_btnForgetPassword(View view) {
        if (txtUsername.getText().toString().isEmpty())
            Toast.makeText(this, "Nhập Username để thực hiện chức năng này!", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.com/search?q=Quên mật khẩu"));
            startActivity(intent);
        }
    }
}