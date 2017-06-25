package com.example.kjw.mylibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ReservedBookActivity extends AppCompatActivity {
    private ListView m_ListView;
    private String userId;
    ListAdapter adapter;

    private String mJsonString;
    private static final String TAG = "BookSearchActivity";
    private static final String TAG_JSON="reservationInfo";
    private static final String TAG_ID= "ID";
    private static final String TAG_TITLE = "TITLE";
    private static final String TAG_RESERVATIONORDER= "RESERVATIONORDER";
    private static final String TAG_EXPECTEDRENTAVAILABLEDATE= "EXPECTEDRENTAVAILABLEDATE";
    private static final String TAG_ASSIGNEDNUMBER = "ASSIGNEDNUMBER";

    private static final int RESERVED_BOOK_DETAIL_ACTIVITY = 0;
    ArrayList<HashMap<String, String>> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "move to ReservedBookActivity success");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_book);

    }

    protected void onResume() {
        super.onResume();
        mArrayList = new ArrayList<>();
        m_ListView = (ListView) findViewById(R.id.reserved_book_list);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        //test용
        //userId = "user";

        HttpTest httpTest = new HttpTest();
        httpTest.execute(userId);
        m_ListView.setOnItemClickListener(onClickListItem);

    }


    public class HttpTest extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReservedBookActivity.this, "Please Wait", null, true, true);
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
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "start doInBackground");
            String serverURL = "http://" + ServerIpData.serverIp + "/reservation_book.php";
            //exe에 넘겨준거
            String keyword = (String)params[0];
            String postParameters = "keyword=" + keyword;
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

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String ID = item.getString(TAG_ID);
                String TITLE = item.getString(TAG_TITLE);
                String RESERVATIONORDER = item.getString(TAG_RESERVATIONORDER);
                String EXPECTEDRENTAVAILABLEDATE = item.getString(TAG_EXPECTEDRENTAVAILABLEDATE);
                String ASSIGNEDNUMBER = item.getString(TAG_ASSIGNEDNUMBER);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, ID);
                hashMap.put(TAG_TITLE, TITLE);
                hashMap.put(TAG_RESERVATIONORDER, RESERVATIONORDER);
                hashMap.put(TAG_EXPECTEDRENTAVAILABLEDATE, EXPECTEDRENTAVAILABLEDATE);
                hashMap.put(TAG_ASSIGNEDNUMBER, ASSIGNEDNUMBER);

                mArrayList.add(hashMap);
            }

            adapter = new SimpleAdapter(
                    ReservedBookActivity.this, mArrayList, R.layout.reserved_book_item_list,
                    new String[]{TAG_TITLE},
                    new int[]{R.id.reserved_book_item_list_title}
            );

            m_ListView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }

    // 터치 이벤트
    private OnItemClickListener onClickListItem = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Intent intent = new Intent(ReservedBookActivity.this, ReservedBookDetailActivity.class);
            intent.putExtra("id", userId);
            intent.putExtra("title", ((HashMap<String,String>)adapter.getItem(arg2)).get(TAG_TITLE));
            intent.putExtra("reservationOrder", ((HashMap<String,String>)adapter.getItem(arg2)).get(TAG_RESERVATIONORDER));
            intent.putExtra("expectedRentAvailableDate", ((HashMap<String,String>)adapter.getItem(arg2)).get(TAG_EXPECTEDRENTAVAILABLEDATE));
            intent.putExtra("assignedNumber", ((HashMap<String,String>)adapter.getItem(arg2)).get(TAG_ASSIGNEDNUMBER));
            startActivityForResult(intent, RESERVED_BOOK_DETAIL_ACTIVITY);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESERVED_BOOK_DETAIL_ACTIVITY) {

            }
        }
    }
}
