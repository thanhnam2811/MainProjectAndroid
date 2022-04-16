package com.example.mainproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ReceiveActivity extends AppCompatActivity {
    public final static String RESPONSE_DATA = "RESPONSE_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        // Hide the action bar
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String txtReceived = intent.getStringExtra(SendActivity.REQUEST_DATA);
        EditText editText = findViewById(R.id.txtDataReceived);
        editText.setText(txtReceived);

        Button btnSendBack = findViewById(R.id.btnSendBack);
        btnSendBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(ReceiveActivity.this, SendActivity.class);
                EditText editText = findViewById(R.id.txtDataSendBack);
                String txtData = editText.getText().toString();
                send.putExtra(RESPONSE_DATA, txtData);
                setResult(RESULT_OK, send);
                finish();
            }
        });
    }
}