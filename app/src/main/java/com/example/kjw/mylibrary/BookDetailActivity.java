package com.example.kjw.mylibrary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
    ArrayList<HashMap<String, String>> mArrayList;
    private String mJsonString;

    private static final String TAG = "BookDetailActivity";
    private static final String TAG_JSON="bookhoindinginfo";
    private static final String TAG_ASSIGNEDNUMBER= "ASSIGNEDNUMBER";
    private static final String TAG_POSSESSIONALCATION= "POSSESSIONALCATION";
    private static final String TAG_RESERVATIONRESULT= "result";

    private String id = "";

    ArrayList<myItems> items;

    private boolean reservationResult = false;

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

        id = intent.getStringExtra("id");

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
                showResultBookDetail();
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

    private void showResultBookDetail(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            items = new ArrayList<myItems>();
            myItems mi;

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String ASSIGNEDNUMBER = item.getString(TAG_ASSIGNEDNUMBER);
                String POSSESSIONALCATION = item.getString(TAG_POSSESSIONALCATION);

                mi = new myItems(ASSIGNEDNUMBER, POSSESSIONALCATION);
                items.add(mi);
            }

            CustomAdapter myadapter = new CustomAdapter(this, R.layout.book_search_holding_info, items);

            holdingInfoList.setAdapter(myadapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }


    public class httpTask2 extends AsyncTask<String, Void, String> {
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
                showResultReservation();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "start doInBackground");
            String serverURL = "http://110.46.227.154/add_book_reservation.php";
            String userID = (String) params[0];
            String callnum = (String) params[1];
            String postParameters = "id=" + userID + "&assignednumber=" + callnum;
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

    private void showResultReservation() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            JSONObject item = jsonArray.getJSONObject(0);
            reservationResult = item.getBoolean(TAG_RESERVATIONRESULT);


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }

    private class CustomAdapter extends BaseAdapter {
       Context maincon;
        LayoutInflater inflater;
        ArrayList<myItems> arSrc;
        int layout;

        public CustomAdapter(Context context, int alayout, ArrayList<myItems> aarSrc){
            maincon = context;
            arSrc = aarSrc;
            layout = alayout;
            inflater = LayoutInflater.from(maincon);
        }

        public int getCount(){
            return arSrc.size();
        }

        @Override
        public Object getItem(int position) {
            return arSrc.get(position).ASSIGNEDNUMBER;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            if (convertView == null) {
                convertView = inflater.inflate(layout, parent, false);
            }

            TextView ASSIGNEDNUMBER_text = (TextView)convertView.findViewById(R.id.textView_holding_info_assign);
            ASSIGNEDNUMBER_text.setText(arSrc.get(pos).ASSIGNEDNUMBER);


            TextView POSSESSIONALCATION_text = (TextView)convertView.findViewById(R.id.textView_holding_info_location);
            POSSESSIONALCATION_text.setText(arSrc.get(pos).POSSESSIONALCATION);

            Button reservation_button = (Button)convertView.findViewById(R.id.book_reservation_button);
            reservation_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    httpTask2 httptest = new httpTask2();
                    httptest.execute(id, arSrc.get(pos).ASSIGNEDNUMBER);
                    if ( reservationResult) {
                        Toast toast = Toast.makeText(BookDetailActivity.this, "예약 성공", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        Toast toast = Toast.makeText(BookDetailActivity.this, "예약 실패", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

            return convertView;
        }
    }

    public class myItems{
        public String ASSIGNEDNUMBER;
        public String POSSESSIONALCATION;

        myItems(String number, String position){
            ASSIGNEDNUMBER = number;
            POSSESSIONALCATION = position;
        }

    }
}
