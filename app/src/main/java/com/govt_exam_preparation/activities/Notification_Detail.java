package com.govt_exam_preparation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.Notification_Model;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class Notification_Detail extends AppCompatActivity {


    TextView job_title, post, job_date;
    Notification_Model details;

    ImageView imgNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        init();

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Notification Detail");
        toolbarOperation.showBackButton(true);

        details = (Notification_Model) getIntent().getExtras().getSerializable("details");

        job_title.setText(details.getTitle());
        job_date.setText(details.getDate());
        post.setText(details.getDescription());

        if (details.getImage().equalsIgnoreCase("")) {
            imgNotification.setVisibility(View.GONE);
        } else {
            imgNotification.setVisibility(View.VISIBLE);

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
            imageLoader.clearMemoryCache();
            imageLoader.clearDiskCache();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    //this is the image that will be displayed if download fails
                    .cacheInMemory()
                    .cacheOnDisc()
                    .build();

            imageLoader.displayImage(My_URL.image_url + details.getImage(),
                    imgNotification, options);
        }


    }

    public void init() {
        job_title = (TextView) findViewById(R.id.job_title);
        post = (TextView) findViewById(R.id.post);
        job_date = (TextView) findViewById(R.id.job_date);
        imgNotification = (ImageView) findViewById(R.id.imgNotification);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
