package com.agoldberg.hercules.builder;

import com.agoldberg.hercules.store.Store;

public class StoreLocationDomainBuilder {
    private String name;
    private String lineOne;
    private String lineTwo;
    private String city;
    private String state;
    private String zipcode;
    private boolean enabled;

    public StoreLocationDomainBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StoreLocationDomainBuilder setLineOne(String lineOne) {
        this.lineOne = lineOne;
        return this;
    }

    public StoreLocationDomainBuilder setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
        return this;
    }

    public StoreLocationDomainBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public StoreLocationDomainBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public StoreLocationDomainBuilder setZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public StoreLocationDomainBuilder setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Store createStoreLocationDomain() {
        return new Store(name, lineOne, lineTwo, city, state, zipcode, enabled);
    }
}