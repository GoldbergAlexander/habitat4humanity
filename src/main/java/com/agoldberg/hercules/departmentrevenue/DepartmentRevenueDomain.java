package com.agoldberg.hercules.departmentrevenue;

import com.agoldberg.hercules.department.DepartmentDomain;
import com.agoldberg.hercules.domain.Auditable;
import com.agoldberg.hercules.store.StoreDomain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class DepartmentRevenueDomain extends Auditable<String> {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private StoreDomain store;

    @ManyToOne
    private DepartmentDomain department;

    private Date date;
    private double amount;

    public DepartmentRevenueDomain() {
    }

    public DepartmentRevenueDomain(StoreDomain store, DepartmentDomain department, Date date, double amount) {
        this.store = store;
        this.department = department;
        this.date = date;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoreDomain getStore() {
        return store;
    }

    public void setStore(StoreDomain store) {
        this.store = store;
    }

    public DepartmentDomain getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDomain department) {
        this.department = department;
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
}
