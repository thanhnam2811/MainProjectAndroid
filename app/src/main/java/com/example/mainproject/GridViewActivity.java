package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mainproject.adapter.ProgramingLanguageApdapter;
import com.example.mainproject.model.ProgramingLanguage;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        List<ProgramingLanguage> list = initData();

        GridView gridView = findViewById(R.id.gridView);

        gridView.setAdapter(new ProgramingLanguageApdapter(this, list, ProgramingLanguageApdapter.GRIDVIEW_TYPE));

        gridView.setOnItemClickListener((parent, view, position, id) -> {
                    ProgramingLanguage language = list.get(position);
                    Toast.makeText(this, "Clicked on: " + language.getName(), Toast.LENGTH_SHORT).show();
                }
        );
    }

    List<ProgramingLanguage> initData() {
        List<ProgramingLanguage> list = new ArrayList<>();

        ProgramingLanguage android = new ProgramingLanguage("Android", "Android is an open source and Linux-based operating system for mobile devices such as smartphones and tablets. It is based on the Linux kernel and includes a user interface based on the Android UI framework. Android is developed by the Open Handset Alliance (OHA).", "android");
        ProgramingLanguage ios = new ProgramingLanguage("iOS", "iOS is a mobile operating system (OS) for mobile devices such as smartphones and tablets. It is the operating system used on Apple's iPhone and iPad, and is the operating system of the Apple TV. iOS is developed by Apple Inc.", "ios");
        ProgramingLanguage java = new ProgramingLanguage("Java", "Java is a general-purpose computer programming language that is concurrent, class-based, object-oriented, and specifically designed to have as few implementation dependencies as possible. It is intended to let application developers \"write once, run anywhere\" (WORA), meaning that compiled Java code can run on all platforms that support Java without the need for recompilation.", "java");
        ProgramingLanguage php = new ProgramingLanguage("PHP", "PHP: Hypertext Preprocessor is a server-side scripting language designed for web development but also used as a general-purpose programming language. PHP code is usually processed on the server side by web servers, but is also used on the client side by HTML-embedded scripting languages such as JavaScript.", "php");

        list.add(android);
        list.add(ios);
        list.add(java);
        list.add(php);

        return list;
    }
}