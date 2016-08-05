package com.govt_exam_preparation.model;

import java.io.Serializable;

/**
 * Created by wscubetech on 22/6/16.
 */
public class CouponModel implements Serializable {
    String couponId = "", couponCode = "", couponDiscount = "";

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(String couponDiscount) {
        this.couponDiscount = couponDiscount;
    }
}
