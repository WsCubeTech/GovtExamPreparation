package com.govt_exam_preparation.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.OptionModel;
import com.govt_exam_preparation.sound.SoundPlay;

import java.util.ArrayList;

/**
 * Created by wscubetech on 21/6/16.
 */
public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {

    ArrayList<OptionModel> arrayOptions = new ArrayList<>();
    Activity activity;
    String comingFrom = "";

    public OptionAdapter(Activity activity, ArrayList<OptionModel> arrayOptions, String comingFrom) {
        this.activity = activity;
        this.arrayOptions = arrayOptions;
        this.comingFrom = comingFrom;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_option_quiz, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OptionModel model = arrayOptions.get(position);
        holder.txtOption.setText(model.getOption());


        if (comingFrom.equalsIgnoreCase("Quiz")) {

            holder.imgMyInput.setVisibility(View.GONE);

            if (model.getMyInput()) {
                holder.relParent.setBackgroundResource(R.drawable.bg_rounded_input);
                holder.txtOption.setTextColor(Color.WHITE);
            } else {
                holder.relParent.setBackgroundResource(R.drawable.bg_white_curve);
                holder.txtOption.setTextColor(ContextCompat.getColor(activity, R.color.colorDimPrimary));
            }



            holder.relParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundPlay soundPlay=new SoundPlay(activity);
                    soundPlay.playBtnClick();

                    for (int i = 0; i < arrayOptions.size(); i++) {
                        OptionModel optionModel = arrayOptions.get(i);
                        if (i != position) {
                            optionModel.setMyInput(false);
                        } else {
                            optionModel.setMyInput(true);
                        }
                        arrayOptions.set(i, optionModel);
                    }
                    notifyDataSetChanged();
                }
            });
        } else {

            if (model.getMyInput()) {
                holder.relParent.setBackgroundResource(R.drawable.bg_rounded_red);
                holder.txtOption.setTextColor(Color.WHITE);
                holder.imgMyInput.setVisibility(View.VISIBLE);
            } else {
                holder.relParent.setBackgroundResource(R.drawable.bg_white_curve);
                holder.txtOption.setTextColor(ContextCompat.getColor(activity, R.color.colorDimPrimary));
                holder.imgMyInput.setVisibility(View.GONE);
            }

            if (model.getCorrect()) {
                holder.relParent.setBackgroundResource(R.drawable.bg_rounded_green);
                holder.txtOption.setTextColor(Color.WHITE);
            }

        }

    }


    @Override
    public int getItemCount() {
        return arrayOptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relParent;
        TextView txtOption;
        ImageView imgMyInput;

        public ViewHolder(View v) {
            super(v);
            relParent = (RelativeLayout) v.findViewById(R.id.relParent);
            txtOption = (TextView) v.findViewById(R.id.txtOption);
            imgMyInput = (ImageView) v.findViewById(R.id.imgMyInput);
        }
    }

}
