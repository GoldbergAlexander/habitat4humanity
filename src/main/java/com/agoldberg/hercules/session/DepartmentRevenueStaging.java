package com.agoldberg.hercules.session;

import com.agoldberg.hercules.dto.DepartmentRevenueDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class DepartmentRevenueStaging {
    private boolean staged;
    private boolean confirmed;
    private DepartmentRevenueDTO departmentRevenueDTO;

    public DepartmentRevenueStaging() {
        staged = false;
        confirmed = false;
    }

    public boolean isStaged() {
        return staged;
    }

    public void setStaged(boolean staged) {
        this.staged = staged;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public DepartmentRevenueDTO getDepartmentRevenueDTO() {
        return departmentRevenueDTO;
    }

    public void setDepartmentRevenueDTO(DepartmentRevenueDTO departmentRevenueDTO) {
        this.departmentRevenueDTO = departmentRevenueDTO;
        setStaged(true);
    }

    public void reset(){
        staged = false;
        confirmed = false;
        departmentRevenueDTO = null;
    }
}
