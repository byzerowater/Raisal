package me.fourground.litmus.data.model;

import java.util.List;

/**
 * Created by YoungSoo Kim on 2017-04-01.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class ReviewListData {

    /**
     * meta : {"total-pages":10}
     * data : []
     * links : {"self":"/api/raisal/select?page=1&size=10","first":null,"prev":null,"next":"/api/raisal/select?page=2&size=10","last":"/api/raisal/select?page=10&size=10"}
     */

    private MetaData meta;
    private LinkData links;
    private List<ReviewData> data;

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }

    public LinkData getLinks() {
        return links;
    }

    public void setLinks(LinkData links) {
        this.links = links;
    }

    public List<ReviewData> getData() {
        return data;
    }

    public void setData(List<ReviewData> data) {
        this.data = data;
    }

}
