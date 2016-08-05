package com.govt_exam_preparation.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.sound.SoundPlay;


/**
 * Created by wscubetech on 7/6/16.
 */
public class DialogMsg {

    Activity activity;
    Dialog dialog;

    public DialogMsg(Activity activity) {
        this.activity = activity;
    }

    public void showDialog(String msg, Boolean showImage) {
        final Dialog dialog = new MyDialog(activity).getMyDialog(R.layout.dialog_msg);
        dialog.setCancelable(true);
        TextView txtMsg = (TextView) dialog.findViewById(R.id.txtMsg);
        TextView txtOk = (TextView) dialog.findViewById(R.id.txtOk);

        ImageView img = (ImageView) dialog.findViewById(R.id.img);

        if (showImage)
            img.setVisibility(View.VISIBLE);
        else
            img.setVisibility(View.GONE);

        LinearLayout linParent = (LinearLayout) dialog.findViewById(R.id.linParent);
        txtMsg.setText(msg);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlay play = new SoundPlay(activity);
                play.playBtnClick();
                dialog.dismiss();
            }
        });
        dialog.show();
        linParent.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up));
    }

    public void showDialog(String msg, Boolean showImage, int imageRes) {
        final Dialog dialog = new MyDialog(activity).getMyDialog(R.layout.dialog_msg);
        dialog.setCancelable(true);
        TextView txtMsg = (TextView) dialog.findViewById(R.id.txtMsg);
        TextView txtOk = (TextView) dialog.findViewById(R.id.txtOk);

        ImageView img = (ImageView) dialog.findViewById(R.id.img);

        if (showImage) {
            img.setImageResource(imageRes);
            img.setVisibility(View.VISIBLE);
        } else
            img.setVisibility(View.GONE);

        LinearLayout linParent = (LinearLayout) dialog.findViewById(R.id.linParent);
        txtMsg.setText(msg);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlay play = new SoundPlay(activity);
                play.playBtnClick();
                dialog.dismiss();
            }
        });
        dialog.show();
        linParent.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up));
    }

    public void showDialog(String msg, Boolean showImage, int imageRes, View.OnClickListener onClickListener) {
        dialog = new MyDialog(activity).getMyDialog(R.layout.dialog_msg);
        dialog.setCancelable(true);
        TextView txtMsg = (TextView) dialog.findViewById(R.id.txtMsg);
        TextView txtOk = (TextView) dialog.findViewById(R.id.txtOk);

        ImageView img = (ImageView) dialog.findViewById(R.id.img);

        if (showImage) {
            img.setImageResource(imageRes);
            img.setVisibility(View.VISIBLE);
        } else
            img.setVisibility(View.GONE);

        if (imageRes == R.drawable.ic_assignment_turned_in_black_24dp)
            img.setColorFilter(ContextCompat.getColor(activity, R.color.colorDarkGreen));

        LinearLayout linParent = (LinearLayout) dialog.findViewById(R.id.linParent);
        txtMsg.setText(msg);
        txtOk.setOnClickListener(onClickListener);
        dialog.show();
        linParent.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up));
    }

    public Dialog getDialog() {
        return dialog;
    }


}
