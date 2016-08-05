package com.govt_exam_preparation.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.sound.SoundPlay;


/**
 * Created by wscubetech on 10/6/16.
 */
public class DialogYesCancel {

    Activity activity;
    Dialog dialog;

    public DialogYesCancel(Activity activity) {
        this.activity = activity;
    }

    public void showDialogYesCancel(String msg, View.OnClickListener clickListenerYes) {
        dialog = new MyDialog(activity).getMyDialog(R.layout.dialog_yes_cancel);
        TextView txtCancel, txtYes, txtMsg;
        LinearLayout linParent;
        linParent = (LinearLayout) dialog.findViewById(R.id.linParent);
        txtCancel = (TextView) dialog.findViewById(R.id.txtCancel);
        txtYes = (TextView) dialog.findViewById(R.id.txtYes);
        txtMsg = (TextView) dialog.findViewById(R.id.txtMsg);

        txtMsg.setText(msg);
        txtYes.setOnClickListener(clickListenerYes);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlay soundPlay=new SoundPlay(activity);
                soundPlay.playBtnClick();
                dialog.dismiss();
            }
        });

        linParent.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up));

        dialog.show();

    }

    public void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

}
