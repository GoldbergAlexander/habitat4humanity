package com.agoldberg.hercules.session;

import com.agoldberg.hercules.dto.EnteredRevenueBatchDTO;
import com.agoldberg.hercules.dto.EnteredRevenueDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class EnteredRevenueBatchStaging {
    private EnteredRevenueBatchDTO enteredRevenueBatchDTO;
    private boolean staged;
    private boolean confirmed;

    public EnteredRevenueBatchStaging() {
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


    public EnteredRevenueBatchDTO getDTO() {
        return enteredRevenueBatchDTO;
    }


    public void setDTO(EnteredRevenueBatchDTO enteredRevenueDTO) {
        this.enteredRevenueBatchDTO = enteredRevenueDTO;
        staged = true;
    }


    public void reset(){
        staged = false;
        confirmed = false;
        enteredRevenueBatchDTO = null;
    }
}
