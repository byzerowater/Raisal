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
         * appraisalAvg : string
         * nPartyUserCount : 0
         * stat : string
         * targetOsCode : string
         */

        private String appId;
        private String appName;
        private String appraisalAvg;
        private int nPartyUserCount;
        private String stat;
        private String targetOsCode;

    public AppInfoData(String appId, String appName, String appraisalAvg, int nPartyUserCount, String stat, String targetOsCode) {
        this.appId = appId;
        this.appName = appName;
        this.appraisalAvg = appraisalAvg;
        this.nPartyUserCount = nPartyUserCount;
        this.stat = stat;
        this.targetOsCode = targetOsCode;
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

        public String getAppraisalAvg() {
            return appraisalAvg;
        }

        public void setAppraisalAvg(String appraisalAvg) {
            this.appraisalAvg = appraisalAvg;
        }

        public int getNPartyUserCount() {
            return nPartyUserCount;
        }

        public void setNPartyUserCount(int nPartyUserCount) {
            this.nPartyUserCount = nPartyUserCount;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
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
        dest.writeString(this.appraisalAvg);
        dest.writeInt(this.nPartyUserCount);
        dest.writeString(this.stat);
        dest.writeString(this.targetOsCode);
    }

    protected AppInfoData(Parcel in) {
        this.appId = in.readString();
        this.appName = in.readString();
        this.appraisalAvg = in.readString();
        this.nPartyUserCount = in.readInt();
        this.stat = in.readString();
        this.targetOsCode = in.readString();
    }

    public static final Parcelable.Creator<AppInfoData> CREATOR = new Parcelable.Creator<AppInfoData>() {
        @Override
        public AppInfoData createFromParcel(Parcel source) {
            return new AppInfoData(source);
        }

        @Override
        public AppInfoData[] newArray(int size) {
            return new AppInfoData[size];
        }
    };
}
