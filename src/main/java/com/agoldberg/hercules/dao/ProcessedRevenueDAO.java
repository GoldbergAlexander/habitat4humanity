package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.ProcessedRevenueDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedRevenueDAO extends JpaRepository<ProcessedRevenueDomain, Long> {
}
