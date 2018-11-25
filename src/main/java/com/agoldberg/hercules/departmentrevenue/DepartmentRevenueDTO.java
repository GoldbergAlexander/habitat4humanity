package com.agoldberg.hercules.departmentrevenue;

import com.agoldberg.hercules.domain.Auditable;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DepartmentRevenueDTO extends Auditable<String> {
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private double amount;
    private String storeName;
    private Long storeId;
    private String departmentName;
    private Long departmentId;


    public DepartmentRevenueDTO() {
    }

    public DepartmentRevenueDTO(Long id, Date date, double amount, String storeName, Long storeId, String departmentName, Long departmentId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.storeName = storeName;
        this.storeId = storeId;
        this.departmentName = departmentName;
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
