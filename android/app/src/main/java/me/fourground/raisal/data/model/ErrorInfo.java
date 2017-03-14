package me.fourground.raisal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoungSoo Kim on 2016-07-29.
 * company Ltd
 * youngsoo.kim@yap.net
 */
public class ErrorInfo implements Parcelable {
    /**
     * 에러메시지
     */
    private String error = null;
    /**
     *  에러가 발생된 입력ID
     */
    private String targetId = null;
    /**
     * 에러가 발생된 행
     */
    private int targetRow = -1;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error);
        dest.writeString(this.targetId);
        dest.writeInt(this.targetRow);
    }

    public ErrorInfo() {
    }

    protected ErrorInfo(Parcel in) {
        this.error = in.readString();
        this.targetId = in.readString();
        this.targetRow = in.readInt();
    }

    public static final Parcelable.Creator<ErrorInfo> CREATOR = new Parcelable.Creator<ErrorInfo>() {
        @Override
        public ErrorInfo createFromParcel(Parcel source) {
            return new ErrorInfo(source);
        }

        @Override
        public ErrorInfo[] newArray(int size) {
            return new ErrorInfo[size];
        }
    };
}
