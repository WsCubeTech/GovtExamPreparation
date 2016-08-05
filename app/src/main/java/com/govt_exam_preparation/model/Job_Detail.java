package com.govt_exam_preparation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wscubetech on 9/3/16.
 */
public class Job_Detail implements Parcelable{
    String title = "",post = "",eligibility = "",salary_range = "",fees = "",last_date = "",descrition = "",
           url_Official_Site = "";

    public Job_Detail(String title, String post, String eligibility, String salary_range, String fees,
                      String last_date, String descrition, String url_Official_Site) {
        this.title = title;
        this.post = post;
        this.eligibility = eligibility;
        this.salary_range = salary_range;
        this.fees = fees;
        this.last_date = last_date;
        this.descrition = descrition;
        this.url_Official_Site = url_Official_Site;
    }

    protected Job_Detail(Parcel in) {
        title = in.readString();
        post = in.readString();
        eligibility = in.readString();
        salary_range = in.readString();
        fees = in.readString();
        last_date = in.readString();
        descrition = in.readString();
        url_Official_Site = in.readString();
    }

    public static final Creator<Job_Detail> CREATOR = new Creator<Job_Detail>() {
        @Override
        public Job_Detail createFromParcel(Parcel in) {
            return new Job_Detail(in);
        }

        @Override
        public Job_Detail[] newArray(int size) {
            return new Job_Detail[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getSalary_range() {
        return salary_range;
    }

    public void setSalary_range(String salary_range) {
        this.salary_range = salary_range;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getUrl_Official_Site() {
        return url_Official_Site;
    }

    public void setUrl_Official_Site(String url_Official_Site) {
        this.url_Official_Site = url_Official_Site;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(post);
        dest.writeString(eligibility);
        dest.writeString(salary_range);
        dest.writeString(fees);
        dest.writeString(last_date);
        dest.writeString(descrition);
        dest.writeString(url_Official_Site);
    }
}

