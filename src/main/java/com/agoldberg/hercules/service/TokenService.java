package com.agoldberg.hercules.service;

import com.agoldberg.hercules.domain.TokenType;
import com.agoldberg.hercules.domain.UserDomain;

public interface TokenService {
    void createToken(UserDomain user, TokenType type);

    UserDomain validateToken(String uuid, TokenType type);
}
