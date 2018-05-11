package com.agoldberg.hercules.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class DepartmentRevenueDomain extends Auditable<String>{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentDomain department;
    private LocalDate date;
    private double revenue;

    public DepartmentRevenueDomain() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DepartmentDomain getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDomain department) {
        this.department = department;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
