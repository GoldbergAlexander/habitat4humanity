package com.agoldberg.hercules.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreDAO extends JpaRepository<StoreDomain, Long>{
    StoreDomain findByName(String name);
    List<StoreDomain> findByEnabledIsTrue();
    StoreDomain findByIdAndEnabledIsTrue(Long id);
}
