package com.agoldberg.hercules.dailyentry;

import com.agoldberg.hercules.domain.Auditable;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SearchDTO {
    private String storeName;
    private Long storeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;
    private LOD lod;

    public SearchDTO() {
    }

    public SearchDTO(String storeName, Long storeId, Date start, Date end) {
        this.storeName = storeName;
        this.storeId = storeId;
        this.start = start;
        this.end = end;
    }

    public SearchDTO(String storeName, Long storeId, Date start, Date end, LOD lod) {
        this.storeName = storeName;
        this.storeId = storeId;
        this.start = start;
        this.end = end;
        this.lod = lod;
    }

    public LOD getLod() {
        return lod;
    }

    public void setLod(LOD lod) {
        this.lod = lod;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
