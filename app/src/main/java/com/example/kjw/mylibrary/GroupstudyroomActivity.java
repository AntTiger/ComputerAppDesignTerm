package com.example.kjw.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by LHS on 2017-06-24.
 */

public class GroupstudyroomActivity extends AppCompatActivity{
    private Button gsr31Button;
    private Button gsr32Button;
    private Button gsr33Button;
    private Button gsr41Button;
    private Button gsr42Button;
    private Button gsr43Button;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupstudyroom);

        gsr31Button = (Button)findViewById(R.id.gsr31_button);
        gsr31Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupstudyroomActivity.this, ReservedGsrActivity.class);
                intent.putExtra("room",31);
                startActivity(intent);
            }
        });
        gsr32Button = (Button)findViewById(R.id.gsr32_button);
        gsr32Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupstudyroomActivity.this, ReservedGsrActivity.class);
                intent.putExtra("room",32);
                startActivity(intent);
            }
        });
        gsr33Button = (Button)findViewById(R.id.gsr33_button);
        gsr33Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupstudyroomActivity.this, ReservedGsrActivity.class);
                intent.putExtra("room",33);
                startActivity(intent);
            }
        });
        gsr41Button = (Button)findViewById(R.id.gsr41_button);
        gsr41Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupstudyroomActivity.this, ReservedGsrActivity.class);
                intent.putExtra("room",41);
                startActivity(intent);
            }
        });
        gsr42Button = (Button)findViewById(R.id.gsr42_button);
        gsr42Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupstudyroomActivity.this, ReservedGsrActivity.class);
                intent.putExtra("room",42);
                startActivity(intent);
            }
        });
        gsr43Button = (Button)findViewById(R.id.gsr43_button);
        gsr43Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupstudyroomActivity.this, ReservedGsrActivity.class);
                intent.putExtra("room",43);
                startActivity(intent);
            }
        });
    }
}
