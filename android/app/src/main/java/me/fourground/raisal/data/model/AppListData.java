package me.fourground.raisal.data.model;

import java.util.List;

/**
 * Created by YoungSoo Kim on 2017-04-01.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class AppListData {

    /**
     * meta : {"total-pages":10}
     * data : []
     * links : {"self":"/api/raisal/select?page=1&size=10","first":null,"prev":null,"next":"/api/raisal/select?page=2&size=10","last":"/api/raisal/select?page=10&size=10"}
     */

    private MetaData meta;
    private LinkData links;
    private List<AppInfoData> data;

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

    public List<AppInfoData> getData() {
        return data;
    }

    public void setData(List<AppInfoData> data) {
        this.data = data;
    }

}
