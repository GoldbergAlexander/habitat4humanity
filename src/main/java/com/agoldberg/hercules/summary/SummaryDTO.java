package com.agoldberg.hercules.summary;

import com.agoldberg.hercules.goal.GoalDTO;
import com.agoldberg.hercules.store.StoreDTO;
import com.agoldberg.hercules.tax.TaxDTO;

import java.util.Date;
import java.util.List;

public class SummaryDTO {
    private StoreDTO storeDTO;
    private GoalDTO goalDTO;
    private double currentYearActual;
    private double priorYearActual;
    private double monthOverMonthActual;
    private double monthOverMonthPercent;
    private double currentYearGoal;
    private double monthOverGoalActual;
    private double monthOverGoalPercent;

    public SummaryDTO() {
    }

    public SummaryDTO(StoreDTO storeDTO, GoalDTO goalDTO, double currentYearActual, double priorYearActual, double monthOverMonthActual, double monthOverMonthPercent, double currentYearGoal, double monthOverGoalActual, double monthOverGoalPercent) {
        this.storeDTO = storeDTO;
        this.goalDTO = goalDTO;
        this.currentYearActual = currentYearActual;
        this.priorYearActual = priorYearActual;
        this.monthOverMonthActual = monthOverMonthActual;
        this.monthOverMonthPercent = monthOverMonthPercent;
        this.currentYearGoal = currentYearGoal;
        this.monthOverGoalActual = monthOverGoalActual;
        this.monthOverGoalPercent = monthOverGoalPercent;
    }

    public StoreDTO getStoreDTO() {
        return storeDTO;
    }

    public void setStoreDTO(StoreDTO storeDTO) {
        this.storeDTO = storeDTO;
    }

    public GoalDTO getGoalDTO() {
        return goalDTO;
    }

    public void setGoalDTO(GoalDTO goalDTO) {
        this.goalDTO = goalDTO;
    }

    public double getCurrentYearActual() {
        return currentYearActual;
    }

    public void setCurrentYearActual(double currentYearActual) {
        this.currentYearActual = currentYearActual;
    }

    public double getPriorYearActual() {
        return priorYearActual;
    }

    public void setPriorYearActual(double priorYearActual) {
        this.priorYearActual = priorYearActual;
    }

    public double getMonthOverMonthActual() {
        return monthOverMonthActual;
    }

    public void setMonthOverMonthActual(double monthOverMonthActual) {
        this.monthOverMonthActual = monthOverMonthActual;
    }

    public double getMonthOverMonthPercent() {
        return monthOverMonthPercent;
    }

    public void setMonthOverMonthPercent(double monthOverMonthPercent) {
        this.monthOverMonthPercent = monthOverMonthPercent;
    }

    public double getCurrentYearGoal() {
        return currentYearGoal;
    }

    public void setCurrentYearGoal(double currentYearGoal) {
        this.currentYearGoal = currentYearGoal;
    }

    public double getMonthOverGoalActual() {
        return monthOverGoalActual;
    }

    public void setMonthOverGoalActual(double monthOverGoalActual) {
        this.monthOverGoalActual = monthOverGoalActual;
    }

    public double getMonthOverGoalPercent() {
        return monthOverGoalPercent;
    }

    public void setMonthOverGoalPercent(double monthOverGoalPercent) {
        this.monthOverGoalPercent = monthOverGoalPercent;
    }
}
