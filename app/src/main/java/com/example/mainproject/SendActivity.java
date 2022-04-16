package com.example.mainproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SendActivity extends AppCompatActivity {

    public final static int REQUEST_CALLPHONE_CODE = 1;
    public final static String REQUEST_DATA = "REQUEST_DATA";
    public final static int _requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        // Hide the action bar
//        getSupportActionBar().hide();

        Button btnSendData = findViewById(R.id.btnCallActivity);
        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtDataSend = findViewById(R.id.txtDataSend);
                Intent intent = new Intent(SendActivity.this, ReceiveActivity.class);
                intent.putExtra(REQUEST_DATA, txtDataSend.getText().toString());
                startActivityForResult(intent, 1);
            }
        });

        Button btnCall = findViewById(R.id.btnCallPhone);
        btnCall.setOnClickListener(view -> {
            if (checkPermission(android.Manifest.permission.CALL_PHONE, REQUEST_CALLPHONE_CODE)) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:0981771024"));
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == _requestCode) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra(ReceiveActivity.RESPONSE_DATA);
                EditText editText = findViewById(R.id.txtDataReceive);
                editText.setText(result);
            }
        }
    }

    // Function to check and request permission
    public boolean checkPermission(String permission, int requestCode) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(SendActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(SendActivity.this, new String[]{permission}, REQUEST_CALLPHONE_CODE);
            return false;
        } else {
            Toast.makeText(SendActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    // This function is called when user accept or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALLPHONE_CODE) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Showing the toast message
                Toast.makeText(SendActivity.this, "Phone call Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SendActivity.this, "Phone call Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}