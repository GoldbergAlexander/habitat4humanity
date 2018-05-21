package com.agoldberg.hercules.dto;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


public class DepartmentDTO {

    private Long id;
    @NotEmpty
    private String name;
    private Long locationId;
    private String locationName;
    @Min(0)
    private double size;
    private boolean enabled;


    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
