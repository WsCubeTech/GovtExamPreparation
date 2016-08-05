package com.govt_exam_preparation.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.QuizModel;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.sql.SqlClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by wscubetech on 27/6/16.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    ArrayList<QuizModel> arrayQuizModel = new ArrayList<>();
    Activity activity;

    public FavoritesAdapter(Activity activity, ArrayList<QuizModel> arrayQuizModel) {
        this.activity = activity;
        this.arrayQuizModel = arrayQuizModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.row_favorites, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final QuizModel quizModel = arrayQuizModel.get(position);

        holder.txtInfo.setText(quizModel.getExamModel().getExamName() + "  " + quizModel.getSubjectModel().getSubjectName());
        holder.txtQuestion.setText("Ques: " + quizModel.getQuesTitle());

        String answer = "";
        switch (quizModel.getQuesCorrectSerialNo()) {
            case "1":
                answer = quizModel.getQuesOption1();
                break;
            case "2":
                answer = quizModel.getQuesOption2();
                break;
            case "3":
                answer = quizModel.getQuesOption3();
                break;
            case "4":
                answer = quizModel.getQuesOption4();
                break;
        }

        holder.txtAnswer.setText("Ans: " + answer);

        int imageRes = quizModel.getFavorite() ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_border_white_24dp;
        holder.imgFavorite.setImageResource(imageRes);

        if (quizModel.getQuesImage().trim().length() > 1) {
            holder.imgQuestion.setVisibility(View.VISIBLE);
            Picasso.with(activity)
                    .load(My_URL.image_url + quizModel.getQuesImage())
                    .placeholder(R.drawable.ic_photo_gallery)
                    .into(holder.imgQuestion);
        } else {
            holder.imgQuestion.setVisibility(View.GONE);
        }

        holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizModel model = arrayQuizModel.get(position);
                model.setFavorite(!model.getFavorite());
                arrayQuizModel.set(position, model);

                SqlClass sqlClass = new SqlClass(activity, activity);
                sqlClass.checkToInsertOrUpdate(model);

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayQuizModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestion, txtAnswer, txtInfo;
        ImageView imgFavorite, imgQuestion;

        public ViewHolder(View v) {
            super(v);
            txtQuestion = (TextView) v.findViewById(R.id.txtQuestion);
            txtAnswer = (TextView) v.findViewById(R.id.txtAnswer);
            txtInfo = (TextView) v.findViewById(R.id.txtInfo);
            imgFavorite = (ImageView) v.findViewById(R.id.imgFavorite);
            imgQuestion = (ImageView) v.findViewById(R.id.imgQuestion);
        }
    }
}
