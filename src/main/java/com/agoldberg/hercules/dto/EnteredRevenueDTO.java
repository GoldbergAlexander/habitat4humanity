package com.agoldberg.hercules.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnteredRevenueDTO {

    private Long id;
    /** This is a bit of an issues -- @DateTimeFormat didn't work with time zones, etc **/
    @NotEmpty
    private String stringDate;
    @NotNull
    private Date date;
    @NotNull
    private Long locationId;
    private String locationName;
    @Min(0)
    private long transactionCount;
    @Min(0)
    private double cashCount;
    @Min(0)
    private double checkCount;
    @Min(0)
    private double cardUnit;
    @Min(0)
    private double payoutReceipt;
    @Min(0)
    private double cashTape;
    @Min(0)
    private double checkTape;
    @Min(0)
    private double cardTape;
    @Min(0)
    private double taxTape;
    @Min(0)
    private double vehicleSale;
    @Min(0)
    private double salesVoid;
    @Min(0)
    private double taxVoid;
    private String memo;

    public EnteredRevenueDTO() {
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
            this.stringDate = stringDate;
        }catch (ParseException e){
            this.stringDate = "Could Not Parse Date: " + stringDate;
        }

    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public long getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(long transactionCount) {
        this.transactionCount = transactionCount;
    }

    public double getCashCount() {
        return cashCount;
    }

    public void setCashCount(double cashCount) {
        this.cashCount = cashCount;
    }

    public double getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(double checkCount) {
        this.checkCount = checkCount;
    }

    public double getCardUnit() {
        return cardUnit;
    }

    public void setCardUnit(double cardUnit) {
        this.cardUnit = cardUnit;
    }

    public double getPayoutReceipt() {
        return payoutReceipt;
    }

    public void setPayoutReceipt(double payoutReceipt) {
        this.payoutReceipt = payoutReceipt;
    }

    public double getCashTape() {
        return cashTape;
    }

    public void setCashTape(double cashTape) {
        this.cashTape = cashTape;
    }

    public double getCheckTape() {
        return checkTape;
    }

    public void setCheckTape(double checkTape) {
        this.checkTape = checkTape;
    }

    public double getCardTape() {
        return cardTape;
    }

    public void setCardTape(double cardTape) {
        this.cardTape = cardTape;
    }

    public double getTaxTape() {
        return taxTape;
    }

    public void setTaxTape(double taxTape) {
        this.taxTape = taxTape;
    }

    public double getVehicleSale() {
        return vehicleSale;
    }

    public void setVehicleSale(double vehicleSale) {
        this.vehicleSale = vehicleSale;
    }

    public double getSalesVoid() {
        return salesVoid;
    }

    public void setSalesVoid(double salesVoid) {
        this.salesVoid = salesVoid;
    }

    public double getTaxVoid() {
        return taxVoid;
    }

    public void setTaxVoid(double taxVoid) {
        this.taxVoid = taxVoid;
    }
}
