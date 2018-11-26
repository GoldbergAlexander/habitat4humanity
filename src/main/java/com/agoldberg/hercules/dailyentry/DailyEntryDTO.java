package com.agoldberg.hercules.dailyentry;

import com.agoldberg.hercules.domain.Auditable;
import com.agoldberg.hercules.store.StoreDomain;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

//@Table(
//        uniqueConstraints = @UniqueConstraint(columnNames = {"location_id", "date"})
//)

public class DailyEntryDTO extends Auditable<String> implements Serializable{
    private Long id;
    private String storeName;
    private Long storeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    private boolean calculated;
    private String memo;

    public DailyEntryDTO() {
    }

    public DailyEntryDTO(Long id, String storeName, Long storeId, Date date, boolean processed, long transactionCount, double cashCount, double checkCount, double cardUnit, double payoutReceipt, double cashTape, double checkTape, double cardTape, double taxTape, boolean calculated, String memo) {
        this.id = id;
        this.storeName = storeName;
        this.storeId = storeId;
        this.date = date;
        this.processed = processed;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
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


}
