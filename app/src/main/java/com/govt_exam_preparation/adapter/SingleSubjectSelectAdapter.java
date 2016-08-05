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
 * Created by wscubetech on 27/6/16.
 */
public class SingleSubjectSelectAdapter extends RecyclerView.Adapter<SingleSubjectSelectAdapter.ViewHolder> {

    Activity act;
    ArrayList<SubjectModel> arraySubjectModel = new ArrayList<>();

    public SingleSubjectSelectAdapter(Activity act, ArrayList<SubjectModel> arraySubjectModel) {
        this.act = act;
        this.arraySubjectModel = arraySubjectModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(LayoutInflater.from(act).inflate(R.layout.row_select_subject, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SubjectModel model = arraySubjectModel.get(position);
        holder.txtSubjectName.setText(model.getSubjectName());

        int visibility = model.getSelected() ? View.VISIBLE : View.GONE;
        holder.imgTick.setVisibility(visibility);

        holder.linParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getItemCount() > 1) {
                    for (int i = 0; i < arraySubjectModel.size(); i++) {
                        SubjectModel subjectModel = arraySubjectModel.get(i);
                        if (subjectModel.getSelected()) {
                            subjectModel.setSelected(false);
                            break;
                        }
                    }

                    model.setSelected(!model.getSelected());
                    arraySubjectModel.set(position, model);
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arraySubjectModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtSubjectName;
        ImageView imgTick;
        LinearLayout linParent;

        public ViewHolder(View v) {
            super(v);
            txtSubjectName = (TextView) v.findViewById(R.id.txtSubjectName);
            imgTick = (ImageView) v.findViewById(R.id.imgTick);
            linParent = (LinearLayout) v.findViewById(R.id.linParent);
        }
    }
}
