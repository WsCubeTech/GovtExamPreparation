package com.govt_exam_preparation.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.OptionModel;
import com.govt_exam_preparation.model.QuizModel;
import com.govt_exam_preparation.others.My_URL;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * Created by wscubetech on 24/6/16.
 */
public class ReviewQuizAdapter extends PagerAdapter {

    LayoutInflater inflater;
    Activity activity;
    ArrayList<QuizModel> arrayQuizModel = new ArrayList<>();

    public ReviewQuizAdapter(Activity activity, ArrayList<QuizModel> arrayQuizModel) {
        this.arrayQuizModel = arrayQuizModel;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*for (QuizModel model : arrayQuizModel) {
            Log.v("InputNCorrectSerialNo", model.getQuesInputSerialNo() +
                    "_" + model.getQuesCorrectSerialNo());
        }*/
    }

    @Override
    public int getCount() {
        return arrayQuizModel.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = inflater.inflate(R.layout.row_review_view_pager, container, false);
        initAndSet(v, position);
        container.addView(v);
        return v;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void initAndSet(View v, int position) {

        LinearLayout linNext;
        TextView txtQuestion, txtNext;
        ImageView imgQuestion;
        RecyclerView recyclerView;
        txtQuestion = (TextView) v.findViewById(R.id.txtQuestion);
        imgQuestion = (ImageView) v.findViewById(R.id.imgQuestion);
        txtNext = (TextView) v.findViewById(R.id.txtNext);
        linNext = (LinearLayout) v.findViewById(R.id.linNext);

        linNext.setVisibility(View.GONE);
        txtNext.setVisibility(View.GONE);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        QuizModel quizModel = arrayQuizModel.get(position);
        txtQuestion.setText("Ques: " + quizModel.getQuesTitle());

        String imageQues = quizModel.getQuesImage().trim();
        if (imageQues.length() > 1) {
            imgQuestion.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 1.5f);
            recyclerView.setLayoutParams(params);

            params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 0.7f);
            imgQuestion.setLayoutParams(params);

            recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
            Picasso.with(activity)
                    .load(My_URL.image_url + imageQues)
                    .resize(480, 256)
                    .placeholder(R.drawable.ic_photo_size_select_actual_white)
                    .into(imgQuestion);
        } else {
            imgQuestion.setVisibility(View.GONE);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 2f);
            recyclerView.setLayoutParams(params);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));

            params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 0f);
            imgQuestion.setLayoutParams(params);


        }

        getOptionsNow(quizModel, recyclerView, position);

    }

    public void getOptionsNow(QuizModel quizModel, RecyclerView recyclerView, int position) {
        recyclerView.setHasFixedSize(true);
        ArrayList<OptionModel> arrayOptionModel = arrayQuizModel.get(position).getArrayOptionModel();
        for (int i = 0; i < arrayOptionModel.size(); i++) {
            final OptionModel optionModel = arrayOptionModel.get(i);

            if (quizModel.getQuesCorrectSerialNo().equalsIgnoreCase("" + (i + 1))) {
                optionModel.setCorrect(true);
            } else {
                optionModel.setCorrect(false);
            }

            arrayOptionModel.set(i, optionModel);

        }


        OptionAdapter adapter = new OptionAdapter(activity, arrayOptionModel, "Review");
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setDuration(900);
        recyclerView.setAdapter(animationAdapter);
    }

}
