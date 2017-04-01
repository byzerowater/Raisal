package me.fourground.raisal.data.model;

import java.util.List;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class RegisterAppRequest {


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
}
