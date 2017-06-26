package com.example.kjw.mylibrary;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private EditText idText;
    private EditText passwordText;
    private Button loginButton;

    private String mJsonString;
    private static final String TAG = "LoginActivity";
    private static final String TAG_JSON="userInfo";
    private static final String TAG_ID= "ID";
    private static final String TAG_PASSWORD = "PW";
    private static final String TAG_PERMISSION= "PM";

    private String inputUserId;
    private String inputUserPassword;

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
                inputUserId = idText.getText().toString();
                inputUserPassword = passwordText.getText().toString();
                HttpTest httpTest = new HttpTest();
                httpTest.execute(inputUserId, inputUserPassword);
            }
        });
    }

    public class HttpTest extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(LoginActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null){

            }
            else {
                mJsonString = result;
                tryLogin();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "start doInBackground");
            String serverURL = "http://" + ServerIpData.serverIp + "/login.php";
            //exe에 넘겨준거
            String idKeyword = (String)params[0];
            String passwordKeyword = (String)params[1];
            String postParameters = "id=" + idKeyword + "&password=" + passwordKeyword;
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                //php로 넘겨주는 부분
                OutputStream outputStream = httpURLConnection.getOutputStream();
                //입력한 데이터가 php 파일로 넘어간다.
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                //가져오는거
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                //실제 데이터가 오는 부분
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }

        }
    }

    private void tryLogin(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            if (jsonArray.length() == 0) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setTitle("icon_login 실패");
                dialog.setMessage("일치하는 정보가 없습니다.");
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            } else {
                JSONObject item = jsonArray.getJSONObject(0);
                String userId = item.getString(TAG_ID);
                String userPassword = item.getString(TAG_PASSWORD);
                int userPermission = item.getInt(TAG_PERMISSION);
                if (inputUserId.equals(userId) && inputUserPassword.equals(userPassword)) {
                    Intent intent = getIntent();
                    intent.putExtra("id", userId);
                    intent.putExtra("password", userPassword);
                    intent.putExtra("permission", userPermission);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }
}
