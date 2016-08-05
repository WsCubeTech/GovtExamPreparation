package com.govt_exam_preparation.model;

import java.io.Serializable;

/**
 * Created by wscubetech on 21/6/16.
 */
public class OptionModel implements Serializable {

    String option="";
    Boolean isMyInput=false,isCorrect=false;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Boolean getMyInput() {
        return isMyInput;
    }

    public void setMyInput(Boolean myInput) {
        isMyInput = myInput;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
