package com.govt_exam_preparation.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.govt_exam_preparation.AppController;
import com.govt_exam_preparation.Constants.QuesAndTheory;
import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.SingleSubjectSelectAdapter;
import com.govt_exam_preparation.dialogs.DialogMsg;
import com.govt_exam_preparation.model.QuizModel;
import com.govt_exam_preparation.model.SubjectModel;
import com.govt_exam_preparation.others.ConnectionDetector;
import com.govt_exam_preparation.others.MyProgress;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.sound.SoundPlay;
import com.govt_exam_preparation.sql.SqlClass;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;
import com.govt_exam_preparation.user_model.User;
import com.govt_exam_preparation.user_model.UserDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Exam_Instructions extends AppCompatActivity implements View.OnClickListener {


    RecyclerView recyclerView;
    TextView txtExamName;
    SubjectModel selectedSubjectModel = new SubjectModel();
    ArrayList<SubjectModel> arraySubjectModel = new ArrayList<>();
    SingleSubjectSelectAdapter adapter;


    String text = "", msgError = "";

    String correctAnsPoints = "0", wrongAnswerPoints = "0";


    ArrayList<QuizModel> arrayQuizModel = new ArrayList<>();


    Dialog progress;
    Button btnOk;

    DialogMsg dialogMsg;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_instructions);

        user = new UserDetails(this).getUserDetail();

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Instructions");
        toolbarOperation.showBackButton(true);


        btnOk = (Button) findViewById(R.id.btnOk);
        txtExamName = (TextView) findViewById(R.id.txtExamName);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewMySubjects();

        btnOk.setOnClickListener(this);
    }

    public void viewMySubjects() {
        //Log.v("Exam_Model", user.getExamModel().getExamId() + "\n" + user.getExamModel().getExamName());

        txtExamName.setText("Exam: " + user.getExamModel().getExamName());

        arraySubjectModel.clear();
        /*for (int i = 0; i < 10; i++) {
            SubjectModel model = new SubjectModel();
            model.setSubjectId("" + (i + 1));
            model.setSubjectName("Subject_" + (i + 1));
            model.setSelected(i == 0);
            arraySubjectModel.add(model);
        }*/

        arraySubjectModel = user.getExamModel().getArraySubjectModel();

        for (int i = 0; i < arraySubjectModel.size(); i++) {
            SubjectModel model = arraySubjectModel.get(i);
            Log.v("Subject_Model", model.getSubjectId() + "\n" + model.getSubjectName() + "\n" + model.getSelected());
            model.setSelected(i == 0);

            if (model.getSelected())
                selectedSubjectModel = model;

            arraySubjectModel.set(i, model);
        }

        adapter = new SingleSubjectSelectAdapter(this, arraySubjectModel);
        recyclerView.setAdapter(adapter);

    }

    public void getMeSelectedSubjectModel() {
        Boolean selected;
        for (SubjectModel subjectModel : arraySubjectModel) {
            selected = subjectModel.getSelected();
            if (selected) {
                selectedSubjectModel = subjectModel;
                break;
            }
        }
    }


    public void viewWhetherQuizOrTextVolley() {

        progress = new MyProgress(this).getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();

        String url = My_URL.viewQuestionOrTextStatus
                + "exam_id=" + user.getExamModel().getExamId()
                + "&subject_id=" + selectedSubjectModel.getSubjectId();

        Log.v("Url", url);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("result") == 1) {
                            JSONObject obj = json.getJSONObject("msg");
                            String statusQuizOrText = obj.getString("status");
                            if (statusQuizOrText.equalsIgnoreCase(QuesAndTheory.STATUS_QUIZ)) {
                                showQuizVolley();
                            } else if (statusQuizOrText.equalsIgnoreCase(QuesAndTheory.STATUS_THEORY)) {
                                if (progress.isShowing())
                                    progress.dismiss();
                                Intent intent = new Intent(Exam_Instructions.this, TheoryActivity.class);
                                intent.putExtra("SelectedSubjectModel", selectedSubjectModel);
                                startActivity(intent);
                            }
                        } else {
                            if (progress.isShowing())
                                progress.dismiss();

                            msgError = json.getString("message");
                            dialogMsg = new DialogMsg(Exam_Instructions.this);

                            if (msgError.equalsIgnoreCase("No Message!!") || msgError.equalsIgnoreCase(""))
                                msgError = "No theory or quiz on server!!";

                            dialogMsg.showDialog(msgError, true, R.drawable.ic_error_black_24dp);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progress.isShowing())
                    progress.dismiss();

                dialogMsg = new DialogMsg(Exam_Instructions.this);
                dialogMsg.showDialog(getString(R.string.networkError), true, R.drawable.ic_error_black_24dp);
            }
        });

        AppController.getInstance().addToRequestQueue(request, "ViewQuizTextStatus");
    }


    public void showQuizVolley() {
        arrayQuizModel.clear();

        final SqlClass sqlClass = new SqlClass(this, this);
        sqlClass.createTableQuizQuestions();

        String url = My_URL.viewQuestionOrText + QuesAndTheory.STATUS_QUIZ
                + "&exam_id=" + user.getExamModel().getExamId()
                + "&subject_id=" + selectedSubjectModel.getSubjectId();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progress.isShowing())
                    progress.dismiss();
                if (response != null) {
                    try {
                        response = Html.fromHtml(response).toString();
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("result") == 1) {
                            correctAnsPoints = json.getString(QuesAndTheory.KEY_CORRECT_POINTS);
                            wrongAnswerPoints = json.getString(QuesAndTheory.KEY_WRONG_POINTS);
                            JSONArray array = json.getJSONArray("msg");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                QuizModel model = new QuizModel();
                                model.setQuesId(obj.getString(QuesAndTheory.KEY_QUES_ID));
                                model.setQuesTitle(obj.getString(QuesAndTheory.KEY_QUES_TITLE));
                                model.setQuesImage(obj.getString(QuesAndTheory.KEY_QUES_IMAGE));
                                model.setQuesOption1(obj.getString(QuesAndTheory.KEY_QUES_OPTION_1));
                                model.setQuesOption2(obj.getString(QuesAndTheory.KEY_QUES_OPTION_2));
                                model.setQuesOption3(obj.getString(QuesAndTheory.KEY_QUES_OPTION_3));
                                model.setQuesOption4(obj.getString(QuesAndTheory.KEY_QUES_OPTION_4));
                                model.setQuesCorrectSerialNo(obj.getString(QuesAndTheory.KEY_QUES_ANS_SERIAL));
                                model.setQuesInputSerialNo("");

                                model.setFavorite(sqlClass.selectQueryCheckFavorite(model));

                                model.setExamModel(user.getExamModel());
                                model.setSubjectModel(selectedSubjectModel);

                                arrayQuizModel.add(model);
                            }
                            goToQuizActivity();
                        } else {
                            dialogMsg = new DialogMsg(Exam_Instructions.this);
                            dialogMsg.showDialog("No theory or quiz on server!", true, R.drawable.ic_error_black_24dp);
                        }
                    } catch (Exception e) {
                        Log.v("ParsingException", "" + e);
                        dialogMsg = new DialogMsg(Exam_Instructions.this);
                        dialogMsg.showDialog(getString(R.string.networkError), true, R.drawable.ic_error_black_24dp);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progress.isShowing())
                    progress.dismiss();
                dialogMsg = new DialogMsg(Exam_Instructions.this);
                dialogMsg.showDialog(getString(R.string.networkError), true, R.drawable.ic_error_black_24dp);
            }
        });

        AppController.getInstance().addToRequestQueue(request, "ViewQuizOrText");
    }


    public void goToQuizActivity() {
        Intent intent = new Intent(Exam_Instructions.this, QuizActivity.class);
        intent.putExtra("ArrayQuizModel", arrayQuizModel);
        intent.putExtra("CorrectAnswerPoints", correctAnsPoints);
        intent.putExtra("WrongAnswerPoints", wrongAnswerPoints);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                SoundPlay play = new SoundPlay(Exam_Instructions.this);
                play.playBtnClick();

                getMeSelectedSubjectModel();

                if (new ConnectionDetector(this).isConnectingToInternet())
                    viewWhetherQuizOrTextVolley();
                else {
                    dialogMsg = new DialogMsg(Exam_Instructions.this);
                    dialogMsg.showDialog(getString(R.string.connectionError), true, R.drawable.ic_error_black_24dp);
                }
                break;
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
