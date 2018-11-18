package com.agoldberg.hercules.builder;

import com.agoldberg.hercules.store.StoreDomain;
import com.agoldberg.hercules.domain.EnteredRevenueDomain;

import java.util.Date;

public class EnteredRevenueDomainBuilder {
    private StoreDomain location;
    private Date date;
    private boolean processed;
    private long transactionCount;
    private double cashCount;
    private double checkCount;
    private double cardUnit;
    private double payoutReceipt;
    private double cashTape;
    private double checkTape;
    private double cardTape;
    private double taxTape;
    private String memo;

    public EnteredRevenueDomainBuilder setLocation(StoreDomain location) {
        this.location = location;
        return this;
    }

    public EnteredRevenueDomainBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public EnteredRevenueDomainBuilder setProcessed(boolean processed) {
        this.processed = processed;
        return this;
    }

    public EnteredRevenueDomainBuilder setTransactionCount(long transactionCount) {
        this.transactionCount = transactionCount;
        return this;
    }

    public EnteredRevenueDomainBuilder setCashCount(double cashCount) {
        this.cashCount = cashCount;
        return this;
    }

    public EnteredRevenueDomainBuilder setCheckCount(double checkCount) {
        this.checkCount = checkCount;
        return this;
    }

    public EnteredRevenueDomainBuilder setCardUnit(double cardUnit) {
        this.cardUnit = cardUnit;
        return this;
    }

    public EnteredRevenueDomainBuilder setPayoutReceipt(double payoutReceipt) {
        this.payoutReceipt = payoutReceipt;
        return this;
    }

    public EnteredRevenueDomainBuilder setCashTape(double cashTape) {
        this.cashTape = cashTape;
        return this;
    }

    public EnteredRevenueDomainBuilder setCheckTape(double checkTape) {
        this.checkTape = checkTape;
        return this;
    }

    public EnteredRevenueDomainBuilder setCardTape(double cardTape) {
        this.cardTape = cardTape;
        return this;
    }

    public EnteredRevenueDomainBuilder setTaxTape(double taxTape) {
        this.taxTape = taxTape;
        return this;
    }


    public EnteredRevenueDomainBuilder setMemo(String memo){
        this.memo = memo;
        return this;
    }

    public EnteredRevenueDomain createEnteredRevenueDomain() {
        return new EnteredRevenueDomain(location, date, processed, transactionCount, cashCount, checkCount, cardUnit, payoutReceipt, cashTape, checkTape, cardTape, taxTape, memo);
    }
}