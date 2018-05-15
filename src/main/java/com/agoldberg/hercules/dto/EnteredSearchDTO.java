package com.agoldberg.hercules.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

public class EnteredSearchDTO {
    private Long locationId;
    private String locationName;

    private String stringStartingDate;
    private String stringEndingDate;

    private Date startingDate;
    private Date endingDate;


    /** Set up the search object with the starting date = to the first of the current month
     * and the ending date = the current date
     */
    public EnteredSearchDTO() {
        Date currentDate = new Date();
        Date startingDate = new Date();
        startingDate.setYear(currentDate.getYear());
        startingDate.setMonth(currentDate.getMonth());
        startingDate.setDate(1);

        this.startingDate = startingDate;
        this.startingDate.setHours(0);
        this.startingDate.setMinutes(0);
        this.startingDate.setSeconds(0);

        this.endingDate = currentDate;
        this.endingDate.setHours(0);
        this.endingDate.setMinutes(0);
        this.endingDate.setSeconds(0);


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.stringStartingDate = dateFormat.format(startingDate);
        this.stringEndingDate = dateFormat.format(endingDate);
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

    public String getStringStartingDate() {
        return stringStartingDate;
    }

    public void setStringStartingDate(String stringStartingDate) {
        try {
            this.startingDate = new SimpleDateFormat("yyyy-MM-dd").parse(stringStartingDate);
            this.startingDate.setHours(0);
            this.startingDate.setMinutes(0);
            this.startingDate.setSeconds(0);
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
            this.endingDate.setHours(0);
            this.endingDate.setMinutes(0);
            this.endingDate.setSeconds(0);
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
