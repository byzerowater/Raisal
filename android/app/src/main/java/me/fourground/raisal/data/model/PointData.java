package me.fourground.raisal.data.model;

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

    private double contents;
    private double design;
    private double satisfaction;
    private double useful;

    public double getContents() {
        return contents;
    }

    public void setContents(double contents) {
        this.contents = contents;
    }

    public double getDesign() {
        return design;
    }

    public void setDesign(double design) {
        this.design = design;
    }

    public double getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(double satisfaction) {
        this.satisfaction = satisfaction;
    }

    public double getUseful() {
        return useful;
    }

    public void setUseful(double useful) {
        this.useful = useful;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.contents);
        dest.writeDouble(this.design);
        dest.writeDouble(this.satisfaction);
        dest.writeDouble(this.useful);
    }

    public PointData() {
    }

    protected PointData(Parcel in) {
        this.contents = in.readDouble();
        this.design = in.readDouble();
        this.satisfaction = in.readDouble();
        this.useful = in.readDouble();
    }

    public static final Parcelable.Creator<PointData> CREATOR = new Parcelable.Creator<PointData>() {
        @Override
        public PointData createFromParcel(Parcel source) {
            return new PointData(source);
        }

        @Override
        public PointData[] newArray(int size) {
            return new PointData[size];
        }
    };

    @Override
    public String toString() {
        return "PointData{" +
                "contents=" + contents +
                ", design=" + design +
                ", satisfaction=" + satisfaction +
                ", useful=" + useful +
                '}';
    }
}
