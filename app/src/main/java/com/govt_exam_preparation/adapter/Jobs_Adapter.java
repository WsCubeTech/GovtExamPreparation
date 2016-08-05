package com.govt_exam_preparation.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.Job_Detail;

import java.util.ArrayList;


/**
 * Created by wscubetech on 8/3/16.
 */
public class Jobs_Adapter extends RecyclerView.Adapter<Jobs_Adapter.ViewHolder> {
    ArrayList<Job_Detail> job_list;

    Activity act;

    public Jobs_Adapter(Activity act, ArrayList<Job_Detail> job_list_){
        this.act = act;
        this.job_list = job_list_;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.exam_title.setText(job_list.get(position).getTitle());
        holder.post.setText("Posts -: " + job_list.get(position).getPost() );
        holder.last_date.setText(job_list.get(position).getLast_date());
    }

    @Override
    public int getItemCount() {
        return job_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView exam_title,post,last_date;

        public ViewHolder(View v) {
            super(v);

            exam_title=(TextView)v.findViewById(R.id.exam_title);
            post=(TextView)v.findViewById(R.id.post);
            last_date = (TextView)v.findViewById(R.id.last_date);
        }
    }

}

