package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.EnteredRevenueDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnteredRevenueDAO extends JpaRepository<EnteredRevenueDomain, Long>{
}
