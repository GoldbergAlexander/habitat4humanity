package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.DepartmentDomain;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartmentDAO extends JpaRepository<DepartmentDomain, Long>{
    List<DepartmentDomain> findByEnabledIsTrue();
    List<DepartmentDomain> findByEnabledIsTrueAndLocation(StoreLocationDomain location);
    DepartmentDomain findByIdAndEnabledIsTrue(Long id);
}
