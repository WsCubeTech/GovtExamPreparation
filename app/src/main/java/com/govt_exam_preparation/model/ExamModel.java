package com.govt_exam_preparation.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wscubetech on 21/6/16.
 */
public class ExamModel implements Serializable {

    String examId = "", examName = "";
    ArrayList<SubjectModel> arraySubjectModel = new ArrayList<>();

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public ArrayList<SubjectModel> getArraySubjectModel() {
        return arraySubjectModel;
    }

    public void setArraySubjectModel(ArrayList<SubjectModel> arraySubjectModel) {
        this.arraySubjectModel = arraySubjectModel;
    }
}
