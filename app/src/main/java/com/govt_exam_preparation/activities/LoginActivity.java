package com.govt_exam_preparation.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.govt_exam_preparation.AppController;
import com.govt_exam_preparation.R;
import com.govt_exam_preparation.custom.CustomFont;
import com.govt_exam_preparation.dialogs.DialogMsg;
import com.govt_exam_preparation.dialogs.MyDialog;
import com.govt_exam_preparation.model.CityModel;
import com.govt_exam_preparation.model.ExamModel;
import com.govt_exam_preparation.model.SubjectModel;
import com.govt_exam_preparation.others.ConnectionDetector;
import com.govt_exam_preparation.others.MyProgress;
import com.govt_exam_preparation.others.MyValidations;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.user_model.User;
import com.govt_exam_preparation.user_model.UserDetails;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnRegister;
    TextInputLayout inpPhoneNumber;
    EditText etPhoneNumber;

    MyValidations validations;

    Toolbar toolbar;

    DialogMsg dialogMsg;
    Dialog progress;

    User user = new User();

    public static LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        loginActivity = this;

        init();
        validations = new MyValidations(this);
        textChangeListeners();
        setOnClickListeners();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        inpPhoneNumber = (TextInputLayout) findViewById(R.id.input_layout_phone);
        etPhoneNumber = (EditText) findViewById(R.id.edtPhone);

        btnRegister.setTypeface(CustomFont.setFontBold(getAssets()));
        btnLogin.setTypeface(CustomFont.setFontBold(getAssets()));
        inpPhoneNumber.setTypeface(CustomFont.setFontRegular(getAssets()));
        etPhoneNumber.setTypeface(CustomFont.setFontRegular(getAssets()));
    }

    public void setOnClickListeners() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    public void textChangeListeners() {
        etPhoneNumber.addTextChangedListener(new MyTextWatcher(inpPhoneNumber));
    }


    public void inputOperationError(TextInputLayout inp, EditText et, String msg) {
        inp.setErrorEnabled(true);
        inp.setError(msg);
        et.requestFocus();
    }

    public void inputOperationNoError(TextInputLayout inp) {
        inp.setErrorEnabled(false);
        inp.setError(null);
    }

    private class MyTextWatcher implements TextWatcher {

        public TextInputLayout inpLayout;

        private MyTextWatcher(TextInputLayout inpLayout) {
            this.inpLayout = inpLayout;
            validations = new MyValidations(LoginActivity.this);
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String input = editable.toString().trim();
            switch (inpLayout.getId()) {

                case R.id.input_layout_phone:
                    if (input.length() > 0 && validations.checkMobile(input))
                        inputOperationNoError(inpLayout);
                    break;
            }
        }
    }

    public void loginAfterValidation() {
        String inputPhone = etPhoneNumber.getText().toString().trim();
        if (inputPhone.length() < 1 || !validations.checkMobile(inputPhone)) {
            inputOperationError(inpPhoneNumber, etPhoneNumber, "Please input a valid mobile number");
            return;
        }

        if (new ConnectionDetector(this).isConnectingToInternet()) {
            loginVolley(inputPhone);
        } else {
            dialogMsg = new DialogMsg(LoginActivity.this);
            dialogMsg.showDialog(getString(R.string.connectionError), true, R.drawable.ic_error_black_24dp);
        }

    }

    public void loginVolley(String inputPhoneNumber) {

        progress = new MyProgress(this).getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();

        String url = My_URL.sendOtpLogin + "flag=2&mobile_number=" + inputPhoneNumber;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progress.isShowing())
                    progress.dismiss();
                if (response != null) {
                    try {
                        Log.v("Response", "" + response);
                        response = Html.fromHtml(response).toString();
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("result") == 1) {
                            String otp = json.getString("otp_code");

                            JSONObject object = json.getJSONObject("user_details");
                            user = new User();
                            user.setUserId(object.getString("user_id"));
                            user.setUserName(object.getString("user_name"));
                            user.setUserEmail(object.getString("user_email"));
                            user.setUserPhone(object.getString("user_phone"));
                            user.setUserAddress(object.getString("user_address"));

                            CityModel cityModel = new CityModel();
                            cityModel.setCityId(object.getString("user_city"));

                            user.setCityModel(cityModel);

                            JSONObject jsonObject = object.getJSONObject("exam");
                            ExamModel examModel = new ExamModel();
                            examModel.setExamId(jsonObject.getString("exam_id"));
                            examModel.setExamName(jsonObject.getString("exam_name"));

                            JSONArray array = object.getJSONArray("subject");
                            ArrayList<SubjectModel> arraySubjectModel = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                SubjectModel subjectModel = new SubjectModel();
                                subjectModel.setSubjectId(obj.getString("examsubject_id"));
                                subjectModel.setSubjectName(obj.getString("examsubject_name"));
                                subjectModel.setSelected(false);
                                subjectModel.setUniversal(obj.getString("examsubject_uni_status").equals("1"));
                                arraySubjectModel.add(subjectModel);
                            }
                            examModel.setArraySubjectModel(arraySubjectModel);
                            user.setExamModel(examModel);
                            viewOtpDialogAndCheck(otp);
                        } else {
                            dialogMsg = new DialogMsg(LoginActivity.this);
                            dialogMsg.showDialog("Sorry!\nNo user registered with this mobile number", true, R.drawable.ic_error_black_24dp);
                        }
                    } catch (Exception e) {
                        Log.v("exception", "" + e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progress.isShowing())
                    progress.dismiss();
                dialogMsg = new DialogMsg(LoginActivity.this);
                dialogMsg.showDialog(getString(R.string.networkError), true, R.drawable.ic_error_black_24dp);
            }
        });

        AppController.getInstance().addToRequestQueue(request, "Login");

    }

    public void viewOtpDialogAndCheck(final String actualOtp) {
        Dialog dialog = new MyDialog(this).getMyDialog(R.layout.dialog_otp);
        TextView txtOk;
        final EditText etOtp;
        final TextInputLayout inpOtp;
        txtOk = (TextView) dialog.findViewById(R.id.txtOk);
        etOtp = (EditText) dialog.findViewById(R.id.etOtp);
        inpOtp = (TextInputLayout) dialog.findViewById(R.id.inpOtp);

        etOtp.setText(actualOtp);
        etOtp.setSelection(etOtp.getText().toString().length());

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = etOtp.getText().toString().trim();
                if (input.equals(actualOtp)) {
                    inputOperationNoError(inpOtp);
                    saveUserDetailsAndGoAhead();
                } else {
                    inputOperationError(inpOtp, etOtp, "OTP entered is incorrect");
                }
            }
        });

        dialog.show();

    }

    public void saveUserDetailsAndGoAhead() {
        UserDetails userDetails = new UserDetails(this);
        userDetails.setUserDetails(user);

        Intent intent = new Intent(this, Dashboard_Activity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                loginAfterValidation();
                break;
            case R.id.btnRegister:
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
        }
    }
}
