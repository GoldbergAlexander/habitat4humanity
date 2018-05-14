package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.RoleDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<RoleDomain, Long>{
    RoleDomain findByName(String name);
}
