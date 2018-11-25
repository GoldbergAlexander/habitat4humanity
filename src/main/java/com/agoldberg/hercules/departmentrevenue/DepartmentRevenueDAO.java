package com.agoldberg.hercules.departmentrevenue;

import com.agoldberg.hercules.department.DepartmentDomain;
import com.agoldberg.hercules.store.StoreDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DepartmentRevenueDAO extends JpaRepository<DepartmentRevenueDomain, Long> {
    DepartmentRevenueDomain findByStoreAndDepartmentAndDate(StoreDomain store, DepartmentDomain department, Date date);
}
