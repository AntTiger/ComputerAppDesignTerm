package com.example.kjw.mylibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class HopeBookActivity extends AppCompatActivity {

    final int FLAG_POPUP = 51;
    final int FLAG_DIRECT = 52;
    String userID = "ID_Man";
    ListView listView;
    HopeListAdapter adapter;

    private String TAG = "phptest";
    ArrayList<HashMap<String, String>> mArrayList;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("희망도서 신청");
        setContentView(R.layout.activity_hope_book);
        listView = (ListView)findViewById(R.id.hopebooklist);
        adapter = new HopeListAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    class MyGetHopeListTask extends AsyncTask<String,Void,String> {
        String bookName;
        String author;
        String publisher;
        String publishDate;
        String ISBN;
        String pan;
        String price;
        String status;

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(HopeBookActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            //mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){

                //mTextViewResult.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = new URL("http://110.46.227.154/getHopeList.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
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

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("hopebooklist");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);
                String TAG_BOOKNAME = "bookname";
                String TAG_AUTHOR = "author";
                String TAG_REGISTERDAY = "registerday";
                String TAG_STATUS = "status";

                String bookname = item.getString("bookname");
                String author = item.getString("author");
                String registerday = item.getString("registerday");
                String status = item.getString("status");
/*
                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_BOOKNAME, bookname);
                hashMap.put(TAG_AUTHOR, author);
                hashMap.put(TAG_REGISTERDAY, registerday);
                hashMap.put(TAG_STATUS,status);

                mArrayList.add(hashMap);
                */
                adapter.addItem(registerday,bookname+" "+author,status);
            }

            listView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}
