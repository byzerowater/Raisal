package me.fourground.raisal.data.model;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class StoreInfoData {
    /**
     * platformCode : OS종류(IOS,ADR)
     * downUrl : 다운로드주소
     */
    private String platformCode;
    private String downUrl;

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }
}
