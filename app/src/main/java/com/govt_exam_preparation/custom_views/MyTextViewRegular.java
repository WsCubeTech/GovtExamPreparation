package com.govt_exam_preparation.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.govt_exam_preparation.custom.CustomFont;


/**
 * Created by wscubetech on 18/6/16.
 */
public class MyTextViewRegular extends TextView {
    public MyTextViewRegular(Context context) {
        super(context);
    }

    public MyTextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextViewRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        Typeface typeface = CustomFont.setFontRegular(getContext().getAssets());
        setTypeface(typeface);
    }
}
