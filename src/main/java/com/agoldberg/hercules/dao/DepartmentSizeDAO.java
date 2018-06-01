package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.DepartmentDomain;
import com.agoldberg.hercules.domain.DepartmentSizeDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepartmentSizeDAO extends JpaRepository<DepartmentSizeDomain, Long> {
    List<DepartmentSizeDomain> findByDepartment(DepartmentDomain departmentDomain);
}
