package com.example.kjw.mylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyLibraryManagementActivity extends AppCompatActivity {
    //TODO. intent에 권한을 넘기도록...
    private Button reservedBookButton;
    private Button rentedBookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library_management);

        reservedBookButton = (Button)findViewById(R.id.reserved_book_button);
        reservedBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLibraryManagementActivity.this, ReservedBookActivity.class);
                //intent.putExtra("id", "201212345");
                //intent.putExtra("password", "password");
                startActivity(intent);
            }
        });

        rentedBookButton = (Button)findViewById(R.id.rented_book_button);
        rentedBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLibraryManagementActivity.this, RentedBookActivity.class);
                //intent.putExtra("id", "201212345");
                //intent.putExtra("password", "password");
                startActivity(intent);
            }
        });

    }
}
