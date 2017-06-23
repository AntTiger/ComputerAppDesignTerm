package com.example.kjw.mylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReservedBookDetailActivity extends AppCompatActivity {
    private String reservedBookTitle;
    private String reservedBookReservationOrder;
    private String reservedBookExpectedRentAvailableDate;

    private TextView reservedBookTitleTextView;
    private TextView reservedBookReservationOrderTextView;
    private TextView reservedBookExpectedRentAvailableDateTextView;

    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_book_detail);
        Intent intent = getIntent();
        reservedBookTitle = intent.getStringExtra("title");
        reservedBookReservationOrder = intent.getStringExtra("reservationOrder");
        reservedBookExpectedRentAvailableDate = intent.getStringExtra("expectedRentAvailableDate");

        reservedBookTitleTextView = (TextView) findViewById(R.id.reserved_book_title_text_view);
        reservedBookReservationOrderTextView = (TextView) findViewById(R.id.reserved_book_reservation_order_text_view);
        reservedBookExpectedRentAvailableDateTextView = (TextView) findViewById(R.id.reserved_book_expected_rent_available_date_text_view);

        reservedBookTitleTextView.setText(reservedBookTitle);
        reservedBookReservationOrderTextView.setText(reservedBookReservationOrder);
        reservedBookExpectedRentAvailableDateTextView.setText(reservedBookExpectedRentAvailableDate);

        cancelButton = (Button)findViewById(R.id.reserved_book_item_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
