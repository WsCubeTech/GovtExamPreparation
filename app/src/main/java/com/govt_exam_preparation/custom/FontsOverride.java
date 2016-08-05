package com.govt_exam_preparation.custom;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public final class FontsOverride {

     public static void setDefaultFont(Context context,
                String staticTypefaceFieldName) {
            final Typeface regular = CustomFont.setFontRegular(context.getAssets());
            replaceFont(staticTypefaceFieldName, regular);
        }

        protected static void replaceFont(String staticTypefaceFieldName,
                final Typeface newTypeface) {
            try {
                final Field staticField = Typeface.class
                        .getDeclaredField(staticTypefaceFieldName);
                staticField.setAccessible(true);

                staticField.set(null, newTypeface);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


}