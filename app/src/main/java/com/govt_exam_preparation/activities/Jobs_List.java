package com.govt_exam_preparation.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.govt_exam_preparation.AppController;
import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.Jobs_Adapter;
import com.govt_exam_preparation.model.Job_Detail;
import com.govt_exam_preparation.others.MyProgress;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Jobs_List extends AppCompatActivity {

    RecyclerView recyclerView;
    Jobs_Adapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    Dialog progress;

    ArrayList<Job_Detail> job_detail_array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar(getString(R.string.jobs));
        toolbarOperation.showBackButton(true);

        mLayoutManager = new LinearLayoutManager(Jobs_List.this);
        recyclerView.setLayoutManager(mLayoutManager);

        get_job_list();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(Jobs_List.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(Jobs_List.this, Jobs_List_Details.class);
                i.putExtra("details", job_detail_array.get(position));
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    public void get_job_list() {

        progress = new MyProgress(Jobs_List.this).getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();

        StringRequest request = new StringRequest(Request.Method.POST, My_URL.job_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.d("notification response", s);

                if (s != null) {
                    s = Html.fromHtml(s).toString();
                    try {
                        JSONObject json = new JSONObject(s);

                        if (json.getString("result").equalsIgnoreCase("1")) {
                            JSONArray msg_array = json.getJSONArray("msg");

                            for (int i = 0; i < msg_array.length(); i++) {
                                job_detail_array.add(new Job_Detail(msg_array.getJSONObject(i).getString("job_title"),
                                        msg_array.getJSONObject(i).getString("job_post"),
                                        msg_array.getJSONObject(i).getString("job_eligibility"),
                                        msg_array.getJSONObject(i).getString("job_salary_range"),
                                        msg_array.getJSONObject(i).getString("job_fees"),
                                        msg_array.getJSONObject(i).getString("job_last_date"),
                                        msg_array.getJSONObject(i).getString("job_descrition"),
                                        msg_array.getJSONObject(i).getString("job_official_site")));
                            }

                            Log.d("Size", job_detail_array.size() + "");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("Size", job_detail_array.size() + "");
                        //                      new MyToast(getActivity()).showToast("Parsing error");
                    }


                }
                if (progress.isShowing())
                    progress.dismiss();

                adapter = new Jobs_Adapter(Jobs_List.this, job_detail_array);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progress.isShowing())
                    progress.dismiss();
            }
        });

        AppController.getInstance().addToRequestQueue(request, "ViewNotifications");
    }


    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

//                @Override
//                public void onLongPress(MotionEvent e) {
//                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                    if (child != null && clickListener != null) {
//                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
//                    }
//                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


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
