package com.govt_exam_preparation.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wscubetech on 21/6/16.
 */
public class QuizModel implements Serializable {

    String quesId = "", quesTitle = "", quesImage = "";
    String quesOption1="",quesOption2="",quesOption3="",quesOption4="";
    String quesCorrectSerialNo = "", quesInputSerialNo = "";
    Boolean isFavorite = false;

    ArrayList<OptionModel> arrayOptionModel=new ArrayList<>();

    ExamModel examModel=new ExamModel();
    SubjectModel subjectModel=new SubjectModel();

    public ExamModel getExamModel() {
        return examModel;
    }

    public void setExamModel(ExamModel examModel) {
        this.examModel = examModel;
    }

    public SubjectModel getSubjectModel() {
        return subjectModel;
    }

    public void setSubjectModel(SubjectModel subjectModel) {
        this.subjectModel = subjectModel;
    }

    public ArrayList<OptionModel> getArrayOptionModel() {
        return arrayOptionModel;
    }

    public void setArrayOptionModel(ArrayList<OptionModel> arrayOptionModel) {
        this.arrayOptionModel = arrayOptionModel;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

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


    public String getQuesImage() {
        return quesImage;
    }

    public void setQuesImage(String quesImage) {
        this.quesImage = quesImage;
    }

    public String getQuesCorrectSerialNo() {
        return quesCorrectSerialNo;
    }

    public void setQuesCorrectSerialNo(String quesCorrectSerialNo) {
        this.quesCorrectSerialNo = quesCorrectSerialNo;
    }

    public String getQuesInputSerialNo() {
        return quesInputSerialNo;
    }

    public void setQuesInputSerialNo(String quesInputSerialNo) {
        this.quesInputSerialNo = quesInputSerialNo;
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
}
