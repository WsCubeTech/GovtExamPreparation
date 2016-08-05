package com.govt_exam_preparation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wscubetech on 9/3/16.
 */
public class Coaching_List_Model implements Parcelable {



    String id = "",name = "",address = "",contact_no = "",image = "";

    public Coaching_List_Model(String id,String name, String address, String contact_no, String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact_no = contact_no;
        this.image = image;
    }

    protected Coaching_List_Model(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        contact_no = in.readString();
        image = in.readString();
    }

    public static final Creator<Coaching_List_Model> CREATOR = new Creator<Coaching_List_Model>() {
        @Override
        public Coaching_List_Model createFromParcel(Parcel in) {
            return new Coaching_List_Model(in);
        }

        @Override
        public Coaching_List_Model[] newArray(int size) {
            return new Coaching_List_Model[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(contact_no);
        dest.writeString(image);
    }
}
