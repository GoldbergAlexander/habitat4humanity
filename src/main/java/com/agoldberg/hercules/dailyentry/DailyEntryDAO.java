package com.agoldberg.hercules.dailyentry;

import com.agoldberg.hercules.store.StoreDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyEntryDAO extends JpaRepository<DailyEntryDomain,Long> {
    DailyEntryDomain findByStoreAndDate(StoreDomain store, Date date);
    List<DailyEntryDomain> findByStore(StoreDomain storeDomain);
    List<DailyEntryDomain> findByStoreAndDateGreaterThanEqualAndDateLessThanEqual(StoreDomain storeDomain, Date start, Date end);
    List<DailyEntryDomain> findByDateGreaterThanEqualAndDateLessThanEqual(Date start, Date end);
}
