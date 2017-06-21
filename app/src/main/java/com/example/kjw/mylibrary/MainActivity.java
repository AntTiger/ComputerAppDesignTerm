package com.example.kjw.mylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.lang.String;

public class MainActivity extends AppCompatActivity {
    private Button myLibraryCallButton;
    private Button bookSearchButton;
    private EditText keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLibraryCallButton = (Button)findViewById(R.id.my_library_button);
        myLibraryCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyLibraryActivity.class);
                //intent.putExtra("id", "201212345");
                //intent.putExtra("password", "password");
                startActivity(intent);
            }
        });

        bookSearchButton = (Button)findViewById(R.id.book_search);
        bookSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookSearch.class);
                keyword = (EditText)findViewById(R.id.search_text);
                String search_keyword = keyword.getText().toString();
                intent.putExtra("search_keyword", search_keyword);
                //intent.putExtra("password", "password");
                startActivity(intent);
            }
        });
    }
}


