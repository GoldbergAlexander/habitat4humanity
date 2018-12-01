package com.agoldberg.hercules.departmentrevenue;

import com.agoldberg.hercules.department.DepartmentDomain;
import com.agoldberg.hercules.store.StoreDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DepartmentRevenueDAO extends JpaRepository<DepartmentRevenueDomain, Long> {
    DepartmentRevenueDomain findByStoreAndDepartmentAndDate(StoreDomain store, DepartmentDomain department, Date date);
    List<DepartmentRevenueDomain> findByStoreOrderByDate(StoreDomain store);
    List<DepartmentRevenueDomain> findByDepartmentOrderByDate(DepartmentDomain department);
    List<DepartmentRevenueDomain> findByStoreAndDepartmentOrderByDate(StoreDomain storeDomain, DepartmentDomain departmentDomain);

    List<DepartmentRevenueDomain> findByDateGreaterThanEqualAndDateLessThanEqualOrderByDate(Date start, Date end);
    List<DepartmentRevenueDomain> findByStoreAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(StoreDomain store, Date start, Date end);
    List<DepartmentRevenueDomain> findByDepartmentAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(DepartmentDomain department, Date start, Date end);
    List<DepartmentRevenueDomain> findByStoreAndDepartmentAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(StoreDomain storeDomain, DepartmentDomain departmentDomain, Date start, Date end);

    List<DepartmentRevenueDomain> findByOrderByDate();
}
