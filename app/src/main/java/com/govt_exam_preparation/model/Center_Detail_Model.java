package com.govt_exam_preparation.model;

import java.util.ArrayList;

/**
 * Created by wscubetech on 6/10/15.
 */
public class Center_Detail_Model {

    String coaching_id="",coaching_name="", coaching_address="",coaching_phone = "",coaching_cources_id = "",
            coaching_cources_name = "";
    ArrayList<Subjects_Sub_Category> sub_cat = new ArrayList<>();

    public Center_Detail_Model(String coaching_id, String coaching_name, String coaching_address,
                               String coaching_phone, String coaching_cources_id, String coaching_cources_name,
                               ArrayList<Subjects_Sub_Category> sub_cat) {
        this.coaching_id = coaching_id;
        this.coaching_name = coaching_name;
        this.coaching_address = coaching_address;
        this.coaching_phone = coaching_phone;
        this.coaching_cources_id = coaching_cources_id;
        this.coaching_cources_name = coaching_cources_name;
        this.sub_cat = sub_cat;
    }

    public String getCoaching_id() {
        return coaching_id;
    }

    public void setCoaching_id(String coaching_id) {
        this.coaching_id = coaching_id;
    }

    public String getCoaching_name() {
        return coaching_name;
    }

    public void setCoaching_name(String coaching_name) {
        this.coaching_name = coaching_name;
    }

    public String getCoaching_address() {
        return coaching_address;
    }

    public void setCoaching_address(String coaching_address) {
        this.coaching_address = coaching_address;
    }

    public String getCoaching_phone() {
        return coaching_phone;
    }

    public void setCoaching_phone(String coaching_phone) {
        this.coaching_phone = coaching_phone;
    }

    public String getCoaching_cources_id() {
        return coaching_cources_id;
    }

    public void setCoaching_cources_id(String coaching_cources_id) {
        this.coaching_cources_id = coaching_cources_id;
    }

    public String getCoaching_cources_name() {
        return coaching_cources_name;
    }

    public void setCoaching_cources_name(String coaching_cources_name) {
        this.coaching_cources_name = coaching_cources_name;
    }

    public ArrayList<Subjects_Sub_Category> getSub_cat() {
        return sub_cat;
    }

    public void setSub_cat(ArrayList<Subjects_Sub_Category> sub_cat) {
        this.sub_cat = sub_cat;
    }
}
