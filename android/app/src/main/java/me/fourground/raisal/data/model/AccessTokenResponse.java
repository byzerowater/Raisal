package me.fourground.raisal.data.model;

import android.os.Parcel;

import me.fourground.raisal.data.model.base.BaseResponse;

/**
 * Created by YoungSoo Kim on 2016-07-29.
 * company Ltd
 * youngsoo.kim@yap.net
 */
public class AccessTokenResponse extends BaseResponse {
    private AccessTokenData data = null;

    public AccessTokenData getData() {
        return data;
    }

    public void setData(AccessTokenData data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.data, flags);
    }

    public AccessTokenResponse() {
    }

    protected AccessTokenResponse(Parcel in) {
        super(in);
        this.data = in.readParcelable(AccessTokenData.class.getClassLoader());
    }

    public static final Creator<AccessTokenResponse> CREATOR = new Creator<AccessTokenResponse>() {
        @Override
        public AccessTokenResponse createFromParcel(Parcel source) {
            return new AccessTokenResponse(source);
        }

        @Override
        public AccessTokenResponse[] newArray(int size) {
            return new AccessTokenResponse[size];
        }
    };

    @Override
    public String toString() {
        return "AccessTokenResponse{" +
                "data=" + data +
                '}';
    }
}
