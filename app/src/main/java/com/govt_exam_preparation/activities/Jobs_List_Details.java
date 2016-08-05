package com.govt_exam_preparation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.Job_Detail_Adapter;
import com.govt_exam_preparation.model.Details_Model;
import com.govt_exam_preparation.model.Job_Detail;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;

import java.util.ArrayList;



public class Jobs_List_Details extends AppCompatActivity {


    TextView job_title, post, description;
    ListView job_details_layout;
    ArrayList<Details_Model> job_details = new ArrayList<>();
    Job_Detail_Adapter job_detail_adapter;
    Job_Detail details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list_details);

        init();

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Vacancy Details");
        toolbarOperation.showBackButton(true);

        setListViewHeightBasedOnChildren(job_details_layout);

        details = getIntent().getExtras().getParcelable("details");

        job_title.setText(details.getTitle());
        post.setText(details.getPost());
        description.setText(details.getDescrition());


        job_details.add(new Details_Model(R.drawable.last_date_icon, "Last Date:", details.getLast_date()));
        job_details.add(new Details_Model(R.drawable.eligibility_icon, "Eligibility:", details.getEligibility()));
        job_details.add(new Details_Model(R.drawable.salary_range, "Salary Range:", "₹" + details.getSalary_range()));
        job_details.add(new Details_Model(R.drawable.fees_icon, "Fees:", "₹" + details.getFees()));
        job_details.add(new Details_Model(R.drawable.website_icon, "Official Website:", details.getUrl_Official_Site()));

        job_detail_adapter = new Job_Detail_Adapter(Jobs_List_Details.this, R.layout.row_job_detail, job_details);
        job_details_layout.setAdapter(job_detail_adapter);

        setListViewHeightBasedOnChildren(job_details_layout);

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public void init() {
        job_title = (TextView) findViewById(R.id.job_title);
        post = (TextView) findViewById(R.id.post);
        description = (TextView) findViewById(R.id.description);
        job_details_layout = (ListView) findViewById(R.id.job_details_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
