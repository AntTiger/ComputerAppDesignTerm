package com.example.kjw.mylibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.HashMap;

public class BookDetailActivity extends AppCompatActivity {
    private String AUTHOR;
    private String CALLNUMBER;
    private String DATATYPE;
    private String ISBN;
    private String KEYWORDS;
    private String MATERIALLOCATION;
    private String PHYSICALDESCRIPTION;
    private String TITLE;
    private String PUBLICATION;
    private String COVERURL;

    private TextView TV_AUTHOR;
    private TextView TV_CALLNUMBER;
    private TextView TV_DATATYPE;
    private TextView TV_ISBN;
    private TextView TV_KEYWORDS;
    private TextView TV_MATERIALLOCATION;
    private TextView TV_PHYSICALDESCRIPTION;
    private TextView TV_TITLE;
    private TextView TV_PUBLICATION;
    private TextView TV_COVERURL;

    private ListView holdingInfoList;
    ListAdapter adapter;
    ArrayList<HashMap<String, String>> mArrayList;
    private String mJsonString;

    private static final String TAG = "BookDetailActivity";
    private static final String TAG_JSON="bookhoindinginfo";
    private static final String TAG_ASSIGNEDNUMBER= "ASSIGNEDNUMBER";
    private static final String TAG_POSSESSIONALCATION= "POSSESSIONALCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        AUTHOR = intent.getStringExtra("AUTHOR");
        CALLNUMBER = intent.getStringExtra("CALLNUMBER");
        DATATYPE = intent.getStringExtra("DATATYPE");
        ISBN = intent.getStringExtra("ISBN");
        KEYWORDS = intent.getStringExtra("KEYWORDS");
        MATERIALLOCATION = intent.getStringExtra("MATERIALLOCATION");
        PHYSICALDESCRIPTION = intent.getStringExtra("PHYSICALDESCRIPTION");
        TITLE = intent.getStringExtra("TITLE");
        PUBLICATION = intent.getStringExtra("PUBLICATION");
        COVERURL = intent.getStringExtra("COVERURL");

        TV_AUTHOR = (TextView) findViewById(R.id.AUTHOR);
        TV_CALLNUMBER = (TextView) findViewById(R.id.CALLNUMBER);
        TV_DATATYPE = (TextView) findViewById(R.id.DATATYPE);
        TV_ISBN = (TextView) findViewById(R.id.ISBN);
        TV_KEYWORDS = (TextView) findViewById(R.id.KEYWORDS);
        TV_MATERIALLOCATION = (TextView) findViewById(R.id.MATERIALLOCATION);
        TV_PHYSICALDESCRIPTION = (TextView) findViewById(R.id.PHYSICALDESCRIPTION);
        TV_TITLE = (TextView) findViewById(R.id.TITLE);
        TV_PUBLICATION = (TextView) findViewById(R.id.PUBLICATION);
        TV_COVERURL = (TextView) findViewById(R.id.COVERURL);

        holdingInfoList = (ListView) findViewById(R.id.holding_book_list) ;
        mArrayList = new ArrayList<>();


        TV_AUTHOR.setText(AUTHOR);
        TV_CALLNUMBER.setText(CALLNUMBER);
        TV_DATATYPE.setText(DATATYPE);
        TV_ISBN.setText(ISBN);
        TV_KEYWORDS.setText(KEYWORDS);
        TV_MATERIALLOCATION.setText(MATERIALLOCATION);
        TV_PHYSICALDESCRIPTION.setText(PHYSICALDESCRIPTION);
        TV_TITLE.setText(TITLE);
        TV_PUBLICATION.setText(PUBLICATION);
        TV_COVERURL.setText(COVERURL);

        httpTask httptest = new httpTask();
        httptest.execute(CALLNUMBER);

    }

    public class httpTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(BookDetailActivity.this, "Please Wait", null, true, true);
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
            String serverURL = "http://110.46.227.154/book_search_holding.php";
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

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String ASSIGNEDNUMBER = item.getString(TAG_ASSIGNEDNUMBER);
                String POSSESSIONALCATION = item.getString(TAG_POSSESSIONALCATION);


                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_ASSIGNEDNUMBER, ASSIGNEDNUMBER);
                hashMap.put(TAG_POSSESSIONALCATION, POSSESSIONALCATION);

                mArrayList.add(hashMap);
            }

            adapter = new SimpleAdapter(
                    BookDetailActivity.this, mArrayList, R.layout.book_search_holding_info,
                    new String[]{TAG_ASSIGNEDNUMBER, TAG_POSSESSIONALCATION},
                    new int[]{R.id.textView_holding_info_assigednumber, R.id.textView_holding_info_location}
            );


            holdingInfoList.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
