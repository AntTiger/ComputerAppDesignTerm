package com.example.kjw.mylibrary;

import android.content.DialogInterface;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

    private Button groupstudyroomButton;
    private EditText keyword;
    private final int LOGIN_ACTIVITY = 0;
    private String id = "";
    private String password = "";
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
                intent.putExtra("id", id);
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
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        loginButton = (ImageView)findViewById(R.id.login_confirm_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginButton.getText().toString().equals("로그인")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, LOGIN_ACTIVITY);
                } else {
                    id = "";
                    password = "";
                    permission = PermissionData.notUser;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("로그아웃");
                    dialog.setMessage("로그아웃이 완료되었습니다.");
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                    setButtonStatus();
                    toggleLoginButton();
                }
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


        groupstudyroomButton = (Button)findViewById(R.id.groupstudyroom_button);
        groupstudyroomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GroupstudyroomActivity.class);
                startActivity(intent);
            }
        });
        setButtonStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == LOGIN_ACTIVITY) {
                id = data.getStringExtra("id");
                permission = data.getIntExtra("permission", PermissionData.notUser);
                setButtonStatus();
                toggleLoginButton();
            }
        }
    }

    private void setButtonStatus() {
        switch (permission) {
            case PermissionData.notUser:
                myLibraryCallButton.setEnabled(false);
                bookSearchButton.setEnabled(true);
                loginButton.setEnabled(true);
                break;
            case PermissionData.user:
                myLibraryCallButton.setEnabled(true);
                bookSearchButton.setEnabled(true);
                loginButton.setEnabled(true);
                break;
            case PermissionData.admin:
                myLibraryCallButton.setEnabled(true);
                bookSearchButton.setEnabled(true);
                loginButton.setEnabled(true);
                break;
            default:
                myLibraryCallButton.setEnabled(false);
                bookSearchButton.setEnabled(true);
                loginButton.setEnabled(true);
        }
    }

    private void toggleLoginButton() {
        loginButton = (Button)findViewById(R.id.login_confirm_button);
        switch (loginButton.getText().toString()) {
            case "로그인":
                loginButton.setText("로그아웃");
                break;
            case "로그아웃":
                loginButton.setText("로그인");
                break;
            default:
                break;
        }
    }
}


