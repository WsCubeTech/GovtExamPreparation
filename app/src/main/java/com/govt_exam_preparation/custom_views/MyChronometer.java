package com.govt_exam_preparation.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

import com.govt_exam_preparation.custom.CustomFont;


public class MyChronometer extends Chronometer {

    private long timeWhenStopped = 0;

    public MyChronometer(Context context) {
        super(context);
        init();
    }

    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyChronometer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void start() {
        setBase(SystemClock.elapsedRealtime()+timeWhenStopped);
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        timeWhenStopped = getBase() - SystemClock.elapsedRealtime();
    }

    public void reset() {
        stop();
        setBase(SystemClock.elapsedRealtime());
        timeWhenStopped = 0;
    }

    public long getCurrentTime() {
        return timeWhenStopped;
    }

    public void setCurrentTime(long time) {
        timeWhenStopped = time;
        setBase(SystemClock.elapsedRealtime()+timeWhenStopped);
    }

    public void init() {
        Typeface typeface = CustomFont.setFontRegular(getContext().getAssets());
        setTypeface(typeface);
    }
}