package com.govt_exam_preparation.activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.govt_exam_preparation.AppController;
import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.ViewPagerAdapter;
import com.govt_exam_preparation.model.Center_Detail_Model;
import com.govt_exam_preparation.model.Coaching_List_Model;
import com.govt_exam_preparation.model.Subjects_Sub_Category;
import com.govt_exam_preparation.model.Teachers_Sub_Category;
import com.govt_exam_preparation.others.MyProgress;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Coaching_Center_Detail extends AppCompatActivity {

    ScrollView scrollview;
    private ViewFlipper mViewFlipper;
    private GestureDetector mGestureDetector;

    private ViewPager viewPager;

    ImageView imgCoachingCenter;

    Dialog progress;


    Snackbar snackbar;
    LinearLayout main_layout;

    String center_id = "";

    TextView center_name, address;

    Coaching_List_Model center_detail;


    ArrayList<Center_Detail_Model> center_list;
    ArrayList<Subjects_Sub_Category> subject_list;
    ArrayList<Teachers_Sub_Category> teacher_list;

    ArrayList<String> arrayImages = new ArrayList<>();

    int result = 0;

    ExpandableListView mExpandableListView;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaching_center_details);

        init();

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar(getString(R.string.coaching_center_detail));
        toolbarOperation.showBackButton(true);

        center_detail = getIntent().getExtras().getParcelable("details");

        center_name.setText(center_detail.getName());
        address.setText(center_detail.getAddress());

        arrayImages.add(center_detail.getImage());
        DrawerCategory(My_URL.coaching_info + center_detail.getId());

        Log.d("Url", My_URL.coaching_info + center_detail.getId());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }


    public void DrawerCategory(String url) {

        result = 0;

        progress = new MyProgress(Coaching_Center_Detail.this).getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s != null) {
                    s = Html.fromHtml(s).toString();

                    Log.d("response", s);
                    try {
                        JSONObject json = new JSONObject(s);

                        if (json.getInt("result") == 1) {

                            JSONObject msg_obj = json.getJSONObject("msg");

                            JSONArray coaching_images = msg_obj.getJSONArray("coaching_gallery");


                            for (int i = 0; i < coaching_images.length(); i++) {
                                arrayImages.add(coaching_images.getJSONObject(i).getString("coaching_image"));
                                Picasso.with(Coaching_Center_Detail.this)
                                        .invalidate(My_URL.image_url + arrayImages.get(i));
                            }

                            String coaching_center_id = json.getJSONObject("msg").getString("coaching_id");
                            String coaching_center_name = json.getJSONObject("msg").getString("coaching_name");
                            String coaching_center_address = json.getJSONObject("msg").getString("coaching_address");
                            String coaching_center_number = json.getJSONObject("msg").getString("coaching_phone");

                            JSONArray coaching_courses_array = msg_obj.getJSONArray("coaching_cources");
                            Log.d("coaching_courses_array", coaching_courses_array.toString());

                            center_list = new ArrayList<>();

                            for (int i = 0; i < coaching_courses_array.length(); i++) {

                                subject_list = new ArrayList<>();

                                JSONArray courses_sub_array = coaching_courses_array.getJSONObject(i).getJSONArray("coaching_cources_sub");
                                Log.d("courses_sub_array", courses_sub_array.toString());

                                for (int j = 0; j < courses_sub_array.length(); j++) {


                                    teacher_list = new ArrayList<>();

                                    JSONArray teacher_array = courses_sub_array.getJSONObject(j).getJSONArray("coaching_cources_sub_teacher");
                                    Log.d("teacher_array", teacher_array.toString());

                                    for (int k = 0; k < teacher_array.length(); k++) {

                                        JSONObject ob = teacher_array.getJSONObject(k);

                                        teacher_list.add(new Teachers_Sub_Category(j,
                                                ob.getString("coaching_teacher_id"),
                                                ob.getString("coaching_teacher_name"),
                                                ob.getString("coaching_teacher_qualification")));

                                    }

                                    subject_list.add(new Subjects_Sub_Category(i,
                                            courses_sub_array.getJSONObject(j).getString("coaching_cources_sub_id"),
                                            courses_sub_array.getJSONObject(j).getString("coaching_cources_sub_name"),
                                            teacher_list));

                                }
                                center_list.add(new Center_Detail_Model(coaching_center_id,
                                        coaching_center_name,
                                        coaching_center_address,
                                        coaching_center_number,
                                        coaching_courses_array.getJSONObject(i).getString("coaching_cources_id"),
                                        coaching_courses_array.getJSONObject(i).getString("coaching_cources_name"),
                                        subject_list));
                            }

                            showViewPagerImagesCoachingCenter(arrayImages);

                        } else {

                            Toast.makeText(Coaching_Center_Detail.this, json.getString("msg"), Toast.LENGTH_LONG).show();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
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

        AppController.getInstance().addToRequestQueue(request, "Category");

    }


    public void init() {

        // Get the ViewFlipper
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imgCoachingCenter = (ImageView) findViewById(R.id.imgCoachingCenter);
        main_layout = (LinearLayout) findViewById(R.id.main_layout);

        mExpandableListView = (ExpandableListView) findViewById(R.id.expandable_list);

        center_name = (TextView) findViewById(R.id.center_name);
        address = (TextView) findViewById(R.id.address);
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

    public void showViewPagerImagesCoachingCenter(ArrayList<String> arrayImages) {
        if (arrayImages.size() > 0) {
            viewPager.setVisibility(View.VISIBLE);
            imgCoachingCenter.setVisibility(View.GONE);
            ViewPagerAdapter adapter = new ViewPagerAdapter(Coaching_Center_Detail.this, arrayImages, center_detail);
            viewPager.setAdapter(adapter);
        } else {
            viewPager.setVisibility(View.GONE);
            imgCoachingCenter.setVisibility(View.VISIBLE);
        }
    }


}
