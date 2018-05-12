package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.TokenDAO;
import com.agoldberg.hercules.domain.TokenDomain;
import com.agoldberg.hercules.domain.TokenType;
import com.agoldberg.hercules.domain.UserDomain;
import com.agoldberg.hercules.event.TokenCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    private TokenDAO tokenDAO;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void createToken(UserDomain user, TokenType type){
        TokenDomain token = new TokenDomain();
        token.setCreated(new Date());
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setTokenType(type);
        tokenDAO.save(token);
        LOGGER.info("Created Token: " + token.getToken() + " for User: " + token.getUser().getUsername());

        TokenCreatedEvent event = new TokenCreatedEvent(this, token);
        eventPublisher.publishEvent(event);
        LOGGER.debug("Published Token Created Event");
    }

    public UserDomain validateToken(String uuid, TokenType type){
        LOGGER.debug("Searching for token: " + uuid);
        TokenDomain token = tokenDAO.findByTokenAndTokenType(uuid, type);
        if(token != null){
            LOGGER.info("Confirmed token for user: " +token.getUser().getUsername());
            return token.getUser();
        }else {
            return null;
        }
    }
}