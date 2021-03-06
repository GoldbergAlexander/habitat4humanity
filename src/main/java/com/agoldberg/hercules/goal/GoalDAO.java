package com.agoldberg.hercules.goal;

import com.agoldberg.hercules.store.StoreDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GoalDAO extends JpaRepository<GoalDomain,Long> {
    List<GoalDomain> findByStoreOrderByStart(StoreDomain store);
    GoalDomain findByStoreAndEndAfterOrStartBefore(StoreDomain store, Date start, Date end);
    GoalDomain findByStoreAndStartBeforeAndEndAfter(StoreDomain store, Date start, Date end);
    GoalDomain findByStoreAndStartLessThanEqualAndEndGreaterThanEqual(StoreDomain store, Date end, Date start);
    GoalDomain findByIdNotAndStoreAndStartLessThanEqualAndEndGreaterThanEqual(Long id, StoreDomain store, Date end, Date start);

}
