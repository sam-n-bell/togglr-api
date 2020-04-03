package com.heb.togglr.api.client.model.response;

import java.util.Date;

public class FeatureResponse {

    private int id;
    private String descr;
    private Integer appId;
    private Date toggled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Date getToggled() { return toggled;}

    public void setToggled(Date toggled) { this.toggled = toggled;
    }
}