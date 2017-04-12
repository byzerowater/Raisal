package me.fourground.litmus.data.model;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MyReviewData {


    /**
     * appComment : string
     * appElement : {"contents":"string","design":"string","satisfaction":"string","useful":"string"}
     * targetOsCode : string
     * userName : string
     * appInfo :
     */

    private String appComment;
    private PointData appElement;
    private String targetOsCode;
    private String userName;
    private AppInfoData appInfo;

    public String getAppComment() {
        return appComment;
    }

    public void setAppComment(String appComment) {
        this.appComment = appComment;
    }

    public PointData getAppElement() {
        return appElement;
    }

    public void setAppElement(PointData appElement) {
        this.appElement = appElement;
    }

    public String getTargetOsCode() {
        return targetOsCode;
    }

    public void setTargetOsCode(String targetOsCode) {
        this.targetOsCode = targetOsCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AppInfoData getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfoData appInfo) {
        this.appInfo = appInfo;
    }

    @Override
    public String toString() {
        return "MyReviewData{" +
                "appComment='" + appComment + '\'' +
                ", appElement=" + appElement +
                ", targetOsCode='" + targetOsCode + '\'' +
                ", userName='" + userName + '\'' +
                ", appInfo=" + appInfo +
                '}';
    }
}
