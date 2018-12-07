package com.agoldberg.hercules.dailyentry;

import com.agoldberg.hercules.goal.GoalDTO;
import com.agoldberg.hercules.tax.TaxDTO;
import org.apache.commons.math3.util.Precision;

import java.util.Date;

public class DailyEntryExtendedAnalysisDTO extends DailyEntryDTO{
    private TaxDTO taxDTO;
    private GoalDTO goalDTO;
    private double actual;
    private double recorded;
    private double overUnder;
    private double valuePerTranscation;
    private double percentageCard;
    private double percentageCash;
    private double percentageCheck;
    private double calculatedTax;

    public DailyEntryExtendedAnalysisDTO() {
    }


    public DailyEntryExtendedAnalysisDTO(Long id, String storeName, Long storeId, Date date, long transactionCount, double cashCount, double checkCount, double cardUnit, double payoutReceipt, double cashTape, double checkTape, double cardTape, double taxTape, boolean calculated, String memo) {
        super(id, storeName, storeId, date, transactionCount, cashCount, checkCount, cardUnit, payoutReceipt, cashTape, checkTape, cardTape, taxTape, calculated, memo);
    }

    public DailyEntryExtendedAnalysisDTO(TaxDTO taxDTO, GoalDTO goalDTO, double actual, double tape, double overUnder, double valuePerTranscation, double percentageCard, double percentageCash, double percentageCheck, double calculatedTax) {
        this.taxDTO = taxDTO;
        this.goalDTO = goalDTO;
        this.actual = actual;
        this.recorded = tape;
        this.overUnder = overUnder;
        this.valuePerTranscation = valuePerTranscation;
        this.percentageCard = percentageCard;
        this.percentageCash = percentageCash;
        this.percentageCheck = percentageCheck;
        this.calculatedTax = calculatedTax;
    }

    public DailyEntryExtendedAnalysisDTO(Long id, String storeName, Long storeId, Date date, long transactionCount, double cashCount, double checkCount, double cardUnit, double payoutReceipt, double cashTape, double checkTape, double cardTape, double taxTape, boolean calculated, String memo, TaxDTO taxDTO, GoalDTO goalDTO, double actual, double tape, double overUnder, double valuePerTranscation, double percentageCard, double percentageCash, double percentageCheck, double calculatedTax) {
        super(id, storeName, storeId, date, transactionCount, cashCount, checkCount, cardUnit, payoutReceipt, cashTape, checkTape, cardTape, taxTape, calculated, memo);
        this.taxDTO = taxDTO;
        this.goalDTO = goalDTO;
        this.actual = actual;
        this.recorded = tape;
        this.overUnder = overUnder;
        this.valuePerTranscation = valuePerTranscation;
        this.percentageCard = percentageCard;
        this.percentageCash = percentageCash;
        this.percentageCheck = percentageCheck;
        this.calculatedTax = calculatedTax;
    }



    public TaxDTO getTaxDTO() {
        return taxDTO;
    }

    public void setTaxDTO(TaxDTO taxDTO) {
        this.taxDTO = taxDTO;
    }

    public GoalDTO getGoalDTO() {
        return goalDTO;
    }

    public void setGoalDTO(GoalDTO goalDTO) {
        this.goalDTO = goalDTO;
    }

    public double getActual() {
        return Precision.round(actual,2);
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    public double getRecorded() {
        return  Precision.round(recorded,2);
    }

    public void setRecorded(double recorded) {
        this.recorded = recorded;
    }

    public double getOverUnder() {
        return  Precision.round(overUnder,2);
    }

    public void setOverUnder(double overUnder) {
        this.overUnder = overUnder;
    }

    public double getValuePerTranscation() {
        return  Precision.round(valuePerTranscation,2);
    }

    public void setValuePerTranscation(double valuePerTranscation) {
        this.valuePerTranscation = valuePerTranscation;
    }

    public double getPercentageCard() {
        return  Precision.round(percentageCard,2);
    }

    public void setPercentageCard(double percentageCard) {
        this.percentageCard = percentageCard;
    }

    public double getPercentageCash() {
        return  Precision.round(percentageCash,2);
    }

    public void setPercentageCash(double percentageCash) {
        this.percentageCash = percentageCash;
    }

    public double getPercentageCheck() {
        return  Precision.round(percentageCheck,2);
    }

    public void setPercentageCheck(double percentageCheck) {
        this.percentageCheck = percentageCheck;
    }

    public double getCalculatedTax() {
        return  Precision.round(calculatedTax,2);
    }

    public void setCalculatedTax(double calculatedTax) {
        this.calculatedTax = calculatedTax;
    }
}
