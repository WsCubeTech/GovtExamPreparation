package com.govt_exam_preparation.model;

/**
 * Created by wscubetech on 19/3/16.
 */
public class Result_Model {
    String question_no = "", choose_option = "", correct_ans = "";

    public Result_Model(String question_no, String choose_option, String correct_ans) {
        this.question_no = question_no;
        this.choose_option = choose_option;
        this.correct_ans = correct_ans;
    }

    public String getQuestion_no() {
        return question_no;
    }

    public void setQuestion_no(String question_no) {
        this.question_no = question_no;
    }

    public String getChoose_option() {
        return choose_option;
    }

    public void setChoose_option(String choose_option) {
        this.choose_option = choose_option;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }
}
