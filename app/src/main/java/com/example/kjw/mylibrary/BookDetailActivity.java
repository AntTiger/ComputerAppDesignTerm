package com.example.kjw.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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


    }

}
