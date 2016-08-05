package com.govt_exam_preparation.model;

import android.view.View;

import java.io.Serializable;

/**
 * Created by wscubetech on 21/6/16.
 */
public class SubjectModel implements Serializable {

    String subjectId="",subjectName="";
    Boolean selected=false;
    Boolean isUniversal=false;
    View.OnClickListener onClickListener;

    public Boolean getUniversal() {
        return isUniversal;
    }

    public void setUniversal(Boolean universal) {
        isUniversal = universal;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
