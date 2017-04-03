package me.fourground.raisal.data.model;

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
     */

    private String appId;
    private String appName;
    private String appStatus;
    private String startDtm;
    private String endDtm;
    private String targetOsCode;
    private float appraisalAvg;
    private int nPartyUserCount;


    public AppInfoData(String appId, String appName, String appStatus, String startDtm, String endDtm, String targetOsCode, float appraisalAvg, int nPartyUserCount) {
        this.appId = appId;
        this.appName = appName;
        this.appStatus = appStatus;
        this.startDtm = startDtm;
        this.endDtm = endDtm;
        this.targetOsCode = targetOsCode;
        this.appraisalAvg = appraisalAvg;
        this.nPartyUserCount = nPartyUserCount;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appId);
        dest.writeString(this.appName);
        dest.writeString(this.appStatus);
        dest.writeFloat(this.appraisalAvg);
        dest.writeString(this.endDtm);
        dest.writeInt(this.nPartyUserCount);
        dest.writeString(this.startDtm);
        dest.writeString(this.targetOsCode);
    }


    protected AppInfoData(Parcel in) {
        this.appId = in.readString();
        this.appName = in.readString();
        this.appStatus = in.readString();
        this.appraisalAvg = in.readFloat();
        this.endDtm = in.readString();
        this.nPartyUserCount = in.readInt();
        this.startDtm = in.readString();
        this.targetOsCode = in.readString();
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
                ", appraisalAvg='" + appraisalAvg + '\'' +
                ", endDtm='" + endDtm + '\'' +
                ", nPartyUserCount=" + nPartyUserCount +
                ", startDtm='" + startDtm + '\'' +
                ", targetOsCode='" + targetOsCode + '\'' +
                '}';
    }
}
