package com.agoldberg.hercules.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentDAO extends JpaRepository<DepartmentDomain, Long> {
     DepartmentDomain findByName(String name);
     DepartmentDomain findByIdAndEnabledIsTrue(Long id);
     List<DepartmentDomain> findByEnabledIsTrue();

}
