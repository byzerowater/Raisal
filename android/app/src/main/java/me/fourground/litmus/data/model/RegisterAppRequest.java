package me.fourground.litmus.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class RegisterAppRequest implements Parcelable {


    /**
     * appName : 앱이름
     * title : 앱평가 제목
     * reqTerm : 테스트기간
     * appDesc : 앱설명
     * downInfo : [{"platformCode":"OS종류(IOS,ADR)","downUrl":"다운로드주소"},{"platformCode":"OS종류","downUrl":"다운로드주소"}]
     */

    private String appName;
    private String title;
    private String reqTerm;
    private String appDesc;
    private List<StoreInfoData> downInfo;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReqTerm() {
        return reqTerm;
    }

    public void setReqTerm(String reqTerm) {
        this.reqTerm = reqTerm;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public List<StoreInfoData> getDownInfo() {
        return downInfo;
    }

    public void setDownInfo(List<StoreInfoData> downInfo) {
        this.downInfo = downInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appName);
        dest.writeString(this.title);
        dest.writeString(this.reqTerm);
        dest.writeString(this.appDesc);
        dest.writeList(this.downInfo);
    }

    public RegisterAppRequest() {
    }

    protected RegisterAppRequest(Parcel in) {
        this.appName = in.readString();
        this.title = in.readString();
        this.reqTerm = in.readString();
        this.appDesc = in.readString();
        this.downInfo = new ArrayList<StoreInfoData>();
        in.readList(this.downInfo, StoreInfoData.class.getClassLoader());
    }

    public static final Parcelable.Creator<RegisterAppRequest> CREATOR = new Parcelable.Creator<RegisterAppRequest>() {
        @Override
        public RegisterAppRequest createFromParcel(Parcel source) {
            return new RegisterAppRequest(source);
        }

        @Override
        public RegisterAppRequest[] newArray(int size) {
            return new RegisterAppRequest[size];
        }
    };
}
