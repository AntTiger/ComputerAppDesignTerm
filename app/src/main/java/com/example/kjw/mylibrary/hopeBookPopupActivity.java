package com.example.kjw.mylibrary;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class hopeBookPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_hope_book_popup);

        TextView directText = (TextView) findViewById(R.id.popupBtDirectRegister);
        directText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result","direct");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        TextView searchText = (TextView) findViewById(R.id.popupBtSearchRegister);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result","search");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        TextView popupTitle = (TextView) findViewById(R.id.popUpTitle);
        popupTitle.setBackgroundColor(Color.parseColor("#2337DE"));
    }
}
