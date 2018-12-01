package com.agoldberg.hercules.summary;

import java.util.Date;
import java.util.List;

public class SearchDTO {
    private List<Long> storeIds;
    private Date date;

    public SearchDTO() {
    }

    public SearchDTO(List<Long> storeIds, Date date) {
        this.storeIds = storeIds;
        this.date = date;
    }

    public List<Long> getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(List<Long> storeIds) {
        this.storeIds = storeIds;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
