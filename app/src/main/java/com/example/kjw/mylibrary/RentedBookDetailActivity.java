package com.example.kjw.mylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RentedBookDetailActivity extends AppCompatActivity {
    private String rentedBookTitle;
    private String rentedBookDueDate;
    private String rentedBookReturnPlace;

    private TextView rentedBookTitleTextView;
    private TextView rentedBookDueDateTextView;
    private TextView rentedBookReturnPlaceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rented_book_detail);
        Intent intent = getIntent();
        rentedBookTitle = intent.getStringExtra("title");
        rentedBookDueDate = intent.getStringExtra("dueDate");
        rentedBookReturnPlace = intent.getStringExtra("returnPlace");

        rentedBookTitleTextView = (TextView) findViewById(R.id.rented_book_title_text_view);
        rentedBookDueDateTextView = (TextView) findViewById(R.id.rented_book_due_date_text_view);
        rentedBookReturnPlaceTextView = (TextView) findViewById(R.id.rented_book_return_place_text_view);

        rentedBookTitleTextView.setText(rentedBookTitle);
        rentedBookDueDateTextView.setText(rentedBookDueDate);
        rentedBookReturnPlaceTextView.setText(rentedBookReturnPlace);
    }
}
