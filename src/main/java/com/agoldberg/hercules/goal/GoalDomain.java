package com.agoldberg.hercules.goal;

import com.agoldberg.hercules.domain.Auditable;
import com.agoldberg.hercules.store.Store;

import javax.persistence.*;
import java.util.Date;

@Entity
public class GoalDomain extends Auditable<String> {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Store store;
    private double rate;
    private Date start;
    private Date end;

    public GoalDomain() {
    }

    public GoalDomain(Store store, double rate, Date start, Date end) {
        this.store = store;
        this.rate = rate;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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
