package com.oe.objects.params;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GetKeyLineParam {

    private String instId;
    private Date after;
    private Date before;
    private String bar;
    private Integer limit;


    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("instId", instId);
        if (after != null) {
            params.put("after", after.getTime() + "");
        }
        if (before != null) {
            params.put("before", before.getTime() + "");
        }
        if (bar != null) {
            params.put("bar", bar);
        }
        if (limit != null) {
            params.put("limit", Integer.toString(limit));
        }
        return params;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public Date getAfter() {
        return after;
    }

    public void setAfter(Date after) {
        this.after = after;
    }

    public Date getBefore() {
        return before;
    }

    public void setBefore(Date before) {
        this.before = before;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
