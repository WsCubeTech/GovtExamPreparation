package com.govt_exam_preparation;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;

import com.govt_exam_preparation.model.Notification_Model;
import com.google.android.gcm.GCMBaseIntentService;

import org.json.JSONObject;

public class GCMIntentService extends GCMBaseIntentService {


    public GCMIntentService() {
        super("550635059300");
    }


    @Override
    protected void onError(Context arg0, String arg1) {
        Log.v("Show Error", arg1);
    }

    @Override
    protected void onMessage(Context arg0, Intent intent) {

        try {
            String action = intent.getAction();
            Log.v("actionPush", action);

            if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
                String message = intent.getExtras().getString("message");
                Log.v("PushReceived", message);


                message = Html.fromHtml(message).toString();
                NotifyMe notify = new NotifyMe(arg0);
                Notification_Model model = parseIncomingNotification(message);
                notify.sendNotification(model);
            }


        } catch (Exception exp) {
            Log.v("gcmException", exp.getMessage(), exp);
            exp.printStackTrace();
        }


    }

    @Override
    protected void onRegistered(Context arg0, String registrationId) {

        try {
            GenerateID.deviceId = registrationId;
            Log.d("user_dev_id", GenerateID.deviceId);


        } catch (Exception exp) {
            Log.v("exceptionGCM", "" + exp);
        } catch (Error e) {
            Log.v("errorGCM", "" + e);
        }

    }

    @Override
    protected void onUnregistered(Context arg0, String arg1) {


    }

    public Notification_Model parseIncomingNotification(String response) {
        Notification_Model model = new Notification_Model();
        try {
            JSONObject json = new JSONObject(response);
            model.setId(json.getString("notification_id"));
            model.setTitle(json.getString("notification_text"));
            model.setDescription(json.getString("notification_description"));
            model.setImage(json.getString("notification_image"));
        } catch (Exception e) {
            Log.v("NotifyParseException", "" + e);
        }
        return model;
    }
}
