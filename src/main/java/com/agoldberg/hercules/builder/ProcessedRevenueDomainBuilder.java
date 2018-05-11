package com.agoldberg.hercules.builder;

import com.agoldberg.hercules.domain.EnteredRevenueDomain;
import com.agoldberg.hercules.domain.ProcessedRevenueDomain;

public class ProcessedRevenueDomainBuilder {
    private Long id;
    private EnteredRevenueDomain enteredRevenue;
    private double actualIntake;
    private double actualPreTaxIntake;
    private double actualTaxableIntake;
    private double actualTaxIntake;
    private double tapeIntake;
    private double tapePreTaxIntake;
    private double tapeTaxableIntake;
    private double overUnder;
    private double taxCount;

    public ProcessedRevenueDomainBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ProcessedRevenueDomainBuilder setEnteredRevenue(EnteredRevenueDomain enteredRevenue) {
        this.enteredRevenue = enteredRevenue;
        return this;
    }

    public ProcessedRevenueDomainBuilder setActualIntake(double actualIntake) {
        this.actualIntake = actualIntake;
        return this;
    }

    public ProcessedRevenueDomainBuilder setActualPreTaxIntake(double actualPreTaxIntake) {
        this.actualPreTaxIntake = actualPreTaxIntake;
        return this;
    }

    public ProcessedRevenueDomainBuilder setActualTaxableIntake(double actualTaxableIntake) {
        this.actualTaxableIntake = actualTaxableIntake;
        return this;
    }

    public ProcessedRevenueDomainBuilder setActualTaxIntake(double actualTaxIntake) {
        this.actualTaxIntake = actualTaxIntake;
        return this;
    }

    public ProcessedRevenueDomainBuilder setTapeIntake(double tapeIntake) {
        this.tapeIntake = tapeIntake;
        return this;
    }

    public ProcessedRevenueDomainBuilder setTapePreTaxIntake(double tapePreTaxIntake) {
        this.tapePreTaxIntake = tapePreTaxIntake;
        return this;
    }

    public ProcessedRevenueDomainBuilder setTapeTaxableIntake(double tapeTaxableIntake) {
        this.tapeTaxableIntake = tapeTaxableIntake;
        return this;
    }

    public ProcessedRevenueDomainBuilder setOverUnder(double overUnder) {
        this.overUnder = overUnder;
        return this;
    }

    public ProcessedRevenueDomainBuilder setTaxCount(double taxCount) {
        this.taxCount = taxCount;
        return this;
    }

    public ProcessedRevenueDomain createProcessedRevenueDomain() {
        return new ProcessedRevenueDomain(id, enteredRevenue, actualIntake, actualPreTaxIntake, actualTaxableIntake, actualTaxIntake, tapeIntake, tapePreTaxIntake, tapeTaxableIntake, overUnder, taxCount);
    }
}