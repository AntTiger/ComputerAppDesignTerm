package com.example.kjw.mylibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;

public class ReservedGsrActivity extends AppCompatActivity {

    private String mJsonString;
    private int roomNum;
    private static final String TAG = "ReservedGsrActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_gsr);

        Intent intent = getIntent();
        roomNum=intent.getIntExtra("room",1);

        Toast toast = Toast.makeText(ReservedGsrActivity.this, Integer.toString(roomNum) , Toast.LENGTH_SHORT );
        toast.show();
    }
    public class httpTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReservedGsrActivity.this, "Please Wait", null, true, true);
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
            String serverURL = "http://110.46.227.154/book_search.php";
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
        /*try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String AUTHOR = item.getString(TAG_AUTHOR);
                String CALLNUMBER = item.getString(TAG_CALLNUMBER);
                String DATATYPE = item.getString(TAG_DATATYPE);
                String ISBN = item.getString(TAG_ISBN);
                String KEYWORDS = item.getString(TAG_KEYWORDS);
                String MATERIALLOCATION = item.getString(TAG_MATERIALLOCATION);
                String PHYSICALDESCRIPTION = item.getString(TAG_PHYSICALDESCRIPTION);
                String TITLE = item.getString(TAG_TITLE);
                String PUBLICATION = item.getString(TAG_PUBLICATION);
                String COVERURL = item.getString(TAG_COVERURL);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_AUTHOR, AUTHOR);
                hashMap.put(TAG_CALLNUMBER, CALLNUMBER);
                hashMap.put(TAG_DATATYPE, DATATYPE);
                hashMap.put(TAG_ISBN, ISBN);
                hashMap.put(TAG_KEYWORDS, KEYWORDS);
                hashMap.put(TAG_MATERIALLOCATION, MATERIALLOCATION);
                hashMap.put(TAG_PHYSICALDESCRIPTION, PHYSICALDESCRIPTION);
                hashMap.put(TAG_TITLE, TITLE);
                hashMap.put(TAG_PUBLICATION, PUBLICATION);
                hashMap.put(TAG_COVERURL, COVERURL);

                mArrayList.add(hashMap);
            }

            adapter = new SimpleAdapter(
                    BookSearchActivity.this, mArrayList, R.layout.book_serach_item_list,
                    new String[]{TAG_AUTHOR,TAG_TITLE, TAG_PUBLICATION},
                    new int[]{R.id.textView_list_authors, R.id.textView_list_title, R.id.textView_list_publication}
            );


            m_ListView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
*/
    }
}
