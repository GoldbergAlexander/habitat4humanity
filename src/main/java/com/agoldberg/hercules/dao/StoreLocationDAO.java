package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.StoreLocationDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreLocationDAO extends JpaRepository<StoreLocationDomain, Long>{
    StoreLocationDomain findByName(String name);
    List<StoreLocationDomain> findByEnabledIsTrue();
}
