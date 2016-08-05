package com.govt_exam_preparation.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.govt_exam_preparation.GenerateID;
import com.govt_exam_preparation.R;
import com.govt_exam_preparation.sql.SqlClass;
import com.govt_exam_preparation.user_model.User;
import com.govt_exam_preparation.user_model.UserDetails;

public class Splash_Activity extends AppCompatActivity {

    ProgressBar seekbar;
    int count = 0;
    CountDownTimer timer;
    SharedPreferences pref;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        SqlClass sqlClass=new SqlClass(this,this);
        sqlClass.createTableQuizQuestions();

        user = new UserDetails(this).getUserDetail();

        new GenerateID(Splash_Activity.this).generate();
        Log.d("userId", user.getUserId());

       /* new CountDownTimer(3000,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {


                    startActivity(new Intent(Splash_Activity.this, Dashboard_Activity.class));
                    finish();


            }

        }.start();*/


        seekbar = (ProgressBar) findViewById(R.id.seekbar);
        timer = new CountDownTimer(30, 30) {


            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                count++;
                if (count < 101) {
                    seekbar.setProgress(count);
                    if (count == 100) {
                        timer.cancel();
                        Intent intent;
                        if (user.getUserId().equalsIgnoreCase(""))
                            intent = new Intent(Splash_Activity.this, LoginActivity.class);
                        else
                            intent = new Intent(Splash_Activity.this, Dashboard_Activity.class);

                        startActivity(intent);
                        finish();

                    }
                    timer.start();
                }
            }
        }.start();

    }


}
