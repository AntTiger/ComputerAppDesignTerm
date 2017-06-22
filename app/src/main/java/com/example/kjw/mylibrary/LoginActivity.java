package com.example.kjw.mylibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText idText;
    private EditText passwordText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idText = (EditText)findViewById(R.id.id_text);
        passwordText = (EditText)findViewById(R.id.password_text);

        loginButton = (Button)findViewById(R.id.login_confirm_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO. ID랑 비밀번호를 받아서 서버에 요청한다.
                String userId = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

            }
        });
    }
}
