package com.agoldberg.hercules.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentRevenueDomain that = (DepartmentRevenueDomain) o;
        return Double.compare(that.revenue, revenue) == 0 &&
                Objects.equals(department, that.department) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(department, date, revenue);
    }
}
