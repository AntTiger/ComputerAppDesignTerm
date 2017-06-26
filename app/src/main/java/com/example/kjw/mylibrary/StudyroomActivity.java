package com.example.kjw.mylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudyroomActivity extends AppCompatActivity {

    private Button sr1Button;
    private Button sr2Button;
    private Button sr3Button;
    private Button sr4Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studyroom);
        sr1Button = (Button)findViewById(R.id.sr1_button);
        sr1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyroomActivity.this, StudyroomDetailActivity.class);
                intent.putExtra("sr",1);
                startActivity(intent);
            }
        });
        sr2Button = (Button)findViewById(R.id.sr2_button);
        sr2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyroomActivity.this, StudyroomDetailActivity.class);
                intent.putExtra("sr",2);
                startActivity(intent);
            }
        });
        sr3Button = (Button)findViewById(R.id.sr3_button);
        sr3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyroomActivity.this, StudyroomDetailActivity.class);
                intent.putExtra("sr",3);
                startActivity(intent);
            }
        });
        sr4Button = (Button)findViewById(R.id.sr4_button);
        sr4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyroomActivity.this, StudyroomDetailActivity.class);
                intent.putExtra("sr",4);
                startActivity(intent);
            }
        });
    }
}
