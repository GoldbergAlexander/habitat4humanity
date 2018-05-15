package com.agoldberg.hercules.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnteredSearchDTO {
    private Long locationId;
    private String locationName;

    private String stringStartingDate;
    private String stringEndingDate;

    private Date startingDate;
    private Date endingDate;

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

    public String getStringStartingDate() {
        return stringStartingDate;
    }

    public void setStringStartingDate(String stringStartingDate) {
        try {
            this.startingDate = new SimpleDateFormat("yyyy-MM-dd").parse(stringStartingDate);
            this.stringStartingDate = stringStartingDate;
        }catch (ParseException e){
            this.stringStartingDate = "Could Not Parse Date";
        }
    }

    public String getStringEndingDate() {
        return stringEndingDate;
    }

    public void setStringEndingDate(String stringEndingDate) {
        try {
            this.endingDate = new SimpleDateFormat("yyyy-MM-dd").parse(stringEndingDate);
            this.stringEndingDate = stringEndingDate;
        }catch (ParseException e){
            this.stringEndingDate = "Could Not Parse Date " + stringEndingDate;
        }
    }
    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }
}
