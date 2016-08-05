package com.govt_exam_preparation.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.govt_exam_preparation.custom.CustomFont;


/**
 * Created by wscubetech on 18/6/16.
 */
public class MyTextViewBold extends TextView {
    public MyTextViewBold(Context context) {
        super(context);
        init();
    }

    public MyTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface typeface = CustomFont.setFontBold(getContext().getAssets());
        setTypeface(typeface);
    }
}
