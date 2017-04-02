package me.fourground.raisal.data.model;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class ReviewData {


    /**
     * comment : string
     * point : {"contents":"string","design":"string","satisfaction":"string","useful":"string"}
     * userName : string
     */

    private String comment;
    private PointData point;
    private String userName;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PointData getPoint() {
        return point;
    }

    public void setPoint(PointData point) {
        this.point = point;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
