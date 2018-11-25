package com.agoldberg.hercules.session;

import com.agoldberg.hercules.departmentrevenue.DepartmentRevenueDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class DepartmentRevenueStaging {
    private DepartmentRevenueDTO enteredRevenueDTO;
    private boolean staged;
    private boolean confirmed;

    public DepartmentRevenueStaging() {
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


    public DepartmentRevenueDTO getDTO() {
        return enteredRevenueDTO;
    }


    public void setDTO(DepartmentRevenueDTO enteredRevenueDTO) {
        this.enteredRevenueDTO = enteredRevenueDTO;
        staged = true;
    }


    public void reset(){
        staged = false;
        confirmed = false;
        enteredRevenueDTO = null;
    }
}
