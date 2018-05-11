package com.agoldberg.hercules.event;

import com.agoldberg.hercules.domain.EnteredRevenueDomain;
import org.springframework.context.ApplicationEvent;

public class RevenueEnteredEvent extends ApplicationEvent{
    private String message;
    private EnteredRevenueDomain enteredRevenue;

    public RevenueEnteredEvent(Object source, EnteredRevenueDomain enteredRevenue) {
        super(source);
        this.enteredRevenue = enteredRevenue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EnteredRevenueDomain getEnteredRevenue() {
        return enteredRevenue;
    }

    public void setEnteredRevenue(EnteredRevenueDomain enteredRevenue) {
        this.enteredRevenue = enteredRevenue;
    }
}
