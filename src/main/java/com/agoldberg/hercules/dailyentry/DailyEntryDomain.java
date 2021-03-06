package com.agoldberg.hercules.dailyentry;

import com.agoldberg.hercules.domain.Auditable;
import com.agoldberg.hercules.store.StoreDomain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

//@Table(
//        uniqueConstraints = @UniqueConstraint(columnNames = {"store_id", "date"})
//)
@Entity
public class DailyEntryDomain extends Auditable<String> implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreDomain store;
    private Date date;
    private long transactionCount;
    private double cashCount;
    private double checkCount;
    private double cardUnit;
    private double payoutReceipt;
    private double cashTape;
    private double checkTape;
    private double cardTape;
    private double taxTape;
    private boolean calculated;
    private String memo;

    public DailyEntryDomain() {
    }

    public DailyEntryDomain(StoreDomain store, Date date, boolean processed, long transactionCount, double cashCount, double checkCount, double cardUnit, double payoutReceipt, double cashTape, double checkTape, double cardTape, double taxTape, boolean calculated, String memo) {
        this.store = store;
        this.date = date;
        this.transactionCount = transactionCount;
        this.cashCount = cashCount;
        this.checkCount = checkCount;
        this.cardUnit = cardUnit;
        this.payoutReceipt = payoutReceipt;
        this.cashTape = cashTape;
        this.checkTape = checkTape;
        this.cardTape = cardTape;
        this.taxTape = taxTape;
        this.calculated = calculated;
        this.memo = memo;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public void setCalculated(boolean calculated) {
        this.calculated = calculated;
    }

    public Long getId() {
        return id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoreDomain getStore() {
        return store;
    }

    public void setStore(StoreDomain store) {
        this.store = store;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyEntryDomain domain = (DailyEntryDomain) o;
        return Objects.equals(store, domain.store) &&
                Objects.equals(date, domain.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(store, date);
    }
}
