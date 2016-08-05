package com.govt_exam_preparation.toolbar_functionality;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.others.My_URL;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


/**
 * Created by wscubetech on 18/6/16.
 */
public class CollapsingToolbarOperation {

    AppCompatActivity activity;
    Toolbar toolbar;
    TextView txtHeader;
    ActionBar actionBar;

    ImageView imgBanner;

    public CollapsingToolbarOperation(AppCompatActivity activity) {
        this.activity = activity;
        init();
    }

    public void init() {
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        txtHeader = (TextView) activity.findViewById(R.id.txtHeader);
        imgBanner = (ImageView) activity.findViewById(R.id.imgBanner);
    }

    public void setMyTitleAndToolbar(String strTitle, int image) {
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setTitle("");
        txtHeader.setText(strTitle);
        imgBanner.setImageResource(image);
    }

    public void setMyTitleAndToolbar(String strTitle, int placeholder, String imageUrl) {
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setTitle("");
        txtHeader.setText(strTitle);

        Picasso.with(activity)
                .load(My_URL.image_url + imageUrl)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(placeholder)
                .into(imgBanner);

    }

    public ImageView getImageView() {
        return imgBanner;
    }

    public void showBackButton(Boolean b) {
        try {
            actionBar.setDisplayHomeAsUpEnabled(b);
        } catch (Exception e) {

        }
    }

}
