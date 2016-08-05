package com.govt_exam_preparation.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.govt_exam_preparation.AppController;
import com.govt_exam_preparation.R;
import com.govt_exam_preparation.dialogs.DialogMsg;
import com.govt_exam_preparation.others.ConnectionDetector;
import com.govt_exam_preparation.others.MyProgress;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;


public class AboutUsActivity extends AppCompatActivity {

    TextView txtAboutUsDescription;
    ImageView imgAboutUs;

    DialogMsg dialogMsg;

    String title = "", image = "", description = "", email = "";

    Dialog progress;

    ToolbarOperation toolbarOperation;

    LinearLayout linEmail;
    TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        init();

        toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar(getString(R.string.about_us));
        toolbarOperation.showBackButton(true);


        if (new ConnectionDetector(this).isConnectingToInternet()) {
            viewAboutUsVolley();
        } else {
            dialogMsg = new DialogMsg(this);
            dialogMsg.showDialog(getString(R.string.connectionError), true, R.drawable.ic_error_black_24dp);
        }

        linEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: " + txtEmail.getText().toString().trim()));
                startActivity(Intent.createChooser(emailIntent, "Send Email via"));
            }
        });

    }

    public void init() {
        txtAboutUsDescription = (TextView) findViewById(R.id.txtAboutUsDescription);
        imgAboutUs = (ImageView) findViewById(R.id.imgAboutUs);
        linEmail = (LinearLayout) findViewById(R.id.linEmail);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
    }


    public void viewAboutUsVolley() {

        progress = new MyProgress(this).getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();

        StringRequest request = new StringRequest(Request.Method.GET, My_URL.viewAboutUs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progress.isShowing())
                    progress.dismiss();
                if (response != null) {
                    try {
                        //response = Html.fromHtml(response).toString();
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("result") == 1) {
                            JSONObject obj = json.getJSONObject("msg");
                            title = obj.getString("page_title");
                            description = obj.getString("page_des");
                            image = obj.getString("page_image").trim();
                            email = obj.getString("page_email").trim();

                            toolbarOperation.setMyTitleAndToolbar(title);
                            txtAboutUsDescription.setText(Html.fromHtml(description));
                            txtEmail.setText(email);

                            if (image.length() > 1) {
                                imgAboutUs.setVisibility(View.VISIBLE);
                                Picasso.with(AboutUsActivity.this)
                                        .load(My_URL.image_url + image)
                                        .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .placeholder(R.drawable.ic_photo_gallery)
                                        .into(imgAboutUs);
                            } else {
                                imgAboutUs.setVisibility(View.GONE);
                            }

                        }
                    } catch (Exception e) {
                        Log.v("Exception", "" + e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progress.isShowing())
                    progress.dismiss();
                dialogMsg = new DialogMsg(AboutUsActivity.this);
                dialogMsg.showDialog(getString(R.string.networkError), true, R.drawable.ic_error_black_24dp);
            }
        });
        AppController.getInstance().addToRequestQueue(request, "ViewAboutUs");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
