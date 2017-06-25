package com.example.kjw.mylibrary;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HopeBook_DirectActivity extends AppCompatActivity {
    EditText edit_bookname;
    EditText edit_author;
    EditText edit_publisher;
    EditText edit_publishdate;
    EditText edit_isbn;
    EditText edit_pan;
    EditText edit_price;

    Button submitBt;
    Button cancelBt;

    String userID = "ID_Man";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("직접신청");
        setContentView(R.layout.activity_hope_book_direct);

        edit_bookname = (EditText)findViewById(R.id.directHope_bookName);
        edit_author = (EditText)findViewById(R.id.directHope_author);
        edit_publisher = (EditText)findViewById(R.id.directHope_publisher);
        edit_publishdate = (EditText)findViewById(R.id.directHope_publishDate);
        edit_isbn = (EditText)findViewById(R.id.directHope_ISBN);
        edit_pan = (EditText)findViewById(R.id.directHope_pan);
        edit_price = (EditText)findViewById(R.id.directHope_price);

        submitBt = (Button)findViewById(R.id.directHope_submitBt);
        cancelBt = (Button)findViewById(R.id.directHope_cancelBt);
        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = edit_bookname.getText().toString();
                String author = edit_author.getText().toString();
                String publisher = edit_publisher.getText().toString();
                String publishDate = edit_publishdate.getText().toString();
                String ISBN = edit_isbn.getText().toString();

                String pan = edit_pan.getText().toString();
                String price = edit_price.getText().toString();

                boolean isInputEmpty = (bookName.isEmpty() ||author.isEmpty() ||publisher.isEmpty() ||publishDate.isEmpty() ||ISBN.isEmpty());

                if(isInputEmpty){
                    Toast.makeText(HopeBook_DirectActivity.this, "필수 항목들을 모두 입력해 주세요", Toast.LENGTH_SHORT).show();
                }else{
                    MyDirectRegisterTask task = new MyDirectRegisterTask(bookName, author, publisher, publishDate, ISBN, pan, price);
                    task.execute();
                    Toast.makeText(HopeBook_DirectActivity.this, "신청 완료", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });


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
                URL url = new URL("http://110.46.227.154/registerHopeDirect.php"); //요청 URL을 입력
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setConnectTimeout(60);

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
