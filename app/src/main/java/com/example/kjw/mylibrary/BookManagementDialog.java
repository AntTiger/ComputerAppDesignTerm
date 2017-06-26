package com.example.kjw.mylibrary;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.HashMap;

public class BookManagementDialog extends Dialog {

    private TextView mModView;
    private String mMod;
    private EditText mTitleView;
    private EditText mAuthorView;
    private EditText mDatatypeView;
    private EditText mISBNView;
    private EditText mKeywordsView;
    private EditText mMateriallocationView;
    private EditText mPhysicaldescriptionView;
    private EditText mPublicationView;
    private EditText mCallnumberView;
    private EditText mCoverURLView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mTitle;
    private String mAuthor;
    private String mDatatype;
    private String mISBN;
    private String mKeywords;
    private String mMateriallocation;
    private String mPhysicaldescription;
    private String mPublication;
    private String mCallnumber;
    private String mCoverURL;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_book_info);

        mModView=(TextView) findViewById(R.id.dialog_book_info_mod);
        mTitleView = (EditText) findViewById(R.id.dialog_book_info_title);
        mAuthorView = (EditText) findViewById(R.id.dialog_book_info_author);
        mDatatypeView = (EditText) findViewById(R.id.dialog_book_info_datatype);
        mISBNView = (EditText) findViewById(R.id.dialog_book_info_isbn);
        mKeywordsView = (EditText) findViewById(R.id.dialog_book_info_keywords);
        mMateriallocationView = (EditText) findViewById(R.id.dialog_book_info_materiallocation);
        mPhysicaldescriptionView = (EditText) findViewById(R.id.dialog_book_info_physicaldescription);
        mPublicationView = (EditText) findViewById(R.id.dialog_book_info_publication);
        mCallnumberView = (EditText) findViewById(R.id.dialog_book_info_callnumber);
        mCoverURLView = (EditText) findViewById(R.id.dialog_book_info_coverurl);
        mLeftButton = (Button) findViewById(R.id.dialog_book_info_ok);
        mRightButton = (Button) findViewById(R.id.dialog_book_info_cancel);

        mTitleView.setText(mTitle);
        mAuthorView.setText(mAuthor);
        mDatatypeView.setText(mDatatype);
        mISBNView.setText(mISBN);
        mKeywordsView.setText(mKeywords);
        mMateriallocationView.setText(mMateriallocation);
        mPhysicaldescriptionView.setText(mPhysicaldescription);
        mPublicationView.setText(mPublication);
        mCallnumberView.setText(mCallnumber);
        mCoverURLView.setText(mCoverURL);
        mModView.setText(mMod);

        if (mLeftClickListener != null && mRightClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
            mRightButton.setOnClickListener(mRightClickListener);
        } else if (mLeftClickListener != null
                && mRightClickListener == null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
        } else {

        }
    }

    public HashMap<String,String> getData(){
        HashMap<String,String> data=new HashMap<>();
        data.put("TITLE",mTitleView.getText().toString());
        data.put("AUTHOR",mAuthorView.getText().toString());
        data.put("DATATYPE",mDatatypeView.getText().toString());
        data.put("ISBN",mISBNView.getText().toString());
        data.put("KEYWORDS",mKeywordsView.getText().toString());
        data.put("MATERIALLOCATION",mMateriallocationView.getText().toString());
        data.put("PHYSICALDESCRIPTION",mPhysicaldescriptionView.getText().toString());
        data.put("PUBLICATION",mPublicationView.getText().toString());
        data.put("CALLNUMBER",mCallnumberView.getText().toString());
        data.put("COVERURL",mCoverURLView.getText().toString());
        return data;
    }
    public BookManagementDialog(Context context, String mod,HashMap<String,String> data, View.OnClickListener leftListener,
                                View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mMod=mod;
        this.mTitle = data.get("TITLE");
        this.mAuthor = data.get("AUTHOR");
        this.mDatatype = data.get("DATATYPE");
        this.mISBN = data.get("ISBN");
        this.mKeywords = data.get("KEYWORDS");
        this.mMateriallocation = data.get("MATERIALLOCATION");
        this.mPhysicaldescription = data.get("PHYSICALDESCRIPTION");
        this.mPublication = data.get("PUBLICATION");
        this.mCallnumber = data.get("CALLNUMBER");
        this.mCoverURL = data.get("COVERURL");
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }

    public BookManagementDialog(Context context, String mod,View.OnClickListener leftListener,
                                View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mMod=mod;
        this.mTitle = "";
        this.mAuthor = "";
        this.mDatatype = "";
        this.mISBN = "";
        this.mKeywords = "";
        this.mMateriallocation = "";
        this.mPhysicaldescription = "";
        this.mPublication = "";
        this.mCallnumber = "";
        this.mCoverURL = "";
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
}

