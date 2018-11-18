package com.agoldberg.hercules.size;

import com.agoldberg.hercules.store.StoreDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SizeDAO extends JpaRepository<SizeDomain,Long> {
    List<SizeDomain> findByStore(StoreDomain store);
    SizeDomain findByStoreAndEndAfterOrStartBefore(StoreDomain store, Date start, Date end);
}
