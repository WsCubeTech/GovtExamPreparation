package com.govt_exam_preparation.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.ReviewQuizAdapter;
import com.govt_exam_preparation.model.QuizModel;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;

import java.util.ArrayList;

public class ReviewQuizViewPagerActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<QuizModel> arrayQuizModel = new ArrayList<>();

    RelativeLayout relSwipe;
    TextView txtOkGotIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_quiz_view_pager);

        init();

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Review Quiz");
        toolbarOperation.showBackButton(true);

        getQuizDetails();

        txtOkGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relSwipe.setVisibility(View.GONE);
                setUpViewPager();
            }
        });

    }

    public void init() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        relSwipe = (RelativeLayout) findViewById(R.id.relSwipe);
        txtOkGotIt = (TextView) findViewById(R.id.txtOkGotIt);
    }

    public void getQuizDetails() {
        arrayQuizModel = (ArrayList<QuizModel>) getIntent().getExtras().getSerializable("ArrayQuizModel");

    }

    public void setUpViewPager() {
        ReviewQuizAdapter adapter = new ReviewQuizAdapter(this, arrayQuizModel);
        viewPager.setAdapter(adapter);
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
