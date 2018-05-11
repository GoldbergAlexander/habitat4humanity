package com.agoldberg.hercules.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DepartmentRevenueDTO {

    private Long id;
    private Long departmentId;
    private String departmentName;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date date;
    private double revenue;

    public DepartmentRevenueDTO() {
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
