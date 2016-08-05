package com.govt_exam_preparation.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.SubjectModel;

import java.util.ArrayList;

/**
 * Created by wscubetech on 21/6/16.
 */
public class SubjectSelectAdapter extends RecyclerView.Adapter<SubjectSelectAdapter.ViewHolder> {

    ArrayList<SubjectModel> arraySubjectModel = new ArrayList<>();
    Activity activity;

    public SubjectSelectAdapter(Activity activity, ArrayList<SubjectModel> arraySubjectModel) {
        this.activity = activity;
        this.arraySubjectModel = arraySubjectModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_subject_multi_select, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubjectModel model = arraySubjectModel.get(position);
        holder.txtSubjectName.setText(model.getSubjectName());
        holder.linSubject.setOnClickListener(model.getOnClickListener());

        int imageRes = model.getSelected()
                ? R.drawable.ic_check_box_black_24dp
                : R.drawable.ic_check_box_outline_blank_black_24dp;

        holder.imgChecked.setImageResource(imageRes);

        if (model.getUniversal())
            holder.txtUniversal.setVisibility(View.VISIBLE);
        else
            holder.txtUniversal.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return arraySubjectModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linSubject;
        ImageView imgChecked;
        TextView txtSubjectName, txtUniversal;

        public ViewHolder(View v) {
            super(v);
            linSubject = (LinearLayout) v.findViewById(R.id.linSubject);
            imgChecked = (ImageView) v.findViewById(R.id.imgChecked);
            txtSubjectName = (TextView) v.findViewById(R.id.txtSubjectName);
            txtUniversal = (TextView) v.findViewById(R.id.txtUniversal);
        }
    }
}
