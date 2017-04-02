package me.fourground.raisal.data.model;

/**
 * Created by YoungSoo Kim on 2017-04-01.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class ContentData {


    /**
     * appDesc : string
     * appDownloadUrl : string
     * appInfoVo : {"appId":"string","appName":"string","appraisalAvg":"string","nPartyUserCount":0,"raisalStat":"string","targetOsCode":"string"}
     * endDtm : string
     * startDtm : string
     */

    private String appDesc;
    private String appDownloadUrl;
    private AppInfoData appInfoVo;
    private String endDtm;
    private String startDtm;

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    public AppInfoData getAppInfoVo() {
        return appInfoVo;
    }

    public void setAppInfoVo(AppInfoData appInfoVo) {
        this.appInfoVo = appInfoVo;
    }

    public String getEndDtm() {
        return endDtm;
    }

    public void setEndDtm(String endDtm) {
        this.endDtm = endDtm;
    }

    public String getStartDtm() {
        return startDtm;
    }

    public void setStartDtm(String startDtm) {
        this.startDtm = startDtm;
    }
}
