package me.fourground.litmus.data.model;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class ReviewData {


    /**
     * appComment : string
     * appElement : {"contents":"string","design":"string","satisfaction":"string","useful":"string"}
     * targetOsCode : string
     * userName : string
     */

    private String appId;
    private String appComment;
    private PointData appElement;
    private String targetOsCode;
    private String userName;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

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

    @Override
    public String toString() {
        return "ReviewData{" +
                "appId='" + appId + '\'' +
                ", appComment='" + appComment + '\'' +
                ", appElement=" + appElement +
                ", targetOsCode='" + targetOsCode + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
