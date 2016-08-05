package com.govt_exam_preparation.custom;

import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by wscubetech on 1/4/16.
 */
public class CustomFont {

    public static final Typeface setFontBold(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, "fonts/pt_sans_bold.ttf");
    }

    public static final Typeface setFontRegular(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, "fonts/pt_sans_regular.ttf");
    }


}
