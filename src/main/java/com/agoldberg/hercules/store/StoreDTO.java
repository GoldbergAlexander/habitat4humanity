package com.agoldberg.hercules.store;

import com.agoldberg.hercules.domain.Auditable;

import javax.validation.constraints.NotEmpty;

public class StoreDTO extends Auditable<String> {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String lineOne;
    private String lineTwo;
    @NotEmpty
    private String city, state, zip;
    private boolean enabled;

    public StoreDTO() {
    }

    public StoreDTO(Long id, String name, String lineOne, String lineTwo, String city, String state, String zip, boolean enabled) {
        this.id = id;
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
}
