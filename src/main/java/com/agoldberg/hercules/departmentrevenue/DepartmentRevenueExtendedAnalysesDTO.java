package com.agoldberg.hercules.departmentrevenue;

import java.util.Date;

public class DepartmentRevenueExtendedAnalysesDTO extends DepartmentRevenueDTO{
    double size;
    double revenuePerSize;

    public DepartmentRevenueExtendedAnalysesDTO(){
    }

    public DepartmentRevenueExtendedAnalysesDTO(Long id, Date date, double amount, String storeName, Long storeId, String departmentName, Long departmentId, double size, double revenuePerSize) {
        super(id, date, amount, storeName, storeId, departmentName, departmentId);
        this.size = size;
        this.revenuePerSize = revenuePerSize;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getRevenuePerSize() {
        return revenuePerSize;
    }

    public void setRevenuePerSize(double revenuePerSize) {
        this.revenuePerSize = revenuePerSize;
    }
}
