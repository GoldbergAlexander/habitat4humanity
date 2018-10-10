package com.agoldberg.hercules.dto;

import java.util.ArrayList;
import java.util.List;

public class EnteredRevenueBatchDTO {
    private Long locationId;
    private String locationName;

    private String data;
    private List<EnteredRevenueDTO> enteredRevenueDTOList = new ArrayList<>();

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<EnteredRevenueDTO> getEnteredRevenueDTOList() {
        return enteredRevenueDTOList;
    }

    public void setEnteredRevenueDTOList(List<EnteredRevenueDTO> enteredRevenueDTOList) {
        this.enteredRevenueDTOList = enteredRevenueDTOList;
    }
}
