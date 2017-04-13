package me.fourground.litmus.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoungSoo Kim on 2017-04-01.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class AppInfoData implements Parcelable {


    /**
     * appId : string
     * appName : string
     * appStatus : string
     * appraisalAvg : string
     * endDtm : string
     * nPartyUserCount : 0
     * startDtm : string
     * targetOsCode : string
     * regId : string
     */

    private String appId;
    private String appName;
    private String appStatus;
    private String startDtm;
    private String endDtm;
    private String targetOsCode;
    private float appraisalAvg;
    private int nPartyUserCount;
    private String regId;


    public AppInfoData(String appId, String appName, String appStatus, String startDtm, String endDtm, String targetOsCode, float appraisalAvg, int nPartyUserCount, String regId) {
        this.appId = appId;
        this.appName = appName;
        this.appStatus = appStatus;
        this.startDtm = startDtm;
        this.endDtm = endDtm;
        this.targetOsCode = targetOsCode;
        this.appraisalAvg = appraisalAvg;
        this.nPartyUserCount = nPartyUserCount;
        this.regId = regId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public float getAppraisalAvg() {
        return appraisalAvg;
    }

    public void setAppraisalAvg(float appraisalAvg) {
        this.appraisalAvg = appraisalAvg;
    }

    public String getEndDtm() {
        return endDtm;
    }

    public void setEndDtm(String endDtm) {
        this.endDtm = endDtm;
    }

    public int getNPartyUserCount() {
        return nPartyUserCount;
    }

    public void setNPartyUserCount(int nPartyUserCount) {
        this.nPartyUserCount = nPartyUserCount;
    }

    public String getStartDtm() {
        return startDtm;
    }

    public void setStartDtm(String startDtm) {
        this.startDtm = startDtm;
    }

    public String getTargetOsCode() {
        return targetOsCode;
    }

    public void setTargetOsCode(String targetOsCode) {
        this.targetOsCode = targetOsCode;
    }

    public int getnPartyUserCount() {
        return nPartyUserCount;
    }

    public void setnPartyUserCount(int nPartyUserCount) {
        this.nPartyUserCount = nPartyUserCount;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appId);
        dest.writeString(this.appName);
        dest.writeString(this.appStatus);
        dest.writeString(this.startDtm);
        dest.writeString(this.endDtm);
        dest.writeString(this.targetOsCode);
        dest.writeFloat(this.appraisalAvg);
        dest.writeInt(this.nPartyUserCount);
        dest.writeString(this.regId);
    }

    protected AppInfoData(Parcel in) {
        this.appId = in.readString();
        this.appName = in.readString();
        this.appStatus = in.readString();
        this.startDtm = in.readString();
        this.endDtm = in.readString();
        this.targetOsCode = in.readString();
        this.appraisalAvg = in.readFloat();
        this.nPartyUserCount = in.readInt();
        this.regId = in.readString();
    }

    public static final Creator<AppInfoData> CREATOR = new Creator<AppInfoData>() {
        @Override
        public AppInfoData createFromParcel(Parcel source) {
            return new AppInfoData(source);
        }

        @Override
        public AppInfoData[] newArray(int size) {
            return new AppInfoData[size];
        }
    };

    @Override
    public String toString() {
        return "AppInfoData{" +
                "appId='" + appId + '\'' +
                ", appName='" + appName + '\'' +
                ", appStatus='" + appStatus + '\'' +
                ", startDtm='" + startDtm + '\'' +
                ", endDtm='" + endDtm + '\'' +
                ", targetOsCode='" + targetOsCode + '\'' +
                ", appraisalAvg=" + appraisalAvg +
                ", nPartyUserCount=" + nPartyUserCount +
                ", regId='" + regId + '\'' +
                '}';
    }
}
