package com.govt_exam_preparation.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.OptionAdapter;
import com.govt_exam_preparation.custom_views.MyChronometer;
import com.govt_exam_preparation.dialogs.DialogPointsInfo;
import com.govt_exam_preparation.model.OptionModel;
import com.govt_exam_preparation.model.QuizModel;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.sql.SqlClass;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtQuestion, txtNext;
    ImageView imgQuestion, imgFavorite;
    RecyclerView recyclerView;

    ArrayList<QuizModel> arrayQuizModel = new ArrayList<>();

    OptionAdapter adapter;

    MyChronometer chronometer;

    int currentQuesIndex = 0;
    String correctAnsPoints = "", wrongAnsPoints = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quiz);

        SqlClass sqlClass = new SqlClass(this, this);
        sqlClass.createTableQuizQuestions();

        init();
        getAllQuestions();
        onClickListeners();
    }

    public void init() {
        chronometer = (MyChronometer) findViewById(R.id.chronometer);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtNext = (TextView) findViewById(R.id.txtNext);
        imgQuestion = (ImageView) findViewById(R.id.imgQuestion);
        imgFavorite = (ImageView) findViewById(R.id.imgFavorite);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    public void onClickListeners() {
        txtNext.setOnClickListener(this);
        imgFavorite.setOnClickListener(this);
    }

    public void getAllQuestions() {
        correctAnsPoints = getIntent().getExtras().getString("CorrectAnswerPoints");
        wrongAnsPoints = getIntent().getExtras().getString("WrongAnswerPoints");
        arrayQuizModel = (ArrayList<QuizModel>) getIntent().getExtras().getSerializable("ArrayQuizModel");

        //Toast.makeText(this, "" + correctAnsPoints + "\n" + wrongAnsPoints, Toast.LENGTH_SHORT).show();
        DialogPointsInfo dialogPointsInfo = new DialogPointsInfo(this);
        dialogPointsInfo.showDialog(correctAnsPoints, wrongAnsPoints);
        dialogPointsInfo.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                showNextQuestion();
            }
        });
    }

    public void showNextQuestion() {
        getFavoriteStatus();

        recyclerView.setVisibility(View.GONE);

        final QuizModel model = arrayQuizModel.get(currentQuesIndex);
        txtQuestion.setText("Ques: " + model.getQuesTitle());

        txtNext.setClickable(false);

        String imageQues = model.getQuesImage().trim();
        if (imageQues.length() > 1) {
            imgQuestion.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 1.3f);
            recyclerView.setLayoutParams(params);

            params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 0.7f);
            imgQuestion.setLayoutParams(params);

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            Picasso.with(this)
                    .load(My_URL.image_url + imageQues)
                    .resize(480, 256)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.ic_photo_size_select_actual_white)
                    .into(imgQuestion, new Callback() {
                        @Override
                        public void onSuccess() {
                            showOptionsAfterLoadingImageIfThere(model);
                            chronometer.start();
                            txtNext.setClickable(true);
                        }

                        @Override
                        public void onError() {
                            showOptionsAfterLoadingImageIfThere(model);
                            chronometer.start();
                            txtNext.setClickable(true);
                        }
                    });
        } else {
            imgQuestion.setVisibility(View.GONE);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 2.0f);
            recyclerView.setLayoutParams(params);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 0f);
            imgQuestion.setLayoutParams(params);

            showOptionsAfterLoadingImageIfThere(model);
            chronometer.start();
            txtNext.setClickable(true);
        }


    }

    public void showOptionsAfterLoadingImageIfThere(QuizModel model) {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        ArrayList<OptionModel> arrayOptionModel = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final OptionModel optionModel = new OptionModel();
            switch (i) {
                case 0:
                    optionModel.setOption(model.getQuesOption1());
                    break;
                case 1:
                    optionModel.setOption(model.getQuesOption2());
                    break;
                case 2:
                    optionModel.setOption(model.getQuesOption3());
                    break;
                case 3:
                    optionModel.setOption(model.getQuesOption4());
                    break;
            }

            optionModel.setMyInput(false);
            arrayOptionModel.add(optionModel);

        }

        model.setArrayOptionModel(arrayOptionModel);
        arrayQuizModel.set(currentQuesIndex, model);

        adapter = new OptionAdapter(this, arrayOptionModel, "Quiz");
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setDuration(900);
        recyclerView.setAdapter(animationAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtNext:
                if (currentQuesIndex < arrayQuizModel.size() - 1) {
                    currentQuesIndex += 1;
                    chronometer.stop();
                    showNextQuestion();
                } else {
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra("ArrayQuizModel", arrayQuizModel);
                    intent.putExtra("CorrectAnswerPoints", correctAnsPoints);
                    intent.putExtra("WrongAnswerPoints", wrongAnsPoints);
                    intent.putExtra("TimeTaken", chronometer.getText().toString() + "");
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.imgFavorite:
                QuizModel quizModel = arrayQuizModel.get(currentQuesIndex);
                quizModel.setFavorite(!quizModel.getFavorite());
                int imageRes = quizModel.getFavorite()
                        ? R.drawable.ic_favorite_white_24dp
                        : R.drawable.ic_favorite_border_white_24dp;
                imgFavorite.setImageResource(imageRes);
                arrayQuizModel.set(currentQuesIndex, quizModel);

                SqlClass sqlClass = new SqlClass(this, this);
                sqlClass.checkToInsertOrUpdate(quizModel);

                break;
        }
    }

    public void getFavoriteStatus() {
        QuizModel quizModel = arrayQuizModel.get(currentQuesIndex);
        int imageRes = quizModel.getFavorite()
                ? R.drawable.ic_favorite_white_24dp
                : R.drawable.ic_favorite_border_white_24dp;
        imgFavorite.setImageResource(imageRes);
    }


}
