package me.fourground.raisal.data.model;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class ReviewData {

    /**
     * appId : string
     * appName : string
     * appraisalAvg : 4.1
     * nPartyUserCount : 0
     * raisalStat : string
     * targetOsCode : string
     * title : string
     */

    private String appId;
    private String appName;
    private int nPartyUserCount;
    private String raisalStat;
    private String targetOsCode;
    private float appraisalAvg;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getNPartyUserCount() {
        return nPartyUserCount;
    }

    public void setNPartyUserCount(int nPartyUserCount) {
        this.nPartyUserCount = nPartyUserCount;
    }

    public String getRaisalStat() {
        return raisalStat;
    }

    public void setRaisalStat(String raisalStat) {
        this.raisalStat = raisalStat;
    }

    public String getTargetOsCode() {
        return targetOsCode;
    }

    public void setTargetOsCode(String targetOsCode) {
        this.targetOsCode = targetOsCode;
    }

    public float getAppraisalAvg() {
        return appraisalAvg;
    }

    public void setAppraisalAvg(float appraisalAvg) {
        this.appraisalAvg = appraisalAvg;
    }
}
