package com.agoldberg.hercules.dto;

import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Value;

public class SummaryStatsDTO {
    /**
     * Month to date:
     * Sum of Actual Daily Pre-Tax Intake between and including [1st of Current Month] and [Today-1]
     */
    private double monthToDate;

    /**
     * Previous Year's Month to Date:
     * Sum of Actual Daily Pre-Tax Intake between and including [1st of (Current Month - 12)] and [Today-1-1Year]
     */
    private double priorYearMonthToDate;

    /**
     * Month over Month Dollars:
     * Current MTD Revenue - PreviousYr MTD Revenue
     */
    private double monthOverMonthDollars;

    /**
     * Month over Month Percent:
     * MOM MTD Revenue Dollars / PreviousYr MTD Revenue
     */
    private double monthOverMonthPercent;

    /**
     * Goal:
     * Manually Entered
     */
    @Value("${goal:0.1}")
    private double goal;

    /**
     * Month to Date Goal:
     * PreviousYr MTD Revenue * (1+Growth Goal)
     */
    private double monthToDateGoal;

    /**
     * Month to Date Performance Dollars
     * Current MTD Revenue - MTD Revenue Goal
     */
    private double monthToDatePerformanceDollars;

    /**
     * Month to Date Performance Percent
     * MTD  / MTD Revenue Goal
     */
    private double monthToDatePerformancePercent;

    public SummaryStatsDTO() {
    }

    public SummaryStatsDTO(double monthToDate, double priorYearMonthToDate) {
        this.monthToDate = Precision.round(monthToDate,2);
        this.priorYearMonthToDate = Precision.round(priorYearMonthToDate,2);
        this.monthOverMonthDollars = Precision.round(this.monthToDate - this.priorYearMonthToDate,2);
        this.monthOverMonthPercent = Precision.round(this.monthToDate / this.priorYearMonthToDate,2);
        this.monthToDateGoal = Precision.round(this.priorYearMonthToDate * (1 + goal),2);
        this.monthToDatePerformanceDollars = Precision.round(this.monthToDate - this.monthToDateGoal,2);
        this.monthToDatePerformancePercent = Precision.round(this.monthToDate / this.monthToDateGoal,2);
    }

    public double getMonthToDate() {
        return monthToDate;
    }

    public void setMonthToDate(double monthToDate) {
        this.monthToDate = monthToDate;
    }

    public double getPriorYearMonthToDate() {
        return priorYearMonthToDate;
    }

    public void setPriorYearMonthToDate(double priorYearMonthToDate) {
        this.priorYearMonthToDate = priorYearMonthToDate;
    }

    public double getMonthOverMonthDollars() {
        return monthOverMonthDollars;
    }

    public void setMonthOverMonthDollars(double monthOverMonthDollars) {
        this.monthOverMonthDollars = monthOverMonthDollars;
    }

    public double getMonthOverMonthPercent() {
        return monthOverMonthPercent;
    }

    public void setMonthOverMonthPercent(double monthOverMonthPercent) {
        this.monthOverMonthPercent = monthOverMonthPercent;
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public double getMonthToDateGoal() {
        return monthToDateGoal;
    }

    public void setMonthToDateGoal(double monthToDateGoal) {
        this.monthToDateGoal = monthToDateGoal;
    }

    public double getMonthToDatePerformanceDollars() {
        return monthToDatePerformanceDollars;
    }

    public void setMonthToDatePerformanceDollars(double monthToDatePerformanceDollars) {
        this.monthToDatePerformanceDollars = monthToDatePerformanceDollars;
    }

    public double getMonthToDatePerformancePercent() {
        return monthToDatePerformancePercent;
    }

    public void setMonthToDatePerformancePercent(double monthToDatePerformancePercent) {
        this.monthToDatePerformancePercent = monthToDatePerformancePercent;
    }
}
