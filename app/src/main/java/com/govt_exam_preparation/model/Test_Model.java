package com.govt_exam_preparation.model;

import android.graphics.Bitmap;

/**
 * Created by wscubetech on 7/4/16.
 */
public class Test_Model {

    String text = "";
    Bitmap bitmap;

    public Test_Model(String text, Bitmap bitmap) {
        this.text = text;
        this.bitmap = bitmap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
