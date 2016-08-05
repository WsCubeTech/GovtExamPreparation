package com.govt_exam_preparation.activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;


public class Exam_Preparation extends AppCompatActivity {


    TextView comprehensive_text;

    LinearLayout main_layout;

    String text = "";
    Typeface face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam__preparation);

        ToolbarOperation toolbarOperation=new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Comprehensive Text");
        toolbarOperation.showBackButton(true);

        main_layout = (LinearLayout)findViewById(R.id.main_layout);
        comprehensive_text = (TextView)findViewById(R.id.comprehensive_text);

        face = Typeface.createFromAsset(getAssets(),
                "fonts/Kruti_Dev_010.ttf");
        comprehensive_text.setTypeface(face);

        try {
            text = getIntent().getExtras().getString("text");
        } catch (Exception e) {
            e.printStackTrace();
        }

        comprehensive_text.setText(text);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
