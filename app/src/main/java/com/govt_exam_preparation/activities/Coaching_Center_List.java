package com.govt_exam_preparation.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.Coaching_List_Adapter;
import com.govt_exam_preparation.model.Coaching_List_Model;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;

import java.util.ArrayList;


public class Coaching_Center_List extends AppCompatActivity {

    RecyclerView recyclerView;
    Coaching_List_Adapter adapter;
    RecyclerView.LayoutManager mLayoutManager;


    ArrayList<Coaching_List_Model> coaching_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaching_center_list);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Coaching Center List");
        toolbarOperation.showBackButton(true);

        coaching_list = getIntent().getExtras().getParcelableArrayList("center_list");


        mLayoutManager = new LinearLayoutManager(Coaching_Center_List.this);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter  = new Coaching_List_Adapter(Coaching_Center_List.this, coaching_list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(Coaching_Center_List.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(Coaching_Center_List.this,Coaching_Center_Detail.class);
                i.putExtra("details",coaching_list.get(position));
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


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
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
