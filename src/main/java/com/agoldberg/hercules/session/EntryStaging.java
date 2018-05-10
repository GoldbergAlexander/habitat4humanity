package com.agoldberg.hercules.session;

import com.agoldberg.hercules.dto.EnteredRevenueDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class EntryStaging {
    private EnteredRevenueDTO enteredRevenueDTO;
    private boolean staged;
    private boolean confirmed;

    public EntryStaging() {
        staged = false;
        confirmed = false;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isStaged() {
        return staged;
    }


    public void setStaged(boolean staged) {
        this.staged = staged;
    }

    public EnteredRevenueDTO getEnteredRevenueDTO() {
        return enteredRevenueDTO;
    }

    public void setEnteredRevenueDTO(EnteredRevenueDTO enteredRevenueDTO) {
        this.enteredRevenueDTO = enteredRevenueDTO;
        staged = true;
    }

    public void reset(){
        staged = false;
        confirmed = false;
        enteredRevenueDTO = null;
    }
}
