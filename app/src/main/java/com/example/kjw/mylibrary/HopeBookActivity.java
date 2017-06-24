package com.example.kjw.mylibrary;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class HopeBookActivity extends AppCompatActivity {

    final int FLAG_POPUP = 51;
    final int FLAG_DIRECT = 52;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("희망도서 신청");
        setContentView(R.layout.activity_hope_book);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.hope_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_hopeBookRegisterIcon:
                Intent intent = new Intent(this, hopeBookPopupActivity.class);
                startActivityForResult(intent, FLAG_POPUP);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FLAG_POPUP) {
                String result = data.getStringExtra("result");
                if (result.equals("direct")) {
                    Toast.makeText(this, "직접신청 선택", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HopeBook_DirectActivity.class);
                    startActivityForResult(intent, FLAG_DIRECT);
                } else if (result.equals("search")) {
                    Toast.makeText(this, "검색후 신청 선택", Toast.LENGTH_SHORT).show();
                }
            }
        }else if(requestCode == FLAG_DIRECT){

        }
    }
}
