package com.example.kjw.mylibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by User on 2017-06-25.
 */

public class HopeSearchListAdapter extends BaseAdapter {
    private Bitmap bitmap;
    private String imgURL;

    private ArrayList<HopeListSearchItem> listItemList = new ArrayList<HopeListSearchItem>();
    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hopebookseach_listitem, parent, false);
        }

        ImageView img = (ImageView) convertView.findViewById(R.id.hopesearch_coverimg);
        TextView name = (TextView)convertView.findViewById(R.id.hopesearch_name);
        TextView author = (TextView)convertView.findViewById(R.id.hopesearch_author);
        TextView price = (TextView)convertView.findViewById(R.id.hopesearch_price);
        TextView publishdate = (TextView)convertView.findViewById(R.id.hopesearch_publishdate);

        HopeListSearchItem item = listItemList.get(position);

        name.setText(item.getName());
        author.setText(item.getAuthor()+"|"+item.getPublisher());
        price.setText(item.getPrice()+"원");
        publishdate.setText(item.getPublishdate());
        imgURL = item.getImgURL();

        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String source = imgURL;
                    URL url = new URL(source);

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                }catch (Exception e){

                }
            }
        });
        mThread.start();
        try{
            mThread.join();

            img.setImageBitmap(bitmap);
        }catch (Exception e){

        }
        return convertView;
    }

    public void addItem(String name, String author, String imgURL, String publisher, String publishdate, String isbn, String price){
        HopeListSearchItem item = new HopeListSearchItem(name, author, imgURL, publisher, publishdate, isbn, price);
        listItemList.add(item);
    }


}
