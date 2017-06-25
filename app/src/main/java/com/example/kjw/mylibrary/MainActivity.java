package com.example.kjw.mylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.lang.String;

public class MainActivity extends AppCompatActivity {
    private ImageView myLibraryCallButton;
    private ImageView bookSearchButton;
    private ImageView loginButton;
    private ImageView hopeBookButton;
    private EditText keyword;
    private final int LOGIN_ACTIVITY = 0;
    private int permission = PermissionData.notUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("main", "start");

        myLibraryCallButton = (ImageView)findViewById(R.id.my_library_management_button);
        myLibraryCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyLibraryManagementActivity.class);
                //intent.putExtra("id", "201212345");
                //intent.putExtra("password", "password");
                startActivity(intent);
            }
        });

        bookSearchButton = (ImageView)findViewById(R.id.book_search);
        bookSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookSearchActivity.class);
                keyword = (EditText)findViewById(R.id.search_text);
                String search_keyword = keyword.getText().toString();
                intent.putExtra("search_keyword", search_keyword);
                //intent.putExtra("password", "password");
                startActivity(intent);
            }
        });

        loginButton = (ImageView)findViewById(R.id.login_confirm_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, LOGIN_ACTIVITY);
            }
        });

        hopeBookButton = (ImageView) findViewById(R.id.mainHopeBookBt);
        hopeBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HopeBookActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == LOGIN_ACTIVITY) {
                permission = data.getIntExtra("permission", PermissionData.notUser);
            }
        }
    }
}


