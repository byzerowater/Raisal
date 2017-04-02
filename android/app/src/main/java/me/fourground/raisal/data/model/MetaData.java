package me.fourground.raisal.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YoungSoo Kim on 2017-04-01.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MetaData {
    /**
     * total-pages : 10
     */

    @SerializedName("total-pages")
    private int totalpages;

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }
}
