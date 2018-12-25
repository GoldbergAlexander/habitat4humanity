package com.agoldberg.hercules.size;

import com.agoldberg.hercules.domain.Auditable;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SizeDTO extends Auditable<String> {
    private Long id;
    private Long storeId;
    @JsonProperty("storeName")
    private String storeName;
    private Long departmentId;
    @JsonProperty("departmentName")
    private String departmentName;
    private double size;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

    public SizeDTO() {
    }

    public SizeDTO(Long id, Long storeId, String storeName, Long departmentId, String departmentName, double size, Date start, Date end) {
        this.id = id;
        this.storeId = storeId;
        this.storeName = storeName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.size = size;
        this.start = start;
        this.end = end;
    }

    @JsonProperty("name")
    public String getName(){
        return storeName + " | " + departmentName;
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
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
