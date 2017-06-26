package com.example.kjw.mylibrary;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;



/**
 * Created by LHS on 2017-06-26.
 */

public class BookManagementFinalCheck extends Dialog {

    private TextView mText;
    private Button mLeftButton;
    private Button mRightButton;
    private String text;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_final_check);

        mText = (TextView) findViewById(R.id.dialog_final_check_text);
        mLeftButton = (Button) findViewById(R.id.dialog_final_check_ok);
        mRightButton = (Button) findViewById(R.id.dialog_final_check_cancel);
        mText.setText(text);

        if (mLeftClickListener != null && mRightClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
            mRightButton.setOnClickListener(mRightClickListener);
        } else if (mLeftClickListener != null
                && mRightClickListener == null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
        } else {

        }
    }



    public BookManagementFinalCheck(Context context,String mod, View.OnClickListener leftListener,
                                View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        text=mod.concat("하시겠습니까?");
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
}