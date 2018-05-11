package com.agoldberg.hercules.dto;

import com.agoldberg.hercules.domain.StoreLocationDomain;

public class DepartmentDTO {

    private String name;
    private Long locationId;
    private double size;
    private boolean enabled;

    public DepartmentDTO() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
