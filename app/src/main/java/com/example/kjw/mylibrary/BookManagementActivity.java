

package com.example.kjw.mylibrary;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

public class BookManagementActivity extends AppCompatActivity implements BookManagementListAdapter.ListBtnClickListener {
    private ListView m_ListView;
    BookManagementListAdapter adapter;
    private String id = "";
    private Button bookAdditionButton;
    private String mJsonString;
    private static final String TAG = "BookManagementActivity";
    private static final String TAG_JSON="bookInfo";
    private static final String TAG_JSON2="query_result";
    private static final String TAG_AUTHOR= "AUTHOR";
    private static final String TAG_CALLNUMBER= "CALLNUMBER";
    private static final String TAG_DATATYPE= "DATATYPE";
    private static final String TAG_ISBN= "ISBN";
    private static final String TAG_KEYWORDS= "KEYWORDS";
    private static final String TAG_MATERIALLOCATION= "MATERIALLOCATION";
    private static final String TAG_PHYSICALDESCRIPTION= "PHYSICALDESCRIPTION";
    private static final String TAG_TITLE= "TITLE";
    private static final String TAG_PUBLICATION= "PUBLICATION";
    private static final String TAG_COVERURL= "COVERURL";
    private static final String TAG_RESULT="RESULT";
    ArrayList<HashMap<String, String>> mArrayList;
    ArrayList<BookManagementListItem> bookManagementList;
    BookManagementVO tvo;



    private BookManagementDialog mBookManagementDialog;
    private BookManagementFinalCheck mBookManagementFinalCheck;
    public void onListBtnClick(BookManagementVO vo){
        tvo=vo;
        if (vo.getFlag()==1){
            mBookManagementDialog=new BookManagementDialog(this,"도서 정보 수정",
                    mArrayList.get(vo.getPosition()),modificationleftListener,
                    dialogrightListener
                    );
            mBookManagementDialog.show();
        }
        else if(vo.getFlag()==2){
            mBookManagementFinalCheck=new BookManagementFinalCheck(this,
                    "제거",finaldeleteListener,finalrightListener);
            mBookManagementFinalCheck.show();

        }

    }

    private View.OnClickListener finaldeleteListener = new View.OnClickListener(){
        public void onClick(View v){
            int pos=tvo.getPosition();
            String crit = bookManagementList.get(pos).getCallnumberStr();
            httpTask_bookdelete httptest = new httpTask_bookdelete();
            httptest.execute(crit);
            httpTask_booklist httptest1 = new httpTask_booklist();
            httptest1.execute();
            mBookManagementFinalCheck.dismiss();
            mBookManagementDialog.dismiss();

        }
    };
    private View.OnClickListener finaladdListener = new View.OnClickListener(){
        public void onClick(View v){

            HashMap<String,String> data=mBookManagementDialog.getData();

            httpTask_bookadd httptest = new httpTask_bookadd();
            httptest.execute(data.get(TAG_TITLE),data.get(TAG_AUTHOR),data.get(TAG_CALLNUMBER),
                    data.get(TAG_DATATYPE),data.get(TAG_ISBN),data.get(TAG_KEYWORDS),
                    data.get(TAG_MATERIALLOCATION),data.get(TAG_PHYSICALDESCRIPTION),data.get(TAG_PUBLICATION)
                    ,data.get(TAG_COVERURL));
            httpTask_booklist httptest1 = new httpTask_booklist();
            httptest1.execute();
            mBookManagementFinalCheck.dismiss();
            mBookManagementDialog.dismiss();

        }
    };
    private View.OnClickListener finalmodifyListener = new View.OnClickListener(){
        public void onClick(View v){
            HashMap<String,String> data=mBookManagementDialog.getData();
            int pos=tvo.getPosition();
            String crit = bookManagementList.get(pos).getCallnumberStr();
            httpTask_bookmodify httptest = new httpTask_bookmodify();
            httptest.execute(data.get(TAG_TITLE),data.get(TAG_AUTHOR),data.get(TAG_CALLNUMBER),
                    data.get(TAG_DATATYPE),data.get(TAG_ISBN),data.get(TAG_KEYWORDS),
                    data.get(TAG_MATERIALLOCATION),data.get(TAG_PHYSICALDESCRIPTION),data.get(TAG_PUBLICATION)
                    ,data.get(TAG_COVERURL),crit);

            httpTask_booklist httptest1 = new httpTask_booklist();
            httptest1.execute();

            mBookManagementFinalCheck.dismiss();
            mBookManagementDialog.dismiss();

        }
    };
    private View.OnClickListener finalrightListener = new View.OnClickListener(){
        public void onClick(View v){

            mBookManagementFinalCheck.dismiss();
        }
    };
    private View.OnClickListener additionleftListener = new View.OnClickListener(){
        public void onClick(View v){
            mBookManagementFinalCheck=new BookManagementFinalCheck(BookManagementActivity.this,
                    "추가",finaladdListener,finalrightListener);
            mBookManagementFinalCheck.show();


        }
    };
    private View.OnClickListener modificationleftListener = new View.OnClickListener(){
        public void onClick(View v){
            mBookManagementFinalCheck=new BookManagementFinalCheck(BookManagementActivity.this,
                    "수정",finalmodifyListener,finalrightListener);
            mBookManagementFinalCheck.show();


        }
    };
    private View.OnClickListener dialogrightListener = new View.OnClickListener(){
        public void onClick(View v){

            mBookManagementDialog.dismiss();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "move to BookManagementActivity success");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_management);

        bookAdditionButton=(Button)findViewById(R.id.book_management_book_addition);
        bookAdditionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBookManagementDialog=new BookManagementDialog(BookManagementActivity.this,"도서 추가",additionleftListener,
                        dialogrightListener
                );
                mBookManagementDialog.show();
            }
        });


        httpTask_booklist httptest = new httpTask_booklist();
        httptest.execute();


    }

    public class httpTask_booklist extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(BookManagementActivity.this, "Please Wait", null, true, true);
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
                showList();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "start doInBackground");
            String serverURL = "http://" + ServerIpData.serverIp + "/book_management_list.php";

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();



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
    public class httpTask_bookadd extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(BookManagementActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response  - " + result);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "start doInBackground");
            String serverURL = "http://" + ServerIpData.serverIp + "/book_management_book_addition.php";

            String title = (String)params[0];
            String author = (String)params[1];
            String callnumber = (String)params[2];
            String datatype = (String)params[3];
            String isbn = (String)params[4];
            String keywords = (String)params[5];
            String materiallocation = (String)params[6];
            String physicaldescription = (String)params[7];
            String publication = (String)params[8];
            String coverurl = (String)params[9];
            String postParameters = "keywords=" + keywords+"&title="+title+"&author="+author+"&callnumber="+callnumber
                    +"&datatype="+datatype+"&isbn="+isbn+"&materiallocation="+materiallocation+"&physicaldescription="
                    +physicaldescription+"&publication="+publication+"&coverurl="+coverurl;
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
    public class httpTask_bookmodify extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(BookManagementActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response  - " + result);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "start doInBackground");
            String serverURL = "http://" + ServerIpData.serverIp + "/book_management_book_modification.php";

            String title = (String)params[0];
            String author = (String)params[1];
            String callnumber = (String)params[2];
            String datatype = (String)params[3];
            String isbn = (String)params[4];
            String keywords = (String)params[5];
            String materiallocation = (String)params[6];
            String physicaldescription = (String)params[7];
            String publication = (String)params[8];
            String coverurl = (String)params[9];
            String crit = (String)params[10];
            String postParameters = "keywords=" + keywords+"&title="+title+"&author="+author+"&callnumber="+callnumber
                    +"&datatype="+datatype+"&isbn="+isbn+"&materiallocation="+materiallocation+"&physicaldescription="
                    +physicaldescription+"&publication="+publication+"&coverurl="+coverurl+"&crit="+crit;
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
    public class httpTask_bookdelete extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(BookManagementActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response  - " + result);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "start doInBackground");
            String serverURL = "http://" + ServerIpData.serverIp + "/book_management_book_deletion.php";


            String callnumber = (String)params[0];
            String postParameters = "callnumber="+callnumber;
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
    private void showList(){
        mArrayList = new ArrayList<>();

        m_ListView = (ListView) findViewById(R.id.book_management_list_view);
        bookManagementList=new ArrayList<>();
        try {
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

                bookManagementList.add(new BookManagementListItem(TITLE,AUTHOR,CALLNUMBER));
            }

            adapter = new BookManagementListAdapter(this,R.layout.book_management_list_item,bookManagementList,this);


            m_ListView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}

