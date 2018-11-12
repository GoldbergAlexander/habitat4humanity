package com.agoldberg.hercules.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ProcessedRevenueDTO {

    private String enteredRevenueLocationName;
    @DateTimeFormat(pattern = "YYYY-m-d")
    private Date enteredRevenueDate;
    private long enteredRevenueTransactionCount;
    private double enteredRevenueCashCount;
    private double enteredRevenueCheckCount;
    private double enteredRevenueCardUnit;
    private double enteredRevenuePayoutReceipt;
    private double enteredRevenueCashTape;
    private double enteredRevenueCheckTape;
    private double enteredRevenueCardTape;
    private double enteredRevenueTaxTape;
    private double enteredRevenueVehicleSale;
    private double enteredRevenueSalesVoid;
    private double enteredRevenueTaxVoid;
    private String enteredRevenueMemo;
    private double actualIntake;
    private double tapeIntake;
    private double overUnder;
    private double taxCount;


    public String getEnteredRevenueLocationName() {
        return enteredRevenueLocationName;
    }

    public void setEnteredRevenueLocationName(String enteredRevenueLocationName) {
        this.enteredRevenueLocationName = enteredRevenueLocationName;
    }

    public Date getEnteredRevenueDate() {
        return enteredRevenueDate;
    }

    public void setEnteredRevenueDate(Date enteredRevenueDate) {
        this.enteredRevenueDate = enteredRevenueDate;
    }

    public long getEnteredRevenueTransactionCount() {
        return enteredRevenueTransactionCount;
    }

    public void setEnteredRevenueTransactionCount(long enteredRevenueTransactionCount) {
        this.enteredRevenueTransactionCount = enteredRevenueTransactionCount;
    }

    public double getEnteredRevenueCashCount() {
        return enteredRevenueCashCount;
    }

    public void setEnteredRevenueCashCount(double enteredRevenueCashCount) {
        this.enteredRevenueCashCount = enteredRevenueCashCount;
    }

    public double getEnteredRevenueCheckCount() {
        return enteredRevenueCheckCount;
    }

    public void setEnteredRevenueCheckCount(double enteredRevenueCheckCount) {
        this.enteredRevenueCheckCount = enteredRevenueCheckCount;
    }

    public double getEnteredRevenueCardUnit() {
        return enteredRevenueCardUnit;
    }

    public void setEnteredRevenueCardUnit(double enteredRevenueCardUnit) {
        this.enteredRevenueCardUnit = enteredRevenueCardUnit;
    }

    public double getEnteredRevenuePayoutReceipt() {
        return enteredRevenuePayoutReceipt;
    }

    public void setEnteredRevenuePayoutReceipt(double enteredRevenuePayoutReceipt) {
        this.enteredRevenuePayoutReceipt = enteredRevenuePayoutReceipt;
    }

    public double getEnteredRevenueCashTape() {
        return enteredRevenueCashTape;
    }

    public void setEnteredRevenueCashTape(double enteredRevenueCashTape) {
        this.enteredRevenueCashTape = enteredRevenueCashTape;
    }

    public double getEnteredRevenueCheckTape() {
        return enteredRevenueCheckTape;
    }

    public void setEnteredRevenueCheckTape(double enteredRevenueCheckTape) {
        this.enteredRevenueCheckTape = enteredRevenueCheckTape;
    }

    public double getEnteredRevenueCardTape() {
        return enteredRevenueCardTape;
    }

    public void setEnteredRevenueCardTape(double enteredRevenueCardTape) {
        this.enteredRevenueCardTape = enteredRevenueCardTape;
    }

    public double getEnteredRevenueTaxTape() {
        return enteredRevenueTaxTape;
    }

    public void setEnteredRevenueTaxTape(double enteredRevenueTaxTape) {
        this.enteredRevenueTaxTape = enteredRevenueTaxTape;
    }

    public double getEnteredRevenueVehicleSale() {
        return enteredRevenueVehicleSale;
    }

    public void setEnteredRevenueVehicleSale(double enteredRevenueVehicleSale) {
        this.enteredRevenueVehicleSale = enteredRevenueVehicleSale;
    }

    public double getEnteredRevenueSalesVoid() {
        return enteredRevenueSalesVoid;
    }

    public void setEnteredRevenueSalesVoid(double enteredRevenueSalesVoid) {
        this.enteredRevenueSalesVoid = enteredRevenueSalesVoid;
    }

    public double getEnteredRevenueTaxVoid() {
        return enteredRevenueTaxVoid;
    }

    public void setEnteredRevenueTaxVoid(double enteredRevenueTaxVoid) {
        this.enteredRevenueTaxVoid = enteredRevenueTaxVoid;
    }

    public String getEnteredRevenueMemo() {
        return enteredRevenueMemo;
    }

    public void setEnteredRevenueMemo(String enteredRevenueMemo) {
        this.enteredRevenueMemo = enteredRevenueMemo;
    }

    public double getActualIntake() {
        return actualIntake;
    }

    public void setActualIntake(double actualIntake) {
        this.actualIntake = actualIntake;
    }


    public double getTapeIntake() {
        return tapeIntake;
    }

    public void setTapeIntake(double tapeIntake) {
        this.tapeIntake = tapeIntake;
    }


    public double getOverUnder() {
        return overUnder;
    }

    public void setOverUnder(double overUnder) {
        this.overUnder = overUnder;
    }

    public double getTaxCount() {
        return taxCount;
    }

    public void setTaxCount(double taxCount) {
        this.taxCount = taxCount;
    }
}
