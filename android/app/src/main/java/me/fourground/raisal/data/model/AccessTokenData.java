package me.fourground.raisal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoungSoo Kim on 2016-07-29.
 * company Ltd
 * youngsoo.kim@yap.net
 */
public class AccessTokenData implements Parcelable {
    /**
     * REST 통신인증을 위한 서버발급ID
     */
    private String clientId;
    /**
     * 사용자 정보 접근을 위해 서버에서 발급한 ID
     */
    private String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.clientId);
        dest.writeString(this.clientSecret);
    }

    public AccessTokenData() {
    }

    protected AccessTokenData(Parcel in) {
        this.clientId = in.readString();
        this.clientSecret = in.readString();
    }

    public static final Parcelable.Creator<AccessTokenData> CREATOR = new Parcelable.Creator<AccessTokenData>() {
        @Override
        public AccessTokenData createFromParcel(Parcel source) {
            return new AccessTokenData(source);
        }

        @Override
        public AccessTokenData[] newArray(int size) {
            return new AccessTokenData[size];
        }
    };

    @Override
    public String toString() {
        return "AccessTokenData{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}
