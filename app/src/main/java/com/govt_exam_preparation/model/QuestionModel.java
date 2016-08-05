package com.govt_exam_preparation.model;

import java.io.Serializable;

/**
 * Created by wscubetech on 10/3/16.
 */
public class QuestionModel implements Serializable {

    String quesId = "", ques = "";
    byte [] quesImage;
    String option1 = "", option2 = "", option3 = "", option4 = "";


    //1 2 3 4
    String correctOptionSerialNo="";

    String type = "";





    int user_input = 0;

    String correct_ans = "";
    String wrong_ans = "";
    String favourite = "No";



    public QuestionModel()
    {

    }

    public QuestionModel(String quesId, String ques, byte[] quesImage, String option1, String option2,
                         String option3, String option4, String correctOptionSerialNo, String type,
                         int user_input, String correct_ans, String wrong_ans, String favourite) {
        this.quesId = quesId;
        this.ques = ques;
        this.quesImage = quesImage;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOptionSerialNo = correctOptionSerialNo;
        this.type = type;
        this.user_input = user_input;
        this.correct_ans = correct_ans;
        this.wrong_ans = wrong_ans;
        this.favourite = favourite;
    }


    public QuestionModel(String quesId, String ques, String option1, String option2,
                         String option3, String option4, String correctOptionSerialNo, String type,
                         int user_input, String correct_ans, String wrong_ans, String favourite) {
        this.quesId = quesId;
        this.ques = ques;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOptionSerialNo = correctOptionSerialNo;
        this.type = type;
        this.user_input = user_input;
        this.correct_ans = correct_ans;
        this.wrong_ans = wrong_ans;
        this.favourite = favourite;
    }


    public String getCorrectOptionSerialNo() {
        return correctOptionSerialNo;
    }

    public void setCorrectOptionSerialNo(String correctOptionSerialNo) {
        this.correctOptionSerialNo = correctOptionSerialNo;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public byte[] getQuesImage() {
        return quesImage;
    }

    public void setQuesImage(byte[] quesImage) {
        this.quesImage = quesImage;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getUser_input() {
        return user_input;
    }

    public void setUser_input(int user_input) {
        this.user_input = user_input;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }

    public String getWrong_ans() {
        return wrong_ans;
    }

    public void setWrong_ans(String wrong_ans) {
        this.wrong_ans = wrong_ans;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }


}
