package com.govt_exam_preparation.model;

import java.io.Serializable;

/**
 * Created by wscubetech on 20/6/16.
 */
public class CityModel implements Serializable {

    String cityId="",cityName="";

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
