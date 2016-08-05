package com.govt_exam_preparation.user_model;

import android.app.Activity;


import com.govt_exam_preparation.model.CityModel;
import com.govt_exam_preparation.model.CouponModel;
import com.govt_exam_preparation.model.ExamModel;
import com.govt_exam_preparation.model.SubjectModel;
import com.govt_exam_preparation.others.GetSetSharedPrefs;

import java.util.ArrayList;

/**
 * Created by wscubetech on 20/6/16.
 */
public class UserDetails {

    Activity act;
    GetSetSharedPrefs prefs;


    public UserDetails(Activity act) {
        this.act = act;
        prefs = new GetSetSharedPrefs(act, "UserDetail");
    }

    public void setUserDetails(User user) {
        prefs.putData("USER_ID", user.getUserId());
        prefs.putData("USER_NAME", user.getUserName());
        prefs.putData("USER_PHONE", user.getUserPhone());
        prefs.putData("USER_EMAIL", user.getUserEmail());
        prefs.putData("USER_ADDRESS", user.getUserAddress());
        prefs.putData("USER_GENDER", user.getUserGender());

        CouponModel couponModel=user.getCouponModel();
        prefs.putData("USER_COUPON_ID",couponModel.getCouponId());
        prefs.putData("USER_COUPON_CODE",couponModel.getCouponCode());
        prefs.putData("USER_COUPON_DISCOUNT",couponModel.getCouponDiscount());

        CityModel cityModel = user.getCityModel();
        prefs.putData("USER_CITY_ID", cityModel.getCityId());
        prefs.putData("USER_CITY_NAME", cityModel.getCityName());

        ExamModel examModel = user.getExamModel();
        prefs.putData("USER_EXAM_ID", examModel.getExamId());
        prefs.putData("USER_EXAM_NAME", examModel.getExamName());

        ArrayList<SubjectModel> arraySubjectModel = examModel.getArraySubjectModel();
        prefs.putData("USER_SUBJECT_COUNT", arraySubjectModel.size() + "");
        int i = 1;
        for (SubjectModel model : arraySubjectModel) {
            prefs.putData("USER_SUBJECT_ID_" + i, model.getSubjectId());
            prefs.putData("USER_SUBJECT_NAME_" + i, model.getSubjectName());
            i += 1;
        }

    }

    public User getUserDetail() {
        User user = new User();
        user.setUserId(prefs.getData("USER_ID"));
        user.setUserName(prefs.getData("USER_NAME"));
        user.setUserPhone(prefs.getData("USER_PHONE"));
        user.setUserEmail(prefs.getData("USER_EMAIL"));
        user.setUserGender(prefs.getData("USER_GENDER"));
        user.setUserAddress(prefs.getData("USER_ADDRESS"));

        CouponModel couponModel=new CouponModel();
        couponModel.setCouponId(prefs.getData("USER_COUPON_ID"));
        couponModel.setCouponCode(prefs.getData("USER_COUPON_CODE"));
        couponModel.setCouponDiscount(prefs.getData("USER_COUPON_DISCOUNT"));

        CityModel cityModel = new CityModel();
        cityModel.setCityId(prefs.getData("USER_CITY_ID"));
        cityModel.setCityName(prefs.getData("USER_CITY_NAME"));

        user.setCityModel(cityModel);

        ExamModel examModel = new ExamModel();
        examModel.setExamId(prefs.getData("USER_EXAM_ID"));
        examModel.setExamName(prefs.getData("USER_EXAM_NAME"));

        ArrayList<SubjectModel> arraySubjectModel = new ArrayList<>();
        try {
            int subjectCount = Integer.parseInt(prefs.getData("USER_SUBJECT_COUNT"));
            for (int i = 0; i < subjectCount; i++) {
                SubjectModel subjectModel = new SubjectModel();
                subjectModel.setSubjectId(prefs.getData("USER_SUBJECT_ID_" + (i + 1)));
                subjectModel.setSubjectName(prefs.getData("USER_SUBJECT_NAME_" + (i + 1)));
                arraySubjectModel.add(subjectModel);
            }
            examModel.setArraySubjectModel(arraySubjectModel);
        } catch (Exception e) {

        }


        user.setExamModel(examModel);

        return user;
    }

}
