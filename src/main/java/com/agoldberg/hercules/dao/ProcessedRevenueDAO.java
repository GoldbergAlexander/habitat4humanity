package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.ProcessedRevenueDomain;
import com.agoldberg.hercules.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProcessedRevenueDAO extends JpaRepository<ProcessedRevenueDomain, Long> {
    List<ProcessedRevenueDomain> findByDateBetween(Date date1, Date date2);
    List<ProcessedRevenueDomain> findByLocationDomainAndDateBetween(Store location, Date date1, Date date2);
    ProcessedRevenueDomain findByLocationDomainAndDate(Store location, Date date);
}
