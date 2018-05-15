package com.agoldberg.hercules.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SummarySearchDTO {
    private Long locationId;
    private String locationName;

    private String stringDate;
    private Date date;

    /** Set up the current Date **/
    public SummarySearchDTO() {
        Date currentDate = new Date();
        this.date = currentDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.stringDate = dateFormat.format(date);
    }

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

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
            this.stringDate = stringDate;
        }catch (ParseException e){
            this.stringDate = "Could Not Parse Date";
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
