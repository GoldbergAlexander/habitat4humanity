package com.agoldberg.hercules.store;

import com.agoldberg.hercules.domain.Auditable;
import com.agoldberg.hercules.goal.GoalDomain;
import com.agoldberg.hercules.tax.TaxDomain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class StoreDomain extends Auditable<String> implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lineOne, lineTwo;
    private String city, state, zip;
    private boolean enabled;

    public StoreDomain() {
    }

    public StoreDomain(String name, String lineOne, String lineTwo, String city, String state, String zip, boolean enabled) {
        this.name = name;
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreDomain that = (StoreDomain) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(lineOne, that.lineOne) &&
                Objects.equals(lineTwo, that.lineTwo) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(zip, that.zip);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, lineOne, lineTwo, city, state, zip);
    }
}
