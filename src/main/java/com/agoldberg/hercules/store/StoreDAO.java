package com.agoldberg.hercules.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreDAO extends JpaRepository<Store, Long>{
    Store findByName(String name);
    List<Store> findByEnabledIsTrue();
    Store findByIdAndEnabledIsTrue(Long id);
}
