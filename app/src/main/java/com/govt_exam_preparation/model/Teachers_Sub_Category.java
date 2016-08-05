package com.govt_exam_preparation.model;

/**
 * Created by wscubetech on 16/3/16.
 */
public class Teachers_Sub_Category {
    int k=0;

    String coaching_teacher_id="", coaching_teacher_name="", coaching_teacher_qualification="";

    public Teachers_Sub_Category(int k, String coaching_teacher_id, String coaching_teacher_name,
                                 String coaching_teacher_qualification) {
        this.k = k;
        this.coaching_teacher_id = coaching_teacher_id;
        this.coaching_teacher_name = coaching_teacher_name;
        this.coaching_teacher_qualification = coaching_teacher_qualification;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String getCoaching_teacher_id() {
        return coaching_teacher_id;
    }

    public void setCoaching_teacher_id(String coaching_teacher_id) {
        this.coaching_teacher_id = coaching_teacher_id;
    }

    public String getCoaching_teacher_name() {
        return coaching_teacher_name;
    }

    public void setCoaching_teacher_name(String coaching_teacher_name) {
        this.coaching_teacher_name = coaching_teacher_name;
    }

    public String getCoaching_teacher_qualification() {
        return coaching_teacher_qualification;
    }

    public void setCoaching_teacher_qualification(String coaching_teacher_qualification) {
        this.coaching_teacher_qualification = coaching_teacher_qualification;
    }
}
