package com.govt_exam_preparation.model;

import java.util.ArrayList;

/**
 * Created by wscubetech on 16/3/16.
 */
public class Subjects_Sub_Category {
    int j=0;

    String coaching_cources_sub_id="",coaching_cources_sub_name="";
    ArrayList<Teachers_Sub_Category> teacher_list;

    public Subjects_Sub_Category(int j, String coaching_cources_sub_id, String coaching_cources_sub_name, ArrayList<Teachers_Sub_Category> teacher_list) {
        this.j = j;
        this.coaching_cources_sub_id = coaching_cources_sub_id;
        this.coaching_cources_sub_name = coaching_cources_sub_name;
        this.teacher_list = teacher_list;
    }

    public ArrayList<Teachers_Sub_Category> getTeacher_list() {

        return teacher_list;
    }

    public void setTeacher_list(ArrayList<Teachers_Sub_Category> teacher_list) {
        this.teacher_list = teacher_list;
    }

  /*  public Subjects_Sub_Category(int j, String coaching_cources_sub_id, String coaching_cources_sub_name) {
        this.j = j;
        this.coaching_cources_sub_id = coaching_cources_sub_id;
        this.coaching_cources_sub_name = coaching_cources_sub_name;
    }
*/
    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public String getCoaching_cources_sub_id() {
        return coaching_cources_sub_id;
    }

    public void setCoaching_cources_sub_id(String coaching_cources_sub_id) {
        this.coaching_cources_sub_id = coaching_cources_sub_id;
    }

    public String getCoaching_cources_sub_name() {
        return coaching_cources_sub_name;
    }

    public void setCoaching_cources_sub_name(String coaching_cources_sub_name) {
        this.coaching_cources_sub_name = coaching_cources_sub_name;
    }
}
