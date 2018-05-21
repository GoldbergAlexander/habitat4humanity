package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.DepartmentSizeDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentSizeDAO extends JpaRepository<DepartmentSizeDomain, Long> {
}
