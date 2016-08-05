package com.govt_exam_preparation.toolbar_functionality;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.govt_exam_preparation.R;


/**
 * Created by wscubetech on 18/6/16.
 */
public class ToolbarOperation {

    AppCompatActivity activity;
    Toolbar toolbar;
    TextView txtHeader;

    ActionBar actionBar;

    public ToolbarOperation(AppCompatActivity activity) {
        this.activity = activity;
        init();
    }

    public void init() {
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        txtHeader = (TextView) activity.findViewById(R.id.txtHeader);
    }

    public void setMyTitleAndToolbar(String strTitle) {
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setTitle("");
        toolbar.setTitle("");
        txtHeader.setText(strTitle);
    }

    public void showBackButton(Boolean b) {
        try {
            actionBar.setDisplayHomeAsUpEnabled(b);
        } catch (Exception e) {

        }
    }

}
