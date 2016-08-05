package com.govt_exam_preparation.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.govt_exam_preparation.AppController;
import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.SubjectSelectAdapter;
import com.govt_exam_preparation.custom.CustomFont;
import com.govt_exam_preparation.dialogs.DialogMsg;
import com.govt_exam_preparation.dialogs.MyDialog;
import com.govt_exam_preparation.model.CityModel;
import com.govt_exam_preparation.model.CouponModel;
import com.govt_exam_preparation.model.ExamModel;
import com.govt_exam_preparation.model.SubjectModel;
import com.govt_exam_preparation.others.ConnectionDetector;
import com.govt_exam_preparation.others.MyProgress;
import com.govt_exam_preparation.others.MyValidations;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;
import com.govt_exam_preparation.user_model.User;
import com.govt_exam_preparation.user_model.UserDetails;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {


    EditText edtName, edtPhone, edtAddress, edtPromoCode, edtEmail;
    private TextInputLayout inputLayoutName, inputLayoutPhone, inputLayoutAddress, inputLayoutPromocode, inputLayoutEmail;
    RadioButton radioMale, radioFemale;
    Button btnRegister;

    RelativeLayout relCitySelect, relExamSelect;
    TextView txtCity, txtExam;

    LinearLayout linHaveACoupon;
    ImageView imgCouponCheck;
    Boolean haveACoupon = false;


    String maleFemale = "male", selectedCityId = "", selectedExamId = "", selectedSubjectsIds = "";

    MyValidations validations;
    ArrayList<CityModel> arrayCityModel = new ArrayList<>();
    ArrayList<ExamModel> arrayExamModel = new ArrayList<>();
    ArrayList<SubjectModel> arraySubjectModel = new ArrayList<>();
    ArrayList<CouponModel> arrayCouponModel = new ArrayList<>();

    CouponModel selectedCouponModel = new CouponModel();

    RecyclerView recyclerView;

    SubjectSelectAdapter subjectSelectAdapter;

    Dialog progress;

    DialogMsg dialogMsg;

    public static RegistrationActivity registrationActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_registration);
        init();

        registrationActivity = this;

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Registration");

        setFonts();

        if (!new ConnectionDetector(this).isConnectingToInternet()) {
            dialogMsg = new DialogMsg(RegistrationActivity.this);
            dialogMsg.showDialog(getString(R.string.connectionError), true, R.drawable.ic_error_black_24dp);
            return;
        }

        validations = new MyValidations(this);

        subjectSelectAdapter = new SubjectSelectAdapter(RegistrationActivity.this, arraySubjectModel);
        recyclerView.setAdapter(subjectSelectAdapter);

        textChangeListeners();
        onClickListeners();
        viewCitiesVolley();
    }


    public void init() {
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_layout_address);
        inputLayoutPromocode = (TextInputLayout) findViewById(R.id.input_layout_promocode);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);

        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPromoCode = (EditText) findViewById(R.id.edtPromocode);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        linHaveACoupon = (LinearLayout) findViewById(R.id.linHaveACoupon);
        imgCouponCheck = (ImageView) findViewById(R.id.imgCouponCheck);

        //Subjects recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        relCitySelect = (RelativeLayout) findViewById(R.id.relCitySelect);
        relExamSelect = (RelativeLayout) findViewById(R.id.relExamSelect);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtExam = (TextView) findViewById(R.id.txtExam);


    }

    public void textChangeListeners() {
        edtName.addTextChangedListener(new MyTextWatcher(inputLayoutName));
        edtAddress.addTextChangedListener(new MyTextWatcher(inputLayoutAddress));
        edtPhone.addTextChangedListener(new MyTextWatcher(inputLayoutPhone));
        edtPromoCode.addTextChangedListener(new MyTextWatcher(inputLayoutPromocode));
        edtEmail.addTextChangedListener(new MyTextWatcher(inputLayoutEmail));
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


    public void registerAfterValidating() {

        String inputName, inputPhone, inputEmail, inputAddress, inputCity;

        inputName = edtName.getText().toString().trim();
        inputPhone = edtPhone.getText().toString().trim();
        inputAddress = edtAddress.getText().toString().trim();
        inputEmail = edtEmail.getText().toString().trim();


        if (inputName.length() < 1 || !validations.checkName(inputName)) {
            inputOperationError(inputLayoutName, edtName, "Please enter a valid user name");
            return;
        }

        if (inputPhone.length() < 1 || !validations.checkMobile(inputPhone)) {
            inputOperationError(inputLayoutPhone, edtPhone, "Please enter a valid phone number");
            return;
        }

        if (inputEmail.length() > 0) {
            if (!validations.checkEmail(inputEmail)) {
                inputOperationError(inputLayoutEmail, edtEmail, "Please enter a valid email address");
                return;
            }
        }

        if (inputAddress.length() < 3) {
            inputOperationError(inputLayoutAddress, edtAddress, "Please enter a valid address");
            return;
        }

        if (selectedCityId.trim().length() < 1) {
            dialogMsg = new DialogMsg(this);
            dialogMsg.showDialog("Please select a city to proceed", true, R.drawable.ic_error_black_24dp);
            return;
        }

        if (selectedExamId.trim().length() < 1) {
            dialogMsg = new DialogMsg(this);
            dialogMsg.showDialog("Please select an exam to proceed", true, R.drawable.ic_error_black_24dp);
            return;
        }

        int flagSelectedSubject = 0;
        for (SubjectModel model : arraySubjectModel) {
            if (model.getSelected()) {
                flagSelectedSubject = 1;
                break;
            }
        }


        if (flagSelectedSubject == 0) {
            dialogMsg = new DialogMsg(this);
            dialogMsg.showDialog("Please select at least one subject to proceed", true, R.drawable.ic_error_black_24dp);
            return;
        }

        if (haveACoupon) {
            int flagCouponOk = 0;
            String input = edtPromoCode.getText().toString().trim();
            for (CouponModel model : arrayCouponModel) {
                if (input.equals(model.getCouponCode())) {
                    flagCouponOk = 1;
                    selectedCouponModel = model;
                    break;
                }
            }

            if (flagCouponOk == 0) {
                dialogMsg = new DialogMsg(this);
                dialogMsg.showDialog("Please input a valid coupon code\nif u have", true, R.drawable.ic_error_black_24dp);
                return;
            }

        }


        User user = new User();
        user.setUserName(inputName);
        user.setUserPhone(inputPhone);
        user.setUserEmail(inputEmail);
        user.setUserAddress(inputAddress);
        user.setUserGender(maleFemale);
        user.setCouponModel(selectedCouponModel);

        setProperModelValuesInUser(user);
    }

    private void setProperModelValuesInUser(User user) {
        CityModel cityModel = new CityModel();
        for (CityModel model : arrayCityModel) {
            if (model.getCityId().equals(selectedCityId)) {
                cityModel = model;
                break;
            }
        }

        ExamModel examModel = new ExamModel();
        for (ExamModel model : arrayExamModel) {
            if (model.getExamId().equalsIgnoreCase(selectedExamId)) {
                examModel = model;
                break;
            }
        }

        ArrayList<SubjectModel> arraySubjectModel = new ArrayList<>();
        for (SubjectModel model : RegistrationActivity.this.arraySubjectModel) {
            if (model.getSelected()) {
                selectedSubjectsIds += model.getSubjectId() + ",";
                arraySubjectModel.add(model);
            }
        }

        examModel.setArraySubjectModel(arraySubjectModel);

        user.setCityModel(cityModel);
        user.setExamModel(examModel);

        selectedSubjectsIds = selectedSubjectsIds.substring(0, selectedSubjectsIds.length() - 1);

        if (new ConnectionDetector(this).isConnectingToInternet()) {
            registerUserVolley(user);
        } else {
            dialogMsg = new DialogMsg(this);
            dialogMsg.showDialog(getString(R.string.connectionError), true, R.drawable.ic_error_black_24dp);
        }
    }


    private class MyTextWatcher implements TextWatcher {

        public TextInputLayout inpLayout;

        private MyTextWatcher(TextInputLayout inpLayout) {
            this.inpLayout = inpLayout;
            validations = new MyValidations(RegistrationActivity.this);
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String input = editable.toString().trim();
            switch (inpLayout.getId()) {
                case R.id.input_layout_name:
                    if (input.length() > 0 && validations.checkName(input))
                        inputOperationNoError(inpLayout);
                    break;
                case R.id.input_layout_address:
                    if (input.length() > 2)
                        inputOperationNoError(inpLayout);
                    break;
                case R.id.input_layout_phone:
                    if (input.length() > 0 && validations.checkMobile(input))
                        inputOperationNoError(inpLayout);
                    break;
                case R.id.input_layout_promocode:
                    if (input.length() > 0)
                        inputOperationNoError(inpLayout);
                    break;
                case R.id.input_layout_email:
                    if (input.length() > 0) {
                        if (validations.checkEmail(input)) {
                            inputOperationNoError(inpLayout);
                        }
                    } else {
                        inputOperationNoError(inpLayout);
                    }
                    break;
            }
        }
    }

    public void setFonts() {
        edtName.setTypeface(CustomFont.setFontRegular(getAssets()));
        edtAddress.setTypeface(CustomFont.setFontRegular(getAssets()));
        edtPromoCode.setTypeface(CustomFont.setFontRegular(getAssets()));
        edtPhone.setTypeface(CustomFont.setFontRegular(getAssets()));
        edtEmail.setTypeface(CustomFont.setFontRegular(getAssets()));

        inputLayoutName.setTypeface(CustomFont.setFontRegular(getAssets()));
        inputLayoutAddress.setTypeface(CustomFont.setFontRegular(getAssets()));
        inputLayoutPhone.setTypeface(CustomFont.setFontRegular(getAssets()));
        inputLayoutEmail.setTypeface(CustomFont.setFontRegular(getAssets()));
        inputLayoutPromocode.setTypeface(CustomFont.setFontRegular(getAssets()));
    }

    public void onClickListeners() {
        relCitySelect.setOnClickListener(this);
        relExamSelect.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        linHaveACoupon.setOnClickListener(this);

        radioMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                maleFemale = b ? "male" : "female";
            }
        });

        radioFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                maleFemale = b ? "female" : "male";
            }
        });


    }

    private void viewCitiesVolley() {
        arrayCityModel.clear();
        progress = new MyProgress(this).getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();
        StringRequest request = new StringRequest(Request.Method.GET, My_URL.city_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("result") == 1) {
                            JSONArray array = json.getJSONArray("msg");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                CityModel model = new CityModel();
                                model.setCityId(obj.getString("city_id"));
                                model.setCityName(obj.getString("city_name"));
                                arrayCityModel.add(model);
                            }

                            if (arrayCityModel.size() > 0) {
                                txtCity.setText(arrayCityModel.get(0).getCityName());
                                selectedCityId = arrayCityModel.get(0).getCityId();
                            }

                        } else {
                            dialogMsg = new DialogMsg(RegistrationActivity.this);
                            dialogMsg.showDialog("No cities found on server", true, R.drawable.ic_error_black_24dp);
                        }
                    } catch (Exception e) {

                    }
                }
                viewExamsVolley();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                viewExamsVolley();
            }
        });
        AppController.getInstance().addToRequestQueue(request, "ViewCities");
    }

    public void viewExamsVolley() {
        arrayExamModel.clear();
        StringRequest request = new StringRequest(Request.Method.GET, My_URL.viewExams, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        response = Html.fromHtml(response).toString();
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("result") == 1) {
                            JSONArray array = json.getJSONArray("msg");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                ExamModel model = new ExamModel();
                                model.setExamId(obj.getString("exam_id"));
                                model.setExamName(obj.getString("exam_name"));
                                arrayExamModel.add(model);
                            }

                            if (arrayExamModel.size() > 0) {
                                txtExam.setText(arrayExamModel.get(0).getExamName());
                                selectedExamId = arrayExamModel.get(0).getExamId();
                                viewSubjectsVolley();
                            }

                        } else {
                            dialogMsg = new DialogMsg(RegistrationActivity.this);
                            dialogMsg.showDialog("No exams found on server", true, R.drawable.ic_error_black_24dp);
                        }
                    } catch (Exception e) {

                    }
                }
                viewCouponsVolley();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progress.isShowing())
                    progress.dismiss();
                dialogMsg = new DialogMsg(RegistrationActivity.this);
                dialogMsg.showDialog(getString(R.string.networkError), true, R.drawable.ic_error_black_24dp);

                viewCouponsVolley();
            }
        });
        AppController.getInstance().addToRequestQueue(request, "ViewExams");
    }

    public void viewCouponsVolley() {
        arrayCouponModel.clear();

        StringRequest request = new StringRequest(Request.Method.GET, My_URL.viewCoupons, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("result") == 1) {
                            JSONArray array = json.getJSONArray("msg");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                CouponModel model = new CouponModel();
                                model.setCouponId(obj.getString("coupon_id"));
                                model.setCouponCode(obj.getString("coupon_code"));
                                model.setCouponDiscount(obj.getString("coupon_value"));
                                arrayCouponModel.add(model);
                            }
                        }
                    } catch (Exception e) {
                        Log.v("Exception_coupon_parse", "" + e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(request, "ViewCoupons");
    }

    public void viewSubjectsVolley() {
        arraySubjectModel.clear();

        notifySubjectsChange();

        if (progress != null) {
            if (!progress.isShowing())
                progress.show();
        }

        StringRequest request = new StringRequest(Request.Method.GET, My_URL.viewSubjects + selectedExamId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progress.isShowing())
                    progress.dismiss();
                if (response != null) {
                    try {
                        response = Html.fromHtml(response).toString();
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("result") == 1) {
                            JSONArray array = json.getJSONArray("msg");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                final SubjectModel model = new SubjectModel();
                                model.setSubjectId(obj.getString("examsubject_id"));
                                model.setSubjectName(obj.getString("examsubject_name"));

                                Boolean universal = obj.getString("examsubject_uni_status").equals("1");
                                model.setUniversal(universal);
                                model.setSelected(universal);

                                model.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (!model.getUniversal()) {
                                            if (model.getSelected())
                                                model.setSelected(false);
                                            else
                                                model.setSelected(true);

                                            notifySubjectsChange();
                                        }
                                    }
                                });
                                arraySubjectModel.add(model);
                                notifySubjectsChange();
                            }


                        }
                    } catch (Exception e) {

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progress.isShowing())
                    progress.dismiss();
                dialogMsg = new DialogMsg(RegistrationActivity.this);
                dialogMsg.showDialog(getString(R.string.networkError), true, R.drawable.ic_error_black_24dp);
            }
        });
        AppController.getInstance().addToRequestQueue(request, "ViewExams");
    }

    public void notifySubjectsChange() {
        if (subjectSelectAdapter != null)
            subjectSelectAdapter.notifyDataSetChanged();
    }


    public void onClickFillCityOrExamAdapter(final String cityOrExamHeader) {
        ArrayList<String> arrayCityOrExamNames = new ArrayList<>();


        if (cityOrExamHeader.toLowerCase().contains("city")) {
            for (CityModel model : arrayCityModel)
                arrayCityOrExamNames.add(model.getCityName());
        } else if (cityOrExamHeader.toLowerCase().contains("exam")) {
            for (ExamModel examModel : arrayExamModel)
                arrayCityOrExamNames.add(examModel.getExamName());
        }

        dialogListOneItem(cityOrExamHeader, arrayCityOrExamNames);

    }

    public void dialogListOneItem(final String cityOrExamHeader, ArrayList<String> arrayCityOrExamNames) {
        final Dialog dialog = new MyDialog(this).getMyDialog(R.layout.dialog_select_one_item);
        TextView txtHeader = (TextView) dialog.findViewById(R.id.txtHeader);
        ListView listView = (ListView) dialog.findViewById(R.id.listView);
        LinearLayout linParent = (LinearLayout) dialog.findViewById(R.id.linParent);

        txtHeader.setText(cityOrExamHeader);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.row_text_view, arrayCityOrExamNames);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                if (cityOrExamHeader.toLowerCase().contains("city")) {
                    CityModel model = arrayCityModel.get(i);
                    selectedCityId = model.getCityId();
                    txtCity.setText(model.getCityName());

                } else if (cityOrExamHeader.toLowerCase().contains("exam")) {
                    ExamModel model = arrayExamModel.get(i);
                    selectedExamId = model.getExamId();
                    txtExam.setText(model.getExamName());

                    viewSubjectsVolley();

                }
            }
        });


        linParent.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        dialog.show();
    }


    public void registerUserVolley(final User user) {

        progress = new MyProgress(this).getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();

        StringRequest request = new StringRequest(Request.Method.POST, My_URL.registration, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progress.isShowing())
                    progress.dismiss();
                if (response != null) {
                    try {
                        Log.v("ResponseRegister", response);
                        response = Html.fromHtml(response).toString();
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("result") == 1) {
                            JSONObject obj = json.getJSONObject("user_details");
                            user.setUserId(obj.getString(User.KEY_USER_ID));
                            saveUserDetailsAndGoAhead(user);
                        } else {
                            dialogMsg = new DialogMsg(RegistrationActivity.this);
                            dialogMsg.showDialog("Email or phone number already exists!\nPlease input a different one", true, R.drawable.ic_error_black_24dp);
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
                dialogMsg = new DialogMsg(RegistrationActivity.this);
                dialogMsg.showDialog(getString(R.string.networkError), true, R.drawable.ic_error_black_24dp);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(User.KEY_USER_NAME, user.getUserName());
                params.put(User.KEY_USER_GENDER, user.getUserGender());
                params.put(User.KEY_USER_PHONE, user.getUserPhone());
                params.put(User.KEY_USER_EMAIL, user.getUserEmail());
                params.put(User.KEY_USER_ADDRESS, user.getUserAddress());
                params.put(User.KEY_USER_CITY, user.getCityModel().getCityId());
                params.put(User.KEY_USER_SELECTED_EXAM_ID, user.getExamModel().getExamId());
                params.put(User.KEY_USER_SELECTED_SUBJECT_IDS, selectedSubjectsIds);
                params.put(User.KEY_USER_SELECTED_COUPON_ID, user.getCouponModel().getCouponId());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request, "RegistrationVolley");
    }

    public void saveUserDetailsAndGoAhead(User user) {
        UserDetails userDetails = new UserDetails(RegistrationActivity.this);
        userDetails.setUserDetails(user);

        dialogMsg = new DialogMsg(RegistrationActivity.this);
        dialogMsg.showDialog("Success!\nYou are successfully registered", true, R.drawable.ic_assignment_turned_in_black_24dp, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMsg.getDialog().dismiss();
            }
        });

        dialogMsg.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                goToDashboard();
            }
        });
    }

    public void goToDashboard() {
        Intent intent = new Intent(RegistrationActivity.this, Dashboard_Activity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                registerAfterValidating();
                break;
            case R.id.relCitySelect:
                onClickFillCityOrExamAdapter("Select City");
                break;
            case R.id.relExamSelect:
                onClickFillCityOrExamAdapter("Select Exam");
                break;
            case R.id.linHaveACoupon:
                int imageRes, visibility;
                if (haveACoupon) {
                    haveACoupon = false;
                    imageRes = R.drawable.ic_check_box_outline_blank_black_24dp;
                    visibility = View.GONE;
                } else {
                    haveACoupon = true;
                    imageRes = R.drawable.ic_check_box_black_24dp;
                    visibility = View.VISIBLE;
                }

                imgCouponCheck.setImageResource(imageRes);
                inputLayoutPromocode.setVisibility(visibility);

                if (haveACoupon) {
                    edtPromoCode.requestFocus();
                }

                break;
        }
    }
}

