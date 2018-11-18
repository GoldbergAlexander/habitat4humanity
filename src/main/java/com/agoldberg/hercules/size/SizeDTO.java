package com.agoldberg.hercules.size;

import com.agoldberg.hercules.domain.Auditable;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SizeDTO extends Auditable<String> {
    private Long id;
    private Long storeId;
    private String storeName;
    private Long departmentId;
    private String departmentName;
    private double rate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

    public SizeDTO() {
    }

    public SizeDTO(Long id, Long storeId, String storeName, Long departmentId, String departmentName, double rate, Date start, Date end) {
        this.id = id;
        this.storeId = storeId;
        this.storeName = storeName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.rate = rate;
        this.start = start;
        this.end = end;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String location_name) {
        this.storeName = location_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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
