package com.agoldberg.hercules.builder;

import com.agoldberg.hercules.domain.EnteredRevenueDomain;
import com.agoldberg.hercules.domain.ProcessedRevenueDomain;

public class ProcessedRevenueDomainBuilder {
    private EnteredRevenueDomain enteredRevenue;
    private double actualIntake;
    private double tapeIntake;
    private double overUnder;
    private double taxCount;
    private double valuePerTransaction;
    private double percentageCard;
    private double percentageCash;
    private double percentageCheck;



    public ProcessedRevenueDomainBuilder setEnteredRevenue(EnteredRevenueDomain enteredRevenue) {
        this.enteredRevenue = enteredRevenue;
        return this;
    }

    public ProcessedRevenueDomainBuilder setActualIntake(double actualIntake) {
        this.actualIntake = actualIntake;
        return this;
    }



    public ProcessedRevenueDomainBuilder setTapeIntake(double tapeIntake) {
        this.tapeIntake = tapeIntake;
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


    public ProcessedRevenueDomainBuilder setValuePerTransaction(double valuePerTransaction) {
        this.valuePerTransaction = valuePerTransaction;
        return this;
    }

    public ProcessedRevenueDomainBuilder setPercentageCard(double percentageCard) {
        this.percentageCard = percentageCard;
        return this;
    }

    public ProcessedRevenueDomainBuilder setPercentageCash(double percentageCash) {
        this.percentageCash = percentageCash;
        return this;
    }

    public ProcessedRevenueDomainBuilder setPercentageCheck(double percentageCheck) {
        this.percentageCheck = percentageCheck;
        return this;
    }

    public ProcessedRevenueDomain createProcessedRevenueDomain() {
        return new ProcessedRevenueDomain(enteredRevenue, enteredRevenue.getDate(), enteredRevenue.getLocation(), actualIntake, tapeIntake, overUnder, taxCount, valuePerTransaction, percentageCard, percentageCash, percentageCheck);
    }


}