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
    List<DepartmentRevenueDomain> findByStore(StoreDomain store);
    List<DepartmentRevenueDomain> findByDepartment(DepartmentDomain department);
    List<DepartmentRevenueDomain> findByStoreAndDepartment(StoreDomain storeDomain, DepartmentDomain departmentDomain);

    List<DepartmentRevenueDomain> findByDateGreaterThanEqualAndDateLessThanEqual(Date start, Date end);
    List<DepartmentRevenueDomain> findByStoreAndDateGreaterThanEqualAndDateLessThanEqual(StoreDomain store, Date start, Date end);
    List<DepartmentRevenueDomain> findByDepartmentAndDateGreaterThanEqualAndDateLessThanEqual(DepartmentDomain department, Date start, Date end);
    List<DepartmentRevenueDomain> findByStoreAndDepartmentAndDateGreaterThanEqualAndDateLessThanEqual(StoreDomain storeDomain, DepartmentDomain departmentDomain, Date start, Date end);

}
