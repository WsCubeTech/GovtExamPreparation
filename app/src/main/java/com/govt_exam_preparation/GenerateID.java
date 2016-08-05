package com.govt_exam_preparation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.user_model.User;
import com.govt_exam_preparation.user_model.UserDetails;
import com.google.android.gcm.GCMRegistrar;

import java.util.HashMap;
import java.util.Map;




public class GenerateID {
    Activity context;
    SharedPreferences prefs;

    public static String senderId = "";
    public static String deviceId = "";
    int result = 0;
    User user;

    public GenerateID(Activity context) {
        this.context = context;
        senderId = context.getString(R.string.project_number);
    }

    public void generate() {
        try {

            GCMRegistrar.checkDevice(context);
            GCMRegistrar.checkManifest(context);

            deviceId = GCMRegistrar.getRegistrationId(context);


            if (deviceId.equals("")) {
                GCMRegistrar.register(context, senderId);
            }

            Log.d("Device Id: ", "" + deviceId);

            user = new UserDetails(context).getUserDetail();
            if (user.getUserId().trim().length() > 0)
                send_did();


        } catch (Exception e) {
            Log.v("Exception...", "" + e);

        }


    }

    public void send_did() {


        String url = My_URL.add_device_id + "user_id="
                + user.getUserId() + "&did=" + deviceId + "&flag=" + "2";

        Log.d("url", url);


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
//           http://www.wscubetechapps.in/mobileteam/exam_preparation_app/api/add_did.php?user_id=&did=&flag=
                Log.d("Response", s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user.getUserId());
                params.put("did", deviceId);
                params.put("flag", "2");

                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request, "Add DeviceId");


    }

}




