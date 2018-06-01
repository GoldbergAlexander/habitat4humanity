package com.agoldberg.hercules.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DepartmentSizeDTO {

    private Long id;
    private Long departmentId;
    private String departmentName;
    private Date start;
    private String stringStart;
    private double size;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getStringStart() {
        return stringStart;
    }

    public void setStringStart(String stringStart) {
        try {
            this.start = new SimpleDateFormat("yyyy-MM-dd").parse(stringStart);
            this.stringStart = stringStart;
        }catch (ParseException e){
            this.stringStart = "Could Not Parse Date";
        }
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

}
