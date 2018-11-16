package com.agoldberg.hercules.goal;

import com.agoldberg.hercules.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GoalDAO extends JpaRepository<GoalDomain,Long> {
    List<GoalDomain> findByStore(Store store);
    GoalDomain findByStoreAndEndAfterOrStartBefore(Store store, Date start, Date end);
}
