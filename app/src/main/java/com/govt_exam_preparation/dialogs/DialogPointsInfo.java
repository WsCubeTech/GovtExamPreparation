package com.govt_exam_preparation.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.govt_exam_preparation.R;

/**
 * Created by wscubetech on 24/6/16.
 */
public class DialogPointsInfo {

    Activity activity;
    Dialog dialog;

    public DialogPointsInfo(Activity activity){
        this.activity=activity;
        dialog=new MyDialog(activity).getMyDialog(R.layout.dialog_quiz_points_info);
    }

    public void showDialog(String correctPoints,String wrongPoints) {
        TextView txtCorrectPoints,txtWrongPoints,txtOk;
        LinearLayout linParent;
        linParent=(LinearLayout)dialog.findViewById(R.id.linParent);
        txtCorrectPoints=(TextView)dialog.findViewById(R.id.txtPointsCorrect);
        txtWrongPoints=(TextView)dialog.findViewById(R.id.txtPointsWrong);
        txtOk=(TextView)dialog.findViewById(R.id.txtOk);
        txtCorrectPoints.setText(correctPoints);
        txtWrongPoints.setText(wrongPoints);

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        linParent.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.slide_up));
        dialog.show();
    }

    public Dialog getDialog(){
        return dialog;
    }

}
