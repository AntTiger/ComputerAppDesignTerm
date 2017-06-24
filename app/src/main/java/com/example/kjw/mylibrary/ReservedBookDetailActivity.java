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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class ReservedBookDetailActivity extends AppCompatActivity {
    private String userId;
    private String assignedNumber;
    private String reservedBookTitle;
    private String reservedBookReservationOrder;
    private String reservedBookExpectedRentAvailableDate;

    private TextView reservedBookTitleTextView;
    private TextView reservedBookReservationOrderTextView;
    private TextView reservedBookExpectedRentAvailableDateTextView;

    private Button cancelButton;

    private String mJsonString;
    private static final String TAG = "ReservedBookDetail";
    private static final String TAG_JSON = "ReservedBookDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_book_detail);
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        assignedNumber = intent.getStringExtra("assignedNumber");
        reservedBookTitle = intent.getStringExtra("title");
        reservedBookReservationOrder = intent.getStringExtra("reservationOrder");
        reservedBookExpectedRentAvailableDate = intent.getStringExtra("expectedRentAvailableDate");
        reservedBookTitleTextView = (TextView) findViewById(R.id.reserved_book_title_text_view);
        reservedBookReservationOrderTextView = (TextView) findViewById(R.id.reserved_book_reservation_order_text_view);
        reservedBookExpectedRentAvailableDateTextView = (TextView) findViewById(R.id.reserved_book_expected_rent_available_date_text_view);

        reservedBookTitleTextView.setText(reservedBookTitle);
        reservedBookReservationOrderTextView.setText(reservedBookReservationOrder);
        reservedBookExpectedRentAvailableDateTextView.setText(reservedBookExpectedRentAvailableDate);

        cancelButton = (Button)findViewById(R.id.reserved_book_item_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpTest httpTest = new HttpTest();
                httpTest.execute(userId, assignedNumber);
            }
        });
    }

    public class HttpTest extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReservedBookDetailActivity.this, "Please Wait", null, true, true);
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
                showCancelDialog();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "start doInBackground");
            String serverURL = "http://110.46.227.154/cancel_reservation.php";
            //exe에 넘겨준거
            String idKeyword = (String)params[0];
            String assignedNumberKeyword = (String)params[1];
            String postParameters = "id=" + idKeyword + "&assignedNumber=" + assignedNumberKeyword;
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

    private void showCancelDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReservedBookDetailActivity.this);
        dialog.setTitle("취소 완료");
        dialog.setMessage("예약이 취소되었습니다.");
        dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
        finish();
    }

}
