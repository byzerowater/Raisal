package me.fourground.litmus.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoungSoo Kim on 2017-04-01.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class ContentData implements Parcelable {


    /**
     * appDesc : string
     * appDownloadUrl : string
     * appElement : {"contents":"string","design":"string","satisfaction":"string","useful":"string"}
     * appInfo : {"appId":"string","appName":"string","appStatus":"string","appraisalAvg":"string","endDtm":"string","nPartyUserCount":0,"startDtm":"string","targetOsCode":"string"}
     * modDtm : string
     * modId : string
     * regDtm : string
     * regId : string
     */

    private String appDesc;
    private String appDownloadUrl;
    private PointData appElement;
    private AppInfoData appInfo;
    private String modDtm;
    private String modId;
    private String regDtm;
    private String regId;

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    public PointData getAppElement() {
        return appElement;
    }

    public void setAppElement(PointData appElement) {
        this.appElement = appElement;
    }

    public AppInfoData getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfoData appInfo) {
        this.appInfo = appInfo;
    }

    public String getModDtm() {
        return modDtm;
    }

    public void setModDtm(String modDtm) {
        this.modDtm = modDtm;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getRegDtm() {
        return regDtm;
    }

    public void setRegDtm(String regDtm) {
        this.regDtm = regDtm;
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
        dest.writeString(this.appDesc);
        dest.writeString(this.appDownloadUrl);
        dest.writeParcelable(this.appElement, flags);
        dest.writeParcelable(this.appInfo, flags);
        dest.writeString(this.modDtm);
        dest.writeString(this.modId);
        dest.writeString(this.regDtm);
        dest.writeString(this.regId);
    }

    public ContentData() {
    }

    protected ContentData(Parcel in) {
        this.appDesc = in.readString();
        this.appDownloadUrl = in.readString();
        this.appElement = in.readParcelable(PointData.class.getClassLoader());
        this.appInfo = in.readParcelable(AppInfoData.class.getClassLoader());
        this.modDtm = in.readString();
        this.modId = in.readString();
        this.regDtm = in.readString();
        this.regId = in.readString();
    }

    public static final Parcelable.Creator<ContentData> CREATOR = new Parcelable.Creator<ContentData>() {
        @Override
        public ContentData createFromParcel(Parcel source) {
            return new ContentData(source);
        }

        @Override
        public ContentData[] newArray(int size) {
            return new ContentData[size];
        }
    };

    @Override
    public String toString() {
        return "ContentData{" +
                "appDesc='" + appDesc + '\'' +
                ", appDownloadUrl='" + appDownloadUrl + '\'' +
                ", appElement=" + appElement +
                ", appInfo=" + appInfo +
                ", modDtm='" + modDtm + '\'' +
                ", modId='" + modId + '\'' +
                ", regDtm='" + regDtm + '\'' +
                ", regId='" + regId + '\'' +
                '}';
    }
}
