package com.govt_exam_preparation.activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.govt_exam_preparation.AppController;
import com.govt_exam_preparation.Constants.QuesAndTheory;
import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.TheoryTitleAdapter;
import com.govt_exam_preparation.dialogs.DialogMsg;
import com.govt_exam_preparation.model.SubjectModel;
import com.govt_exam_preparation.model.TheoryDetailModel;
import com.govt_exam_preparation.model.TheoryModel;
import com.govt_exam_preparation.others.MyProgress;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;
import com.govt_exam_preparation.user_model.User;
import com.govt_exam_preparation.user_model.UserDetails;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TheoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Dialog progress;
    DialogMsg dialogMsg;

    ArrayList<TheoryModel> arrayTheoryModel = new ArrayList<>();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);
        user = new UserDetails(this).getUserDetail();
        init();
        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Theory");
        toolbarOperation.showBackButton(true);

        getSelectedSubjectModel();
    }

    public void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getSelectedSubjectModel() {
        SubjectModel subjectModel = (SubjectModel) getIntent().getExtras().getSerializable("SelectedSubjectModel");
        viewTheoryVolley(subjectModel);
    }

    public void viewTheoryVolley(SubjectModel subjectModel) {

        arrayTheoryModel.clear();

        progress = new MyProgress(this).getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();

        String url = My_URL.viewQuestionOrText + QuesAndTheory.STATUS_THEORY
                + "&exam_id=" + user.getExamModel().getExamId()
                + "&subject_id=" + subjectModel.getSubjectId();

        Log.v("urlTheory", "" + url);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progress.isShowing())
                    progress.dismiss();

                if (response != null) {
                    try {
                        //response = Html.fromHtml(response).toString();
                        Log.v("Response", "" + response);
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("result") == 1) {
                            JSONArray array = json.getJSONArray("msg");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                final TheoryModel theoryModel = new TheoryModel();
                                theoryModel.setTitle(obj.getString(QuesAndTheory.KEY_THEORY_MAIN_TITLE));

                                ArrayList<TheoryDetailModel> arrayTheoryDetailModel = new ArrayList<>();
                                JSONArray array2 = obj.getJSONArray("theory");
                                for (int j = 0; j < array2.length(); j++) {
                                    JSONObject object = array2.getJSONObject(j);
                                    TheoryDetailModel detailModel = new TheoryDetailModel();
                                    detailModel.setTheoryId(object.getString(QuesAndTheory.KEY_THEORY_ID));
                                    detailModel.setTheoryTitle(object.getString(QuesAndTheory.KEY_THEORY_TITLE));
                                    detailModel.setTheoryDesc(object.getString(QuesAndTheory.KEY_THEORY_DESC));
                                    detailModel.setTheoryImage(object.getString(QuesAndTheory.KEY_THEORY_IMAGE));
                                    arrayTheoryDetailModel.add(detailModel);
                                }

                                theoryModel.setArrayTheoryDetailModel(arrayTheoryDetailModel);

                                arrayTheoryModel.add(theoryModel);

                            }

                            TheoryTitleAdapter adapter = new TheoryTitleAdapter(TheoryActivity.this, arrayTheoryModel);
                            recyclerView.setAdapter(adapter);

                        }
                    } catch (Exception e) {
                        Log.v("Exception", "" + e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progress.isShowing())
                    progress.dismiss();
                dialogMsg = new DialogMsg(TheoryActivity.this);
                dialogMsg.showDialog(getString(R.string.networkError), true, R.drawable.ic_error_black_24dp);
            }
        });

        AppController.getInstance().addToRequestQueue(request, "ViewTheory");
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
