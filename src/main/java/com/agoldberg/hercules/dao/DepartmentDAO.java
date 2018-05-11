package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.DepartmentDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartmentDAO extends JpaRepository<DepartmentDomain, Long>{
    List<DepartmentDomain> findByEnabledIsTrue();
    DepartmentDomain findByIdAndEnabledIsTrue(Long id);
}
