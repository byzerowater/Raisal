package me.fourground.raisal.data.model;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class RegisterReviewRequest {


    /**
     * useTerm : 앱이용기간
     * appElement : {"contents":"콘텐츠점수(5)","design":"디자인점수(5)","satisfaction":"지속성(5)","useful":"사용성(5)"}
     * comment : 사용소감
     */

    private String useTerm;
    private PointData appElement;
    private String comment;

    public String getUseTerm() {
        return useTerm;
    }

    public void setUseTerm(String useTerm) {
        this.useTerm = useTerm;
    }

    public PointData getAppElement() {
        return appElement;
    }

    public void setAppElement(PointData appElement) {
        this.appElement = appElement;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
