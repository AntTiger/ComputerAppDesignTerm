package com.example.kjw.mylibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class StudyroomDetailActivity extends AppCompatActivity {

    private ArrayList<TextView> seat=new ArrayList<TextView>();

    private int roomnum;
    private static final String TAG = "StudyroomDetailActivity";

    private static final String TAG_JSON="studyroom";
    private static final String TAG_SN ="seatnumber";

    private TextView srname;

    private String mJsonString;
    private String roomname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studyroom_detail);

        Intent intent = getIntent();
        roomnum =intent.getIntExtra("sr",0);
        roomname = intent.getStringExtra("sn");

        srname=(TextView)findViewById(R.id.roomname);
        srname.setText(roomname);



        seat.add((TextView)findViewById(R.id.seat1));
        seat.add((TextView)findViewById(R.id.seat2));
        seat.add((TextView)findViewById(R.id.seat3));
        seat.add((TextView)findViewById(R.id.seat4));
        seat.add((TextView)findViewById(R.id.seat5));
        seat.add((TextView)findViewById(R.id.seat6));
        seat.add((TextView)findViewById(R.id.seat7));
        seat.add((TextView)findViewById(R.id.seat8));

        seat.add((TextView)findViewById(R.id.seat9));
        seat.add((TextView)findViewById(R.id.seat10));
        seat.add((TextView)findViewById(R.id.seat11));
        seat.add((TextView)findViewById(R.id.seat12));
        seat.add((TextView)findViewById(R.id.seat13));
        seat.add((TextView)findViewById(R.id.seat14));
        seat.add((TextView)findViewById(R.id.seat15));
        seat.add((TextView)findViewById(R.id.seat16));

        seat.add((TextView)findViewById(R.id.seat17));
        seat.add((TextView)findViewById(R.id.seat18));
        seat.add((TextView)findViewById(R.id.seat19));
        seat.add((TextView)findViewById(R.id.seat20));
        seat.add((TextView)findViewById(R.id.seat21));
        seat.add((TextView)findViewById(R.id.seat22));
        seat.add((TextView)findViewById(R.id.seat23));
        seat.add((TextView)findViewById(R.id.seat24));


        GetSeat task=new GetSeat();

        task.execute(Integer.toString(roomnum));
    }
    private class GetSeat extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(StudyroomDetailActivity.this,
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
                seatchk();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = "http://"+ServerIpData.serverIp+"/get_studyroom_seat.php";
            String roomnumber = (String)params[0];

            String postParameters = "roomnumber=" + roomnumber ;

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
    private void seatchk(){
        try {


            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String seatnumber = item.getString(TAG_SN);

                int sn=Integer.valueOf(seatnumber);

                seat.get(sn-1).setBackgroundColor(Color.GRAY);

            }

        } catch (JSONException e) {

            Log.d(TAG, "seatchk : ", e);
        }

    }
}
