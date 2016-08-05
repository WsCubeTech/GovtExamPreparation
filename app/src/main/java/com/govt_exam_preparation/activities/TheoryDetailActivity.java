package com.govt_exam_preparation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.TheoryDetailAdapter;
import com.govt_exam_preparation.model.TheoryModel;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;


public class TheoryDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory_detail);

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Theory Detail");
        toolbarOperation.showBackButton(true);

        init();
        getAndSetDetail();

    }

    public void init() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getAndSetDetail() {
        TheoryModel theoryModel = (TheoryModel) getIntent().getExtras().getSerializable("TheoryModel");
        txtTitle.setText(Html.fromHtml(theoryModel.getTitle()));
        TheoryDetailAdapter adapter = new TheoryDetailAdapter(this, theoryModel.getArrayTheoryDetailModel());
        recyclerView.setAdapter(adapter);
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
