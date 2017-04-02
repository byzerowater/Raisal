package me.fourground.raisal.data.model;

/**
 * Created by YoungSoo Kim on 2017-04-02.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class PointViewData {
    private String title;
    private String explanation;
    private float point;

    public PointViewData(String title, String explanation) {
        this.title = title;
        this.explanation = explanation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }
}
