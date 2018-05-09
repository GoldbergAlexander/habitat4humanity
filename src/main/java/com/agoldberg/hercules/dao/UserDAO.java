package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<UserDomain, Long> {
    UserDomain findByUsername(String email);
}
