package com.govt_exam_preparation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.govt_exam_preparation.R;
import com.govt_exam_preparation.activities.TheoryDetailActivity;
import com.govt_exam_preparation.model.TheoryModel;

import java.util.ArrayList;

/**
 * Created by wscubetech on 26/6/16.
 */
public class TheoryTitleAdapter extends RecyclerView.Adapter<TheoryTitleAdapter.ViewHolder> {

    ArrayList<TheoryModel> arrayTheoryModel = new ArrayList<>();
    Activity activity;

    public TheoryTitleAdapter(Activity activity, ArrayList<TheoryModel> arrayTheoryModel) {
        this.arrayTheoryModel = arrayTheoryModel;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.row_theory_title, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TheoryModel model = arrayTheoryModel.get(position);
        holder.txtTitle.setText(model.getTitle());
        holder.txtSerialNo.setText("" + (position + 1));
        holder.linParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, TheoryDetailActivity.class);
                intent.putExtra("TheoryModel", model);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayTheoryModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtSerialNo;
        LinearLayout linParent;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtSerialNo = (TextView) v.findViewById(R.id.txtSerialNo);
            linParent = (LinearLayout) v.findViewById(R.id.linParent);
        }
    }
}
