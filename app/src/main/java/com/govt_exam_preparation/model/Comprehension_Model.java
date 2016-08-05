package com.govt_exam_preparation.model;

/**
 * Created by wscubetech on 4/4/16.
 */
public class Comprehension_Model {

    String question_text_id = "";
    String question_text = "";
    byte []  question_image;
    String question_type = "";

    public Comprehension_Model(String question_text_id, String question_text, byte[] question_image,
                               String question_type) {
        this.question_text_id = question_text_id;
        this.question_text = question_text;
        this.question_image = question_image;
        this.question_type = question_type;
    }

    public Comprehension_Model(String question_text_id, String question_text,
                               String question_type) {
        this.question_text_id = question_text_id;
        this.question_text = question_text;

        this.question_type = question_type;
    }

    public Comprehension_Model() {

    }

    public String getQuestion_text_id() {
        return question_text_id;
    }

    public void setQuestion_text_id(String question_text_id) {
        this.question_text_id = question_text_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public byte[] getQuestion_image() {
        return question_image;
    }

    public void setQuestion_image(byte[] question_image) {
        this.question_image = question_image;
    }

}
