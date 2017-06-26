package com.example.kjw.mylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class StudyroomDetailActivity extends AppCompatActivity {

    private ArrayList<TextView> seat=new ArrayList<TextView>();

    private int Roomnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studyroom_detail);

        Intent intent = getIntent();
        Roomnum=intent.getIntExtra("sr",0);

        seat.add((TextView)findViewById(R.id.seat1));
        seat.add((TextView)findViewById(R.id.seat2));
        seat.add((TextView)findViewById(R.id.seat3));
        seat.add((TextView)findViewById(R.id.seat4));
        seat.add((TextView)findViewById(R.id.seat5));
        seat.add((TextView)findViewById(R.id.seat6));
        seat.add((TextView)findViewById(R.id.seat7));
        seat.add((TextView)findViewById(R.id.seat8));

        seat.add((TextView)findViewById(R.id.seat9));
        seat.add((TextView)findViewById(R.id.seat10));
        seat.add((TextView)findViewById(R.id.seat11));
        seat.add((TextView)findViewById(R.id.seat12));
        seat.add((TextView)findViewById(R.id.seat13));
        seat.add((TextView)findViewById(R.id.seat14));
        seat.add((TextView)findViewById(R.id.seat15));
        seat.add((TextView)findViewById(R.id.seat16));

        seat.add((TextView)findViewById(R.id.seat17));
        seat.add((TextView)findViewById(R.id.seat18));
        seat.add((TextView)findViewById(R.id.seat19));
        seat.add((TextView)findViewById(R.id.seat20));
        seat.add((TextView)findViewById(R.id.seat21));
        seat.add((TextView)findViewById(R.id.seat22));
        seat.add((TextView)findViewById(R.id.seat23));
        seat.add((TextView)findViewById(R.id.seat24));
    }
}
