package com.govt_exam_preparation.others;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.govt_exam_preparation.R;
import com.wang.avi.AVLoadingIndicatorView;



/**
 * Created by wscubetech on 15/10/15.
 */
public class MyProgress {

    Context context;
    Drawable drawable;

    public MyProgress(Context context, Drawable drawable) {
        this(context);
        this.drawable = drawable;
    }

    public MyProgress(Context context) {
        this.context = context;
    }

    public Dialog getProgressDialog(final String title, final int layout) {
        Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(layout);

        dialog.setCancelable(false);
        TextView txtLoading;
        ProgressBar p;

        AVLoadingIndicatorView a;

        txtLoading = (TextView) dialog.findViewById(R.id.txtLoading);

        // p = (ProgressBar) dialog.findViewById(R.id.progressBar);

        a = (AVLoadingIndicatorView) dialog.findViewById(R.id.avloadingIndicatorView);


        if (drawable != null)
            a.setVisibility(View.VISIBLE);


        txtLoading.setText(title);
        return dialog;
    }
}