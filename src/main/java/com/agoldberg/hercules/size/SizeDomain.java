package com.agoldberg.hercules.size;

import com.agoldberg.hercules.department.DepartmentDomain;
import com.agoldberg.hercules.domain.Auditable;
import com.agoldberg.hercules.store.StoreDomain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class SizeDomain extends Auditable<String> {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private StoreDomain store;
    @ManyToOne(fetch = FetchType.EAGER)
    private DepartmentDomain department;
    private double size;
    private Date start;
    private Date end;

    public SizeDomain() {
    }

    public SizeDomain(StoreDomain store, DepartmentDomain department, double size, Date start, Date end) {
        this.store = store;
        this.department = department;
        this.size = size;
        this.start = start;
        this.end = end;
    }

    public DepartmentDomain getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDomain department) {
        this.department = department;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SizeDomain that = (SizeDomain) o;
        return Double.compare(that.getSize(), getSize()) == 0 &&
                Objects.equals(getStore(), that.getStore()) &&
                Objects.equals(getDepartment(), that.getDepartment()) &&
                Objects.equals(getStart(), that.getStart()) &&
                Objects.equals(getEnd(), that.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStore(), getDepartment(), getSize(), getStart(), getEnd());
    }
}
