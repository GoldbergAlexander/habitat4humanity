package com.agoldberg.hercules.tax;

import com.agoldberg.hercules.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaxDAO extends JpaRepository<TaxDomain,Long> {
    List<TaxDomain> findByStore(Store store);
    TaxDomain findByStoreAndEndAfterOrStartBefore(Store store, Date start, Date end);
}
