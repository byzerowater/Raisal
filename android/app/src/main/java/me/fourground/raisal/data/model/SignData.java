package me.fourground.raisal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class SignData implements Parcelable {

    /**
     * authKey
     */
    private String authKey;
    /**
     * 닉네임
     */
    private String nickName;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.authKey);
        dest.writeString(this.nickName);
    }

    protected SignData(Parcel in) {
        this.authKey = in.readString();
        this.nickName = in.readString();
    }

    public static final Creator<SignData> CREATOR = new Creator<SignData>() {
        @Override
        public SignData createFromParcel(Parcel source) {
            return new SignData(source);
        }

        @Override
        public SignData[] newArray(int size) {
            return new SignData[size];
        }
    };
}
