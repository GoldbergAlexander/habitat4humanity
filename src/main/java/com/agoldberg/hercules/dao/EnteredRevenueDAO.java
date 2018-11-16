package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.EnteredRevenueDomain;
import com.agoldberg.hercules.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EnteredRevenueDAO extends JpaRepository<EnteredRevenueDomain, Long>{
    EnteredRevenueDomain findByLocationAndDate(Store locationDomain, Date date);
}
