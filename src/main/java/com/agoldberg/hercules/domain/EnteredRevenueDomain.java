package com.agoldberg.hercules.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"location_id", "date"})
)
@Entity
public class EnteredRevenueDomain extends Auditable<String> implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private StoreLocationDomain location;
    @Column(name = "date")
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
    private double vehicleSale;
    private double salesVoid;
    private double taxVoid;
    private String memo;

    public EnteredRevenueDomain() {
    }

    public EnteredRevenueDomain(StoreLocationDomain location, Date date, boolean processed, long transactionCount, double cashCount, double checkCount, double cardUnit, double payoutReceipt, double cashTape, double checkTape, double cardTape, double taxTape, double vehicleSale, double salesVoid, double taxVoid, String memo) {
        this.date = date;
        this.location = location;
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
        this.vehicleSale = vehicleSale;
        this.salesVoid = salesVoid;
        this.taxVoid = taxVoid;
        this.memo = memo;
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

    public StoreLocationDomain getLocation() {
        return location;
    }

    public void setLocation(StoreLocationDomain location) {
        this.location = location;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnteredRevenueDomain domain = (EnteredRevenueDomain) o;
        return Objects.equals(location, domain.location) &&
                Objects.equals(date, domain.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(location, date);
    }
}
