package com.agoldberg.hercules.domain;

import org.apache.commons.math3.util.Precision;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class ProcessedRevenueDomain extends Auditable<String>{
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "entered_id"
    )
    private EnteredRevenueDomain enteredRevenue;

    @ManyToOne
    @JoinColumn(name="location_id")
    private StoreLocationDomain locationDomain;
    private Date date;
    private double actualIntake;
    private double actualPreTaxIntake;
    private double actualTaxableIntake;
    private double tapeIntake;
    private double tapePreTaxIntake;
    private double tapeTaxableIntake;
    private double overUnder;
    private double taxCount;


    public ProcessedRevenueDomain() {
    }

    public ProcessedRevenueDomain(EnteredRevenueDomain enteredRevenue, Date date, StoreLocationDomain location, double actualIntake, double actualPreTaxIntake, double actualTaxableIntake, double tapeIntake, double tapePreTaxIntake, double tapeTaxableIntake, double overUnder, double taxCount) {
        this.locationDomain = location;
        this.date = date;
        this.enteredRevenue = enteredRevenue;
        this.actualIntake = Precision.round(actualIntake,2);
        this.actualPreTaxIntake = Precision.round(actualPreTaxIntake,2);
        this.actualTaxableIntake = Precision.round(actualTaxableIntake,2);
        this.tapeIntake = Precision.round(tapeIntake,2);
        this.tapePreTaxIntake = Precision.round(tapePreTaxIntake,2);
        this.tapeTaxableIntake = Precision.round(tapeTaxableIntake,2);
        this.overUnder = Precision.round(overUnder,2);
        this.taxCount = Precision.round(taxCount,2);
    }

    public StoreLocationDomain getLocationDomain() {
        return locationDomain;
    }

    public void setLocationDomain(StoreLocationDomain locationDomain) {
        this.locationDomain = locationDomain;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnteredRevenueDomain getEnteredRevenue() {
        return enteredRevenue;
    }

    public void setEnteredRevenue(EnteredRevenueDomain enteredRevenue) {
        this.enteredRevenue = enteredRevenue;
    }

    public double getActualIntake() {
        return actualIntake;
    }

    public void setActualIntake(double actualIntake) {
        this.actualIntake = actualIntake;
    }

    public double getActualPreTaxIntake() {
        return actualPreTaxIntake;
    }

    public void setActualPreTaxIntake(double actualPreTaxIntake) {
        this.actualPreTaxIntake = actualPreTaxIntake;
    }

    public double getActualTaxableIntake() {
        return actualTaxableIntake;
    }

    public void setActualTaxableIntake(double actualTaxableIntake) {
        this.actualTaxableIntake = actualTaxableIntake;
    }


    public double getTapeIntake() {
        return tapeIntake;
    }

    public void setTapeIntake(double tapeIntake) {
        this.tapeIntake = tapeIntake;
    }

    public double getTapePreTaxIntake() {
        return tapePreTaxIntake;
    }

    public void setTapePreTaxIntake(double tapePreTaxIntake) {
        this.tapePreTaxIntake = tapePreTaxIntake;
    }

    public double getTapeTaxableIntake() {
        return tapeTaxableIntake;
    }

    public void setTapeTaxableIntake(double tapeTaxableIntake) {
        this.tapeTaxableIntake = tapeTaxableIntake;
    }

    public double getTaxCount() {
        return taxCount;
    }

    public void setTaxCount(double taxCount) {
        this.taxCount = taxCount;
    }

    public double getOverUnder() {
        return overUnder;
    }

    public void setOverUnder(double overUnder) {
        this.overUnder = overUnder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessedRevenueDomain that = (ProcessedRevenueDomain) o;
        return Objects.equals(locationDomain, that.locationDomain) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(locationDomain, date);
    }
}
