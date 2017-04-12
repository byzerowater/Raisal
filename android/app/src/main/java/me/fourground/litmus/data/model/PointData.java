package me.fourground.litmus.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class PointData implements Parcelable {
    /**
     * contents : 4.8
     * design : 4.8
     * satisfaction : 4.8
     * useful : 4.8
     */

    private float contents;
    private float design;
    private float satisfaction;
    private float useful;

    public float getContents() {
        return contents;
    }

    public void setContents(float contents) {
        this.contents = contents;
    }

    public float getDesign() {
        return design;
    }

    public void setDesign(float design) {
        this.design = design;
    }

    public float getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(float satisfaction) {
        this.satisfaction = satisfaction;
    }

    public float getUseful() {
        return useful;
    }

    public void setUseful(float useful) {
        this.useful = useful;
    }


    @Override
    public String toString() {
        return "PointData{" +
                "contents=" + contents +
                ", design=" + design +
                ", satisfaction=" + satisfaction +
                ", useful=" + useful +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.contents);
        dest.writeFloat(this.design);
        dest.writeFloat(this.satisfaction);
        dest.writeFloat(this.useful);
    }

    public PointData() {
    }

    protected PointData(Parcel in) {
        this.contents = in.readFloat();
        this.design = in.readFloat();
        this.satisfaction = in.readFloat();
        this.useful = in.readFloat();
    }

    public static final Creator<PointData> CREATOR = new Creator<PointData>() {
        @Override
        public PointData createFromParcel(Parcel source) {
            return new PointData(source);
        }

        @Override
        public PointData[] newArray(int size) {
            return new PointData[size];
        }
    };
}
