package com.agoldberg.hercules.dto;

import java.util.Date;

public class DepartmentSizeDTO {

    private Long id;
    private Long departmentId;
    private Long departmentName;
    private Date start;
    private Date end;

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

    public Long getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(Long departmentName) {
        this.departmentName = departmentName;
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
