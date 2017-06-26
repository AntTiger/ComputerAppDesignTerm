package com.example.kjw.mylibrary;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.net.URLEncoder;
import java.util.HashMap;

public class HopeSearchActivity extends AppCompatActivity {

    ListView listView;
    HopeSearchListAdapter adapter;
    private final String TAG = "HopeSearchActivity";
    String mJsonString;
    String userID = "ID_Man";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("인터파크 검색 결과");
        Intent intent = getIntent();
        userID = intent.getStringExtra("id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hope_search);
        listView = (ListView) findViewById(R.id.hopeSearchList);
        adapter = new HopeSearchListAdapter();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HopeListSearchItem item = (HopeListSearchItem)adapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(HopeSearchActivity.this);  // this(현재 액티비티인 MainActivity 지정)
                builder.setTitle("안내 메시지");  // 타이틀 설정
                builder.setMessage(item.getName()+"을(를) 희망 도서 목록에 추가할까요?");  // 메시지 설정

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {  // 긍적 버튼 생성
                    @Override
                    public void onClick(DialogInterface dialog, int which) {  // 이 버튼이 눌리면
                        MyDirectRegisterTask task = new MyDirectRegisterTask(item.getName(), item.getAuthor(), item.getPublisher(), item.getPublishdate(), item.getIsbn(), "",  item.getPrice());
                        task.execute();
                        Toast.makeText(HopeSearchActivity.this, "신청했습니다.", Toast.LENGTH_SHORT).show();
                        finish();  // 애플리케이션 종료
                    }
                });
                builder.setNegativeButton("아니", new DialogInterface.OnClickListener() {  // 긍적 버튼 생성
                    @Override
                    public void onClick(DialogInterface dialog, int which) {  // 이 버튼이 눌리면
                    }
                });
                AlertDialog dialog = builder.create();  // dialog 객체 생성
                dialog.show();  // 화면에 띄어준다
            }
        });

        String keyword = intent.getStringExtra("keyword");
        MyGetSearchListTask task = new MyGetSearchListTask();
        task.execute(keyword);
    }

    class MyGetSearchListTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;
        String errorString = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(HopeSearchActivity.this,
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
                String keyword = params[0];
                String str = URLEncoder.encode(new String(keyword.getBytes("UTF-8")));
                URL url = new URL("http://book.interpark.com/api/search.api?key=B5B3532F48A1A439D8F752EF5D3D8BB24D60363993D409259843439F29EB6A15&query="+str+"&output=json&queryType=all&maxResults=25&sort=accuracy");
                Log.d("URL", str);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

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
            JSONArray jsonArray = jsonObject.getJSONArray("item");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String title = item.getString("title");
                String pubDate = item.getString("pubDate");
                String price = item.getString("priceSales");
                String imgURL = item.getString("coverSmallUrl");
                String publisher = item.getString("publisher");
                String author = item.getString("author");
                String isbn = item.getString("isbn");

                adapter.addItem(title,author,imgURL,publisher,pubDate,isbn,price);
            }

            listView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    class MyDirectRegisterTask extends AsyncTask<String,Void,String>{
        String bookName;
        String author;
        String publisher;
        String publishDate;
        String ISBN;
        String pan;
        String price;

        public MyDirectRegisterTask(String bookName, String author, String publisher, String publishDate, String ISBN, String pan, String price) {
            this.bookName = bookName;
            this.author = author;
            this.publisher = publisher;
            this.publishDate = publishDate;
            this.ISBN = ISBN;
            this.pan = pan;
            this.price = price;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://"+ServerIpData.serverIp+"/registerHopeDirect.php"); //요청 URL을 입력
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); //캐릭터셋 설정

                String parameters =
                        "id=" + new String(userID.getBytes("UTF-8"))
                                +"&bookname=" +  new String(bookName.getBytes("UTF-8"))
                                +"&author=" + new String(author.getBytes("UTF-8"))
                                +"&publisher=" + new String(publisher.getBytes("UTF-8"))
                                +"&publishdate=" + new String(publishDate.getBytes("UTF-8"))
                                +"&isbn=" + new String(ISBN.getBytes("UTF-8"))
                                +"&pan=" + new String(pan.getBytes("UTF-8"))
                                +"&price=" + new String(price.getBytes("UTF-8"));

                writer.write(parameters); //요청 파라미터를 입력
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //캐릭터셋 설정

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                System.out.println("response:" + sb.toString());

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
    }

}
