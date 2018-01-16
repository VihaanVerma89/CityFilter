
package com.cityfilter.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("previous")
    @Expose
    private City previous;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public City getPrevious() {
        return previous;
    }

    public void setPrevious(City previous) {
        this.previous = previous;
    }

}
