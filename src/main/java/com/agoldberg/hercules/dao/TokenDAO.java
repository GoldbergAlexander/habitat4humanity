package com.agoldberg.hercules.dao;

import com.agoldberg.hercules.domain.TokenDomain;
import com.agoldberg.hercules.domain.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDAO extends JpaRepository<TokenDomain, Long>{
    TokenDomain findByTokenAndTokenType(String token, TokenType type);
}
