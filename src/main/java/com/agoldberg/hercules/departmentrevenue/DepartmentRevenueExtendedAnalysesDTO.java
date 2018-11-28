package com.agoldberg.hercules.departmentrevenue;

import com.agoldberg.hercules.size.SizeDTO;

import java.util.Date;

public class DepartmentRevenueExtendedAnalysesDTO extends DepartmentRevenueDTO{
    SizeDTO sizeDTO;
    double size;
    double revenuePerSize;

    public DepartmentRevenueExtendedAnalysesDTO(){
    }


    public DepartmentRevenueExtendedAnalysesDTO(Long id, Date date, double amount, String storeName, Long storeId, String departmentName, Long departmentId, SizeDTO sizeDTO, double size, double revenuePerSize) {
        super(id, date, amount, storeName, storeId, departmentName, departmentId);
        this.sizeDTO = sizeDTO;
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

    public SizeDTO getSizeDTO() {
        return sizeDTO;
    }

    public void setSizeDTO(SizeDTO sizeDTO) {
        this.sizeDTO = sizeDTO;
    }
}
