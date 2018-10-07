package com.example.shreysindher.project_insight;

public class RequestItem {

    private Double mLatitude;
    private Double mLongitude;
    private String mAlert;
    private String mName;

    public RequestItem(Double mLatitude, Double mLongitude, String mAlert, String mName) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mAlert = mAlert;
        this.mName = mName;
    }

    public Double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmAlert() {
        return mAlert;
    }

    public void setmAlert(String mAlert) {
        this.mAlert = mAlert;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
