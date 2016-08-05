package com.govt_exam_preparation.user_model;


import com.govt_exam_preparation.model.CityModel;
import com.govt_exam_preparation.model.CouponModel;
import com.govt_exam_preparation.model.ExamModel;

/**
 * Created by wscubetech on 20/6/16.
 */
public class User {
    String userId = "", userName = "", userAddress = "", userGender = "", userPhone = "", userEmail = "";
    CouponModel couponModel=new CouponModel();


    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_PHONE = "user_phone";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_ADDRESS = "user_address";
    public static final String KEY_USER_CITY = "user_city";
    public static final String KEY_USER_GENDER = "user_gender";
    public static final String KEY_USER_SELECTED_EXAM_ID = "selected_exam_id";
    public static final String KEY_USER_SELECTED_SUBJECT_IDS = "selected_subject_ids";
    public static final String KEY_USER_SELECTED_COUPON_ID = "selected_coupon_id";


    public CouponModel getCouponModel() {
        return couponModel;
    }

    public void setCouponModel(CouponModel couponModel) {
        this.couponModel = couponModel;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    CityModel cityModel = new CityModel();
    ExamModel examModel = new ExamModel();

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }

    public ExamModel getExamModel() {
        return examModel;
    }

    public void setExamModel(ExamModel examModel) {
        this.examModel = examModel;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}
