package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.DepartmentRevenueDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRevenueDAO extends JpaRepository<DepartmentRevenueDomain, Long>{

}
