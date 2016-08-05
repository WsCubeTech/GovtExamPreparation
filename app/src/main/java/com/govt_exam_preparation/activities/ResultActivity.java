package com.govt_exam_preparation.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.OptionModel;
import com.govt_exam_preparation.model.QuizModel;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linRetake, linReview;
    LinearLayout linRetakeReview;
    CardView cardView;

    ArrayList<QuizModel> arrayQuizModel = new ArrayList<>();

    TextView txtTotal, txtCorrect, txtIncorrect, txtSkipped, txtTotalPoints, txtTimeTaken;

    String correctPoints = "", incorrectPoints = "", timeTaken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_new);
        init();

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Results");
        toolbarOperation.showBackButton(true);

        getResultDetails();
        onClickListeners();
        calculateAndSetPoints();

        cardView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in_bounce_effect));
        linRetakeReview.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in_bounce_effect));
    }

    public void getResultDetails() {
        arrayQuizModel = (ArrayList<QuizModel>) getIntent().getExtras().getSerializable("ArrayQuizModel");
        correctPoints = getIntent().getExtras().getString("CorrectAnswerPoints");
        incorrectPoints = getIntent().getExtras().getString("WrongAnswerPoints");
        timeTaken = getIntent().getExtras().getString("TimeTaken");
    }

    public void init() {
        cardView = (CardView) findViewById(R.id.card);
        linRetakeReview = (LinearLayout) findViewById(R.id.linRetakeReview);
        txtTotalPoints = (TextView) findViewById(R.id.txtTotalPoints);
        txtTimeTaken = (TextView) findViewById(R.id.txtTimeTaken);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtCorrect = (TextView) findViewById(R.id.txtCorrect);
        txtIncorrect = (TextView) findViewById(R.id.txtIncorrect);
        txtSkipped = (TextView) findViewById(R.id.txtSkipped);
        linRetake = (LinearLayout) findViewById(R.id.linRetake);
        linReview = (LinearLayout) findViewById(R.id.linReview);

    }

    public void onClickListeners() {
        linReview.setOnClickListener(this);
        linRetake.setOnClickListener(this);
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

    public void calculateAndSetPoints() {
        float total = 0;
        int correctNo = 0, incorrectNo = 0, skippedNo = 0, totalQues = 0;
        float correct = Float.parseFloat(correctPoints);
        float incorrect = Float.parseFloat(incorrectPoints);
        totalQues = arrayQuizModel.size();
        for (QuizModel model : arrayQuizModel) {
            for (OptionModel optionModel : model.getArrayOptionModel()) {
                Log.v("TagCorrect", optionModel.getCorrect() + "");
            }
        }

        for (QuizModel model : arrayQuizModel) {
            int flagInput = 0;
            ArrayList<OptionModel> arrayOptions = model.getArrayOptionModel();
            for (int i = 0; i < arrayOptions.size(); i++) {
                OptionModel optionModel = arrayOptions.get(i);
                int correctSerialNo = Integer.parseInt(model.getQuesCorrectSerialNo()) - 1;
                if (optionModel.getMyInput() && correctSerialNo == i) {
                    total += correct;
                    correctNo += 1;
                    flagInput = 1;
                    break;
                } else if (optionModel.getMyInput()) {
                    total -= incorrect;
                    incorrectNo += 1;
                    flagInput = 1;
                    break;
                }
            }

            if (flagInput == 0) {
                skippedNo += 1;
            }
        }

        txtTotalPoints.setText(total + " / " + (totalQues * Integer.parseInt(correctPoints)));
        txtSkipped.setText("" + skippedNo);
        txtCorrect.setText("" + correctNo);
        txtIncorrect.setText("" + incorrectNo);
        txtTotal.setText("" + totalQues);
        txtTimeTaken.setText("Time Taken: " + timeTaken);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.linReview:
                intent = new Intent(this, ReviewQuizViewPagerActivity.class);
                intent.putExtra("ArrayQuizModel", arrayQuizModel);
                startActivity(intent);
                break;
            case R.id.linRetake:
                intent = new Intent(this, QuizActivity.class);
                intent.putExtra("ArrayQuizModel", arrayQuizModel);
                intent.putExtra("CorrectAnswerPoints", correctPoints);
                intent.putExtra("WrongAnswerPoints", incorrectPoints);
                startActivity(intent);
                finish();
                break;
        }
    }
}
