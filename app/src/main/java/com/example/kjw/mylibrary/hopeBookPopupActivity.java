package com.example.kjw.mylibrary;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class hopeBookPopupActivity extends AppCompatActivity {
    EditText editText;
    ImageView imageView;
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
        editText = (EditText) findViewById(R.id.hopeSearchKeyword);
        imageView = (ImageView)findViewById(R.id.hopeSearchBt);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().length() == 0){
                    Toast.makeText(hopeBookPopupActivity.this, "icon_search 키워드를 입력해 주세요", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(hopeBookPopupActivity.this,HopeSearchActivity.class);
                    intent.putExtra("keyword",editText.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
