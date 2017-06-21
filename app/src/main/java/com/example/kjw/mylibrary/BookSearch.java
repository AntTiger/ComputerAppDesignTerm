package com.example.kjw.mylibrary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Toast;

public class BookSearch extends AppCompatActivity {
    private ListView m_ListView;
    private ArrayAdapter<String> m_Adapter;

    private OnItemClickListener onClickListItem = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
            Toast.makeText(getApplicationContext(), m_Adapter.getItem(arg2), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);

        m_ListView = (ListView) findViewById(R.id.book_list);
        m_ListView.setAdapter(m_Adapter);

        m_ListView.setOnItemClickListener(onClickListItem);

       addItem();

    }

    private void addItem(){
        m_Adapter.add("재와 환상의 그림갈");
        m_Adapter.add("이 세계에 멋진 축복을");
        m_Adapter.add("유녀전기");
        m_Adapter.add("가브릴 드롭아웃");
        m_Adapter.add("사이코패스");
    }

}
