package com.agoldberg.hercules.tax;

import com.agoldberg.hercules.store.StoreDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaxDAO extends JpaRepository<TaxDomain,Long> {
    List<TaxDomain> findByStore(StoreDomain store);
    TaxDomain findByStoreAndEndAfterOrStartBefore(StoreDomain store, Date start, Date end);
    TaxDomain findByStoreAndStartBeforeAndEndAfter(StoreDomain store, Date start, Date end);
}
