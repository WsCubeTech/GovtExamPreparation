package com.govt_exam_preparation.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.govt_exam_preparation.adapter.Notification_Adapter;
import com.govt_exam_preparation.model.Notification_Model;
import com.govt_exam_preparation.others.CheckNetwork;
import com.govt_exam_preparation.others.MyProgress;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;



public class Notification extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Notification_Adapter adapter;
    Dialog progress;

    ArrayList<Notification_Model> notification_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Notifications");
        toolbarOperation.showBackButton(true);

        if (new CheckNetwork(getApplicationContext()).isNetworkOnline()) {
            get_notification();
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(Notification.this, recyclerView, new Jobs_List.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(Notification.this, Notification_Detail.class);
                i.putExtra("details", notification_list.get(position));
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void get_notification() {

        progress = new MyProgress(Notification.this).getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();

        StringRequest request = new StringRequest(Request.Method.GET, My_URL.view_notification, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                if (s != null) {
                    notification_list.clear();
                    s = Html.fromHtml(s).toString();
                    Log.d("notification_response", s);
                    try {
                        JSONObject json = new JSONObject(s);

                        if (json.getInt("result") == 1) {

                            JSONArray msg_array = json.getJSONArray("msg");

                            for (int i = 0; i < msg_array.length(); i++) {
                                JSONObject obj = msg_array.getJSONObject(i);
                                Notification_Model model = new Notification_Model();
                                model.setId(obj.getString("notification_id"));
                                model.setTitle(obj.getString("notification_text"));
                                model.setDescription(obj.getString("notification_description"));
                                model.setDate(obj.getString("notification_date"));
                                model.setImage(obj.getString("notification_image"));
                                notification_list.add(model);
                            }

                            adapter = new Notification_Adapter(Notification.this, notification_list);
                            recyclerView.setAdapter(adapter);

                        }

                    } catch (Exception e) {
                        Log.v("exception", "" + e);
                    }


                }
                if (progress.isShowing())
                    progress.dismiss();


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


    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Jobs_List.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Jobs_List.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
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
