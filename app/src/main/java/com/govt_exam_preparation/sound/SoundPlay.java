package com.govt_exam_preparation.sound;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.govt_exam_preparation.R;


/**
 * Created by wscubetech on 20/6/16.
 */
public class SoundPlay {

    public static final int SOUND_BTN_CLICK = R.raw.tinypush;

    MediaPlayer mediaPlayer;
    Activity activity;

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mep) {
            mep.stop();
            mep.reset();
            mep.release();

            //null the global MediaPlayer object
            mediaPlayer = null;
        }
    };

    public SoundPlay(Activity activity) {
        this.activity = activity;
    }

    public void playBtnClick() {
        mediaPlayer = MediaPlayer.create(activity, SOUND_BTN_CLICK);
        startPlaying();
    }

    public void startPlaying() {
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(onCompletionListener);
        } catch (Exception e) {
            Log.v("Exception", "" + e);
        }
    }


}
