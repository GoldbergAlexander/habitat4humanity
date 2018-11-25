package com.agoldberg.hercules.departmentrevenue;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;

public class SearchDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;
    private double amount;
    private String storeName;
    @Nullable
    private Long storeId;
    private String departmentName;
    @Nullable
    private Long departmentId;

    public SearchDTO() {
    }

    public SearchDTO(Date start, Date end, double amount, String storeName, Long storeId, String departmentName, Long departmentId) {
        this.start = start;
        this.end = end;
        this.amount = amount;
        this.storeName = storeName;
        this.storeId = storeId;
        this.departmentName = departmentName;
        this.departmentId = departmentId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
