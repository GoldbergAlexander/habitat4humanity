package com.agoldberg.hercules.dailyentry;

import com.agoldberg.hercules.store.StoreDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DailyEntryDAO extends JpaRepository<DailyEntryDomain,Long> {
    DailyEntryDomain findByStoreAndDate(StoreDomain store, Date date);
}
