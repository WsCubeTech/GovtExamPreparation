package com.govt_exam_preparation.model;

import java.io.Serializable;

/**
 * Created by wscubetech on 20/6/16.
 */
public class FavoriteModel implements Serializable {

    String quesId="",quesTitle="",quesOption1="",quesOption2="",quesOption3="",quesOption4="",quesImage="";
    String quesCorrect="",quesInput="";

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getQuesTitle() {
        return quesTitle;
    }

    public void setQuesTitle(String quesTitle) {
        this.quesTitle = quesTitle;
    }

    public String getQuesOption1() {
        return quesOption1;
    }

    public void setQuesOption1(String quesOption1) {
        this.quesOption1 = quesOption1;
    }

    public String getQuesOption2() {
        return quesOption2;
    }

    public void setQuesOption2(String quesOption2) {
        this.quesOption2 = quesOption2;
    }

    public String getQuesOption3() {
        return quesOption3;
    }

    public void setQuesOption3(String quesOption3) {
        this.quesOption3 = quesOption3;
    }

    public String getQuesOption4() {
        return quesOption4;
    }

    public void setQuesOption4(String quesOption4) {
        this.quesOption4 = quesOption4;
    }

    public String getQuesImage() {
        return quesImage;
    }

    public void setQuesImage(String quesImage) {
        this.quesImage = quesImage;
    }

    public String getQuesCorrect() {
        return quesCorrect;
    }

    public void setQuesCorrect(String quesCorrect) {
        this.quesCorrect = quesCorrect;
    }

    public String getQuesInput() {
        return quesInput;
    }

    public void setQuesInput(String quesInput) {
        this.quesInput = quesInput;
    }
}
