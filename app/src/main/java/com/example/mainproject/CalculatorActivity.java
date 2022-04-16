package com.example.mainproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CalculatorActivity extends AppCompatActivity {

    TextView txtCalc;
    Button btnClear, btnCalc, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        // Bind
        txtCalc = findViewById(R.id.txtCalc);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(view -> txtCalc.setText("0"));

        btnCalc = findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(view -> txtCalc.setText(String.valueOf(calculate(txtCalc.getText().toString()))));

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(view -> {
            int length = txtCalc.getText().toString().length();
            String afterDelete = txtCalc.getText().toString().substring(0, length - 1);
            txtCalc.setText(afterDelete);
        });
    }

    public void onClick_Append(View view) {
        Button btn = (Button) view;
        if (txtCalc.getText().toString().equals("0"))
            txtCalc.setText("");
        txtCalc.append(btn.getText().toString());
    }
    private static Double calculate(String input) {

//        Xử lý dấu ngoặc
        if (input.contains("(") && input.contains(")")) {
            int start = input.indexOf("(");
            int end = input.lastIndexOf(")");
            String subInput = input.substring(start + 1, end);
            Double result = calculate(subInput);
            input = input.replace("(" + subInput + ")", String.valueOf(result));
        }

//        Tách biểu thức
        StringTokenizer stringTokenizer = new StringTokenizer(input, "+-*/", true);
        List<String> tokens = new ArrayList<>();

//        Xử lý dấu đầu chuỗi
        if (input.charAt(0) == '-' || input.charAt(0) == '+') {
            tokens.add("0");
        }
        while (stringTokenizer.hasMoreTokens()) {
            tokens.add(stringTokenizer.nextToken());
        }
//        printString(tokens);

//        Xử lý dấu nhân, chia
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("*") || tokens.get(i).equals("/")) {
                double first = Double.parseDouble(tokens.get(i - 1));
                double second;
                if (tokens.get(i + 1).equals("-"))
                    second = Double.parseDouble(tokens.get(i + 2)) * -1;
                else
                    second = Double.parseDouble(tokens.get(i + 1));
                tokens.set(i - 1, calculate(first, second, tokens.get(i)));
                if (tokens.get(i + 1).equals("-"))
                    tokens.remove(i);
                tokens.remove(i);
                tokens.remove(i);
                i--;
//                printString(tokens);
            }
        }

//        Xử lý dấu cộng, trừ
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("+") || tokens.get(i).equals("-")) {
                double first = Double.parseDouble(tokens.get(i - 1));
                double second;
                if (tokens.get(i + 1).equals("-"))
                    second = Double.parseDouble(tokens.get(i + 2)) * -1;
                else
                    second = Double.parseDouble(tokens.get(i + 1));
                tokens.set(i - 1, calculate(first, second, tokens.get(i)));
                if (tokens.get(i + 1).equals("-"))
                    tokens.remove(i);
                tokens.remove(i);
                tokens.remove(i);
                i--;
//                printString(tokens);
            }
        }
        return Double.parseDouble(tokens.get(0));
    }

    //    Tính toán 2 số
    private static String calculate(double first, double second, String token) {
        switch (token) {
            case "+":
                return String.valueOf(first + second);
            case "-":
                return String.valueOf(first - second);
            case "*":
                return String.valueOf(first * second);
            case "/":
                return String.valueOf(first / second);
        }
        return String.valueOf(0);
    }
//
//    private static void printString(List<String> tokens) {
//        for (String token : tokens) {
//            System.out.print(token + " ");
//        }
//        System.out.println();
//    }
//
}