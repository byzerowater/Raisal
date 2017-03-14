package me.fourground.raisal.data.model.base;

import android.os.Parcel;
import android.os.Parcelable;

import me.fourground.raisal.data.model.ErrorInfo;

import java.util.ArrayList;

/**
 * Created by YoungSoo Kim on 2016-07-29.
 * company Ltd
 * youngsoo.kim@yap.net
 */
public class BaseResponse implements Parcelable {
    /**
     * 전송일시분초
     */
    private String txDate;
    /**
     * 성공여부
     */
    private boolean success;
    /**
     * 총행수(페이징에 사용)
     */
    private int recordsTotal;
    /**
     * 현재페이지의 행수(페이징에 사용)
     */
    private int rowCnt;
    /**
     * 에러메시지(Http Status값이 2xx가 아닐 경우 필수반환)
     */
    private String error;
    /**
     * 에러가 발생된 입력ID(Http Status값이 2xx가 아닐 경우 필수반환)
     */
    private String targetId;
    /**
     * 에러가 발생된 행(Http Status값이 2xx가 아닐 경우 필수반환)
     */
    private int targetRow;
    /**
     * n개 이상의 에러발생(Http Status값이 2xx가 아닐 경우 필수반환)
     */
    private ArrayList<ErrorInfo> failStack;

    public String getTxDate() {
        return txDate;
    }

    public void setTxDate(String txDate) {
        this.txDate = txDate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRowCnt() {
        return rowCnt;
    }

    public void setRowCnt(int rowCnt) {
        this.rowCnt = rowCnt;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public ArrayList<ErrorInfo> getFailStack() {
        return failStack;
    }

    public void setFailStack(ArrayList<ErrorInfo> failStack) {
        this.failStack = failStack;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.txDate);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeInt(this.recordsTotal);
        dest.writeInt(this.rowCnt);
        dest.writeString(this.error);
        dest.writeString(this.targetId);
        dest.writeInt(this.targetRow);
        dest.writeTypedList(this.failStack);
    }

    public BaseResponse() {
    }

    protected BaseResponse(Parcel in) {
        this.txDate = in.readString();
        this.success = in.readByte() != 0;
        this.recordsTotal = in.readInt();
        this.rowCnt = in.readInt();
        this.error = in.readString();
        this.targetId = in.readString();
        this.targetRow = in.readInt();
        this.failStack = in.createTypedArrayList(ErrorInfo.CREATOR);
    }

    public static final Creator<BaseResponse> CREATOR = new Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel source) {
            return new BaseResponse(source);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };

    @Override
    public String toString() {
        return "BaseResponse{" +
                "txDate='" + txDate + '\'' +
                ", success=" + success +
                ", recordsTotal=" + recordsTotal +
                ", rowCnt=" + rowCnt +
                ", error='" + error + '\'' +
                ", targetId='" + targetId + '\'' +
                ", targetRow=" + targetRow +
                ", failStack=" + failStack +
                '}';
    }
}
