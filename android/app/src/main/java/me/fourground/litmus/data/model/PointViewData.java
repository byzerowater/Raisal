package me.fourground.litmus.data.model;

/**
 * Created by YoungSoo Kim on 2017-04-02.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class PointViewData {
    private String title;
    private float point;

    public PointViewData(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }
}
