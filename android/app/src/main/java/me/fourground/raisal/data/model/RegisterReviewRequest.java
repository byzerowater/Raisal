package me.fourground.raisal.data.model;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class RegisterReviewRequest {


    /**
     * useTerm : 앱이용기간
     * platformCode : OS종류(IOS,ADR)
     * point : {"contents":4.8,"design":4.8,"durability":4.8,"useful":4.8}
     * comment : 사용소감
     */

    private String useTerm;
    private String platformCode;
    private PointData point;
    private String comment;

    public String getUseTerm() {
        return useTerm;
    }

    public void setUseTerm(String useTerm) {
        this.useTerm = useTerm;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public PointData getPoint() {
        return point;
    }

    public void setPoint(PointData point) {
        this.point = point;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
