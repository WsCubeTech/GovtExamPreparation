package com.govt_exam_preparation.model;

import java.io.Serializable;

/**
 * Created by wscubetech on 26/6/16.
 */
public class TheoryDetailModel implements Serializable {
    String theoryId="",theoryTitle="",theoryDesc="",theoryImage="";

    public String getTheoryId() {
        return theoryId;
    }

    public void setTheoryId(String theoryId) {
        this.theoryId = theoryId;
    }

    public String getTheoryTitle() {
        return theoryTitle;
    }

    public void setTheoryTitle(String theoryTitle) {
        this.theoryTitle = theoryTitle;
    }

    public String getTheoryDesc() {
        return theoryDesc;
    }

    public void setTheoryDesc(String theoryDesc) {
        this.theoryDesc = theoryDesc;
    }

    public String getTheoryImage() {
        return theoryImage;
    }

    public void setTheoryImage(String theoryImage) {
        this.theoryImage = theoryImage;
    }
}
