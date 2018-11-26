package com.agoldberg.hercules.dailyentry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyEntryDAO extends JpaRepository<DailyEntryDomain,Long> {

}
