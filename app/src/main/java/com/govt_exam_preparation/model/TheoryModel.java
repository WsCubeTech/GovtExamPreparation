package com.govt_exam_preparation.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wscubetech on 26/6/16.
 */
public class TheoryModel implements Serializable {
    String title="";
    ArrayList<TheoryDetailModel> arrayTheoryDetailModel=new ArrayList<>();

    public ArrayList<TheoryDetailModel> getArrayTheoryDetailModel() {
        return arrayTheoryDetailModel;
    }

    public void setArrayTheoryDetailModel(ArrayList<TheoryDetailModel> arrayTheoryDetailModel) {
        this.arrayTheoryDetailModel = arrayTheoryDetailModel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
