package com.example.kjw.mylibrary;

import android.content.DialogInterface;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.String;

public class MainActivity extends AppCompatActivity {
    private ImageView bookManagementCallButton;
    private ImageView myLibraryCallButton;
    private ImageView bookSearchButton;
    private ImageView loginButton;
    private ImageView hopeBookButton;

    private TextView loginButtonText;

    private ImageView groupstudyroomButton;
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

        bookManagementCallButton = (ImageView)findViewById(R.id.book_management_button);
        bookManagementCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookManagementActivity.class);
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

        loginButtonText = (TextView)findViewById(R.id.login_confirm_button_text);

        loginButton = (ImageView)findViewById(R.id.login_confirm_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginButtonText.getText().toString().equals("로그인")) {
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
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


        groupstudyroomButton = (ImageView)findViewById(R.id.groupstudyroom_button);
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
                setButtonImageStatus(myLibraryCallButton, false);

                bookManagementCallButton.setEnabled(false);
                setButtonImageStatus(bookManagementCallButton, false);

                bookSearchButton.setEnabled(true);
                setButtonImageStatus(bookSearchButton, true);

                loginButton.setEnabled(true);
                setButtonImageStatus(loginButton, true);

                hopeBookButton.setEnabled(false);
                setButtonImageStatus(hopeBookButton, false);
                break;
            case PermissionData.user:
                myLibraryCallButton.setEnabled(true);
                setButtonImageStatus(myLibraryCallButton, true);

                bookManagementCallButton.setEnabled(false);
                setButtonImageStatus(bookManagementCallButton, false);

                bookSearchButton.setEnabled(true);
                setButtonImageStatus(bookSearchButton, true);

                loginButton.setEnabled(true);
                setButtonImageStatus(loginButton, true);

                hopeBookButton.setEnabled(true);
                setButtonImageStatus(hopeBookButton, true);

                break;
            case PermissionData.admin:
                myLibraryCallButton.setEnabled(true);
                setButtonImageStatus(myLibraryCallButton, true);

                bookManagementCallButton.setEnabled(true);
                setButtonImageStatus(bookManagementCallButton, false);

                bookSearchButton.setEnabled(true);
                setButtonImageStatus(bookSearchButton, true);

                loginButton.setEnabled(true);
                setButtonImageStatus(loginButton, true);

                hopeBookButton.setEnabled(true);
                setButtonImageStatus(hopeBookButton, true);
                break;
            default:
                myLibraryCallButton.setEnabled(false);
                setButtonImageStatus(myLibraryCallButton, false);

                bookManagementCallButton.setEnabled(false);
                setButtonImageStatus(bookManagementCallButton, false);

                bookSearchButton.setEnabled(true);
                setButtonImageStatus(bookSearchButton, true);

                loginButton.setEnabled(true);
                setButtonImageStatus(loginButton, true);

                hopeBookButton.setEnabled(false);
                setButtonImageStatus(hopeBookButton, false);
        }
    }

    private void toggleLoginButton() {
        loginButtonText = (TextView)findViewById(R.id.login_confirm_button_text);
        switch (loginButtonText.getText().toString()) {
            case "로그인":
                loginButtonText.setText("로그아웃");
                break;
            case "로그아웃":
                loginButtonText.setText("로그인");
                break;
            default:
                break;
        }
    }

    private void setButtonImageStatus(ImageView buttonImage, boolean isEnable) {
        if (isEnable) {
            buttonImage.getDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        } else {
            buttonImage.getDrawable().clearColorFilter();
        }
    }
}


