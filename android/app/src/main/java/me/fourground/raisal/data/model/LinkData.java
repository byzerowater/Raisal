package me.fourground.raisal.data.model;

/**
 * Created by YoungSoo Kim on 2017-04-01.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class LinkData {


    /**
     * self : /api/raisal/select?page=1&size=10
     * first : null
     * prev : null
     * next : /api/raisal/select?page=2&size=10
     * last : /api/raisal/select?page=10&size=10
     */

    private String self;
    private Object first;
    private Object prev;
    private String next;
    private String last;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public Object getFirst() {
        return first;
    }

    public void setFirst(Object first) {
        this.first = first;
    }

    public Object getPrev() {
        return prev;
    }

    public void setPrev(Object prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
