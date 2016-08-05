package com.govt_exam_preparation.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.dialogs.DialogYesCancel;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.sound.SoundPlay;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;
import com.govt_exam_preparation.user_model.User;
import com.govt_exam_preparation.user_model.UserDetails;



public class Dashboard_Activity extends AppCompatActivity {

    CardView about_us_view, jobs_view, coaching_center_view, exam_preparation_view, favourite_view, share_view,
            follow_us_view, notification;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();

        if (RegistrationActivity.registrationActivity != null)
            RegistrationActivity.registrationActivity.finish();

        if (LoginActivity.loginActivity != null)
            LoginActivity.loginActivity.finish();

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar(getString(R.string.app_name));



        about_us_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                play.playBtnClick();

                Intent i = new Intent(Dashboard_Activity.this, AboutUsActivity.class);
                startActivity(i);


            }
        });

        jobs_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                play.playBtnClick();

                Intent i = new Intent(Dashboard_Activity.this, Jobs_List.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);


            }
        });

        coaching_center_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                play.playBtnClick();

                Intent i = new Intent(Dashboard_Activity.this, City_Coaching_Center.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);


            }
        });

        exam_preparation_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                play.playBtnClick();

                Intent i = new Intent(Dashboard_Activity.this, Exam_Instructions.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);


            }
        });

        favourite_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                play.playBtnClick();

                Intent intent = new Intent(Dashboard_Activity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        share_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                play.playBtnClick();

                String shareBody = "Exam Preparation \n Here is the download Url:- https://play.google.com/store/apps/details?id=" + getPackageName().toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invite for Govt. Exam Preparation");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));


            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                play.playBtnClick();
                startActivity(new Intent(Dashboard_Activity.this, Notification.class));
            }
        });


        follow_us_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(Dashboard_Activity.this,Favourite.class);
                startActivity(i);*/
                SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                play.playBtnClick();

                final Dialog dialog = new Dialog(Dashboard_Activity.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.dialog_follow);


                ImageView fbicon = (ImageView) dialog.findViewById(R.id.fbicon);
                ImageView twittericon = (ImageView) dialog.findViewById(R.id.twittericon);
                ImageView linkdinicon = (ImageView) dialog.findViewById(R.id.linkdinicon);
                ImageView googleicon = (ImageView) dialog.findViewById(R.id.googleicon);


                Animation animZoom = AnimationUtils.loadAnimation(Dashboard_Activity.this, R.anim.zoom_in);
                //Animation animDown = AnimationUtils.loadAnimation(Dashboard_Activity.this, R.anim.move_down);
                fbicon.startAnimation(animZoom);
                twittericon.startAnimation(animZoom);
                linkdinicon.startAnimation(animZoom);
                googleicon.startAnimation(animZoom);


                fbicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        My_URL.Web_view_url = "FB";
                        Intent i = new Intent(Dashboard_Activity.this, Follow_Us.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    }
                });


                twittericon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                        play.playBtnClick();
                        dialog.dismiss();
                        My_URL.Web_view_url = "TW";
                        Intent i = new Intent(Dashboard_Activity.this, Follow_Us.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    }
                });


                linkdinicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                        play.playBtnClick();
                        dialog.dismiss();
                        My_URL.Web_view_url = "LI";
                        Intent i = new Intent(Dashboard_Activity.this, Follow_Us.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    }
                });


                googleicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlay play = new SoundPlay(Dashboard_Activity.this);
                        play.playBtnClick();
                        dialog.dismiss();
                        My_URL.Web_view_url = "G+";
                        Intent i = new Intent(Dashboard_Activity.this, Follow_Us.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    }
                });

                dialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent);


                dialog.show();


            }
        });

        decideWhetherComingFromNotification();
    }

    public void decideWhetherComingFromNotification(){
        try{
            String comingFrom=getIntent().getExtras().getString("ComingFrom");
            if(comingFrom!=null && comingFrom.equalsIgnoreCase("Notification"))
                startActivity(new Intent(Dashboard_Activity.this, Notification.class));
        }catch (Exception e){
            Log.v("ExceptionNotifyDash",""+e);
        }
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        about_us_view = (CardView) findViewById(R.id.about_us_view);
        jobs_view = (CardView) findViewById(R.id.jobs_view);
        coaching_center_view = (CardView) findViewById(R.id.coaching_center_view);
        exam_preparation_view = (CardView) findViewById(R.id.exam_preparation_view);
        favourite_view = (CardView) findViewById(R.id.favourite_view);
        share_view = (CardView) findViewById(R.id.share_view);
        follow_us_view = (CardView) findViewById(R.id.follow_us_view);
        notification = (CardView) findViewById(R.id.notification);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemLogOut:
                DialogYesCancel dialogYesCancel = new DialogYesCancel(this);
                dialogYesCancel.showDialogYesCancel("Are you sure you want to log out?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserDetails userDetails = new UserDetails(Dashboard_Activity.this);
                        userDetails.setUserDetails(new User());

                        Intent intent = new Intent(Dashboard_Activity.this, LoginActivity.class);
                        startActivity(intent);
                        Dashboard_Activity.this.finish();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
