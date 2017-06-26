package com.example.kjw.mylibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ReservedGsrActivity extends AppCompatActivity {

    private TextView date1;
    private TextView date2;
    private TextView date3;

    private RadioButton radiodate1;
    private RadioButton radiodate2;
    private RadioButton radiodate3;

    private RadioButton radiotime9;
    private RadioButton radiotime10;
    private RadioButton radiotime11;
    private RadioButton radiotime12;
    private RadioButton radiotime13;
    private RadioButton radiotime14;
    private RadioButton radiotime15;
    private RadioButton radiotime16;
    private RadioButton radiotime17;
    private RadioButton radiotime18;

    private EditText editid1;
    private EditText editid2;
    private EditText editid3;
    private EditText editid4;
    private EditText editid5;
    private EditText editid6;

    private Button idchkbutton1;
    private Button idchkbutton2;
    private Button idchkbutton3;
    private Button idchkbutton4;
    private Button idchkbutton5;
    private Button idchkbutton6;

    private Button reserve_button;

    private static final String TAG = "ReservedGsrActivity";

    private static final String TAG_JSON="groupstudyrooms";
    private static final String TAG_RTIME ="reservationtime";

    private static final String TAG_JSON2="userInfo";

    private String mJsonString;
    private int roomNum;

    private int idchk;
    private int confirmid;

    private int permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_gsr);

        confirmid=0;
        idchk=0;

        Intent intent = getIntent();
        roomNum = intent.getIntExtra("room", 1);
        permission=intent.getIntExtra("permission", PermissionData.notUser);
        if (permission == PermissionData.notUser) {
            Toast toast = Toast.makeText(ReservedGsrActivity.this, "로그인 후 이용해주세요", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }

        // 날짜
        date1 = (TextView) findViewById(R.id.date1);
        date2 = (TextView) findViewById(R.id.date2);
        date3 = (TextView) findViewById(R.id.date3);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("MM-dd");

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(date);
        cal2.setTime(date);

        cal1.add(Calendar.DATE, 1);
        cal2.add(Calendar.DATE, 2);

        date1.setText(mFormat.format(date));
        date2.setText(mFormat.format(cal1.getTime()));
        date3.setText(mFormat.format(cal2.getTime()));

        //여기까지 날짜

        //라디오 시간

        radiotime9 = (RadioButton) findViewById(R.id.radiotime9);
        radiotime10 = (RadioButton) findViewById(R.id.radiotime10);
        radiotime11 = (RadioButton) findViewById(R.id.radiotime11);
        radiotime12 = (RadioButton) findViewById(R.id.radiotime12);
        radiotime13 = (RadioButton) findViewById(R.id.radiotime13);
        radiotime14 = (RadioButton) findViewById(R.id.radiotime14);
        radiotime15 = (RadioButton) findViewById(R.id.radiotime15);
        radiotime16 = (RadioButton) findViewById(R.id.radiotime16);
        radiotime17 = (RadioButton) findViewById(R.id.radiotime17);
        radiotime18 = (RadioButton) findViewById(R.id.radiotime18);

        //radiotime9.setEnabled(false);
        //여기까지 라디오 시간


        //라디오 날짜

        radiodate1 = (RadioButton) findViewById(R.id.radiodate1);

        radiodate1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);

                final SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");

                String groupstudyroomnumber = Integer.toString(roomNum);
                String reservationdate =sqlFormat.format(date);;


                GetGsr task=new GetGsr();

                task.execute(groupstudyroomnumber,reservationdate);
            }
        });

        radiodate2 = (RadioButton) findViewById(R.id.radiodate2);

        radiodate2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                Calendar cal1 = Calendar.getInstance();

                cal1.setTime(date);

                cal1.add(Calendar.DATE, 1);

                final SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");

                String groupstudyroomnumber = Integer.toString(roomNum);
                String reservationdate = sqlFormat.format(cal1.getTime());


                GetGsr task=new GetGsr();

                task.execute(groupstudyroomnumber,reservationdate);
            }
        });

        radiodate3 = (RadioButton) findViewById(R.id.radiodate3);

        radiodate3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                Calendar cal2 = Calendar.getInstance();

                cal2.setTime(date);

                cal2.add(Calendar.DATE, 2);

                final SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");

                String groupstudyroomnumber = Integer.toString(roomNum);
                String reservationdate = sqlFormat.format(cal2.getTime());

                GetGsr task=new GetGsr();

                task.execute(groupstudyroomnumber,reservationdate);
            }
        });

        //여기까지 라디오 날짜

        //에딧텍스트
        editid1=(EditText)findViewById(R.id.sid1);
        editid2=(EditText)findViewById(R.id.sid2);
        editid3=(EditText)findViewById(R.id.sid3);
        editid4=(EditText)findViewById(R.id.sid4);
        editid5=(EditText)findViewById(R.id.sid5);
        editid6=(EditText)findViewById(R.id.sid6);
        //에딧 여기까지

        //아이디 체크 버튼
        idchkbutton1=(Button)findViewById(R.id.check_sid1_button);
        idchkbutton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                idchk=1;
                String eid=editid1.getText().toString();
                if(eid.length()==0) {
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }else if(eid.equals(editid2.getText().toString())||eid.equals(editid3.getText().toString())||
                        eid.equals(editid4.getText().toString())||eid.equals(editid5.getText().toString())||
                        eid.equals(editid6.getText().toString())){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "다른 ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                GetId task=new GetId();
                task.execute(editid1.getText().toString());
            }
        });
        idchkbutton2=(Button)findViewById(R.id.check_sid2_button);
        idchkbutton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                idchk=2;
                String eid=editid2.getText().toString();
                if(eid.length()==0){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }else if(eid.equals(editid1.getText().toString())||eid.equals(editid3.getText().toString())||
                        eid.equals(editid4.getText().toString())||eid.equals(editid5.getText().toString())||
                        eid.equals(editid6.getText().toString())){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "다른 ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                GetId task=new GetId();
                task.execute(editid2.getText().toString());
            }
        });
        idchkbutton3=(Button)findViewById(R.id.check_sid3_button);
        idchkbutton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                idchk=3;
                String eid=editid3.getText().toString();
                if(eid.length()==0){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }else if(eid.equals(editid2.getText().toString())||eid.equals(editid1.getText().toString())||
                        eid.equals(editid4.getText().toString())||eid.equals(editid5.getText().toString())||
                        eid.equals(editid6.getText().toString())){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "다른 ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                GetId task=new GetId();
                task.execute(editid3.getText().toString());
            }
        });
        idchkbutton4=(Button)findViewById(R.id.check_sid4_button);
        idchkbutton4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                idchk=4;
                String eid=editid4.getText().toString();
                if(eid.length()==0){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }else if(eid.equals(editid2.getText().toString())||eid.equals(editid3.getText().toString())||
                        eid.equals(editid1.getText().toString())||eid.equals(editid5.getText().toString())||
                        eid.equals(editid6.getText().toString())){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "다른 ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                GetId task=new GetId();
                task.execute(editid4.getText().toString());
            }
        });
        idchkbutton5=(Button)findViewById(R.id.check_sid5_button);
        idchkbutton5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                idchk=5;
                String eid=editid5.getText().toString();
                if(eid.length()==0){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }else if(eid.equals(editid2.getText().toString())||eid.equals(editid3.getText().toString())||
                        eid.equals(editid4.getText().toString())||eid.equals(editid1.getText().toString())||
                        eid.equals(editid6.getText().toString())){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "다른 ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                GetId task=new GetId();
                task.execute(editid5.getText().toString());
            }
        });
        idchkbutton6=(Button)findViewById(R.id.check_sid6_button);
        idchkbutton6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                idchk=6;
                String eid=editid6.getText().toString();
                if(eid.length()==0){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }else if(eid.equals(editid2.getText().toString())||eid.equals(editid3.getText().toString())||
                        eid.equals(editid4.getText().toString())||eid.equals(editid5.getText().toString())||
                        eid.equals(editid1.getText().toString())){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "다른 ID를 입력하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                GetId task=new GetId();
                task.execute(editid6.getText().toString());
            }
        });

        if(roomNum==31||roomNum==32||roomNum==33){
            editid5.setEnabled(false);
            editid5.setVisibility(View.INVISIBLE);
            editid6.setEnabled(false);
            editid6.setVisibility(View.INVISIBLE);
            idchkbutton5.setEnabled(false);
            idchkbutton5.setVisibility(View.INVISIBLE);
            idchkbutton6.setEnabled(false);
            idchkbutton6.setVisibility(View.INVISIBLE);
        }

        //아이디 체크 여기까지


        //예약 버튼
        reserve_button = (Button) findViewById(R.id.reserve_gsr_button);
        reserve_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InsertData task = new InsertData();

                String groupstudyroomnumber = Integer.toString(roomNum);
                String reservationdate = "";
                String reservationtime = "";

                long now = System.currentTimeMillis();
                Date date = new Date(now);

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();

                cal1.setTime(date);
                cal2.setTime(date);

                cal1.add(Calendar.DATE, 1);
                cal2.add(Calendar.DATE, 2);

                final SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");

                if (radiodate1.isChecked()) {
                    reservationdate = sqlFormat.format(date);
                } else if (radiodate2.isChecked()) {
                    reservationdate = sqlFormat.format(cal1.getTime());
                } else if (radiodate3.isChecked()) {
                    reservationdate = sqlFormat.format(cal2.getTime());
                } else {
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "날짜를 선택하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (radiotime9.isChecked()) {
                    reservationtime = "9";
                } else if (radiotime10.isChecked()) {
                    reservationtime = "10";
                } else if (radiotime11.isChecked()) {
                    reservationtime = "11";
                } else if (radiotime12.isChecked()) {
                    reservationtime = "12";
                } else if (radiotime13.isChecked()) {
                    reservationtime = "13";
                } else if (radiotime14.isChecked()) {
                    reservationtime = "14";
                } else if (radiotime15.isChecked()) {
                    reservationtime = "15";
                } else if (radiotime16.isChecked()) {
                    reservationtime = "16";
                } else if (radiotime17.isChecked()) {
                    reservationtime = "17";
                } else if (radiotime18.isChecked()) {
                    reservationtime = "18";
                } else {
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "시간을 선택하세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if((roomNum==41||roomNum==42||roomNum==43)&&confirmid<5){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "인원이 부족합니다(5인 이상)", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }else if((roomNum==31||roomNum==32||roomNum==33)&&confirmid<3){
                    Toast toast = Toast.makeText(ReservedGsrActivity.this, "인원이 부족합니다(3인 이상)", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                task.execute(groupstudyroomnumber, reservationdate, reservationtime);

                Toast toast = Toast.makeText(ReservedGsrActivity.this, "예약되었습니다", Toast.LENGTH_SHORT);
                toast.show();

                finish();
            }
        });
        //여기까지 예약 버튼

        //Toast toast = Toast.makeText(ReservedGsrActivity.this, Integer.toString(roomNum) , Toast.LENGTH_SHORT );
        //toast.show();
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReservedGsrActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String groupstudyroomnumber = (String) params[0];
            String reservationdate = (String) params[1];
            String reservationtime = (String) params[2];

            String serverURL = "http://"+ServerIpData.serverIp+"/set_reserved_gsr.php";
            String postParameters = "groupstudyroomnumber=" + groupstudyroomnumber + "&reservationdate=" + reservationdate+"&reservationtime=" + reservationtime;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    private class GetGsr extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReservedGsrActivity.this,
                    "Please Wait", null, true, true);
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
                timecheck();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = "http://"+ServerIpData.serverIp+"/get_reserved_gsr.php";
            String groupstudyroomnumber = (String)params[0];
            String reservationdate = (String)params[1];

            String postParameters = "groupstudyroomnumber=" + groupstudyroomnumber + "&reservationdate=" + reservationdate;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

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

    private void timecheck(){
        try {
            radiotime9.setEnabled(true);
            radiotime11.setEnabled(true);
            radiotime12.setEnabled(true);
            radiotime13.setEnabled(true);
            radiotime14.setEnabled(true);
            radiotime15.setEnabled(true);
            radiotime16.setEnabled(true);
            radiotime17.setEnabled(true);
            radiotime18.setEnabled(true);


            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String rtime = item.getString(TAG_RTIME);

                if(rtime.equals("9")){
                    radiotime9.setEnabled(false);
                }else if(rtime.equals("10")){
                    radiotime10.setEnabled(false);
                }else if(rtime.equals("11")){
                    radiotime11.setEnabled(false);
                }else if(rtime.equals("12")){
                    radiotime12.setEnabled(false);
                }else if(rtime.equals("13")){
                    radiotime13.setEnabled(false);
                }else if(rtime.equals("14")){
                    radiotime14.setEnabled(false);
                }else if(rtime.equals("15")){
                    radiotime15.setEnabled(false);
                }else if(rtime.equals("16")){
                    radiotime16.setEnabled(false);
                }else if(rtime.equals("17")){
                    radiotime17.setEnabled(false);
                }else if(rtime.equals("18")){
                    radiotime18.setEnabled(false);
                }

            }

        } catch (JSONException e) {

            Log.d(TAG, "timecheck : ", e);
        }

    }

    private class GetId extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReservedGsrActivity.this,
                    "Please Wait", null, true, true);
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
                idcheck();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = "http://"+ServerIpData.serverIp+"/get_id.php";
            String idKeyword = (String)params[0];

            String postParameters = "id=" + idKeyword;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

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
    private void idcheck(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON2);

            if (jsonArray.length() == 0) {
                Toast toast = Toast.makeText(ReservedGsrActivity.this, "존재하지 않는 ID입니다", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                confirmid++;
                if(idchk==1){
                    editid1.setEnabled(false);
                    idchkbutton1.setEnabled(false);
                }else if(idchk==2){
                    editid2.setEnabled(false);
                    idchkbutton2.setEnabled(false);
                }else if(idchk==3){
                    editid3.setEnabled(false);
                    idchkbutton3.setEnabled(false);
                }else if(idchk==4){
                    editid4.setEnabled(false);
                    idchkbutton4.setEnabled(false);
                }else if(idchk==5){
                    editid5.setEnabled(false);
                    idchkbutton5.setEnabled(false);
                }else if(idchk==6){
                    editid6.setEnabled(false);
                    idchkbutton6.setEnabled(false);
                }
                Toast toast = Toast.makeText(ReservedGsrActivity.this, "ID가 확인되었습니다", Toast.LENGTH_SHORT);
                toast.show();
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }

}
