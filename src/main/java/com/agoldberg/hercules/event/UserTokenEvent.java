package com.agoldberg.hercules.event;

import com.agoldberg.hercules.domain.TokenType;
import com.agoldberg.hercules.domain.UserDomain;
import org.springframework.context.ApplicationEvent;

public class UserTokenEvent extends ApplicationEvent {
    private String message;
    private UserDomain userDomain;
    private TokenType tokenType;

    public UserTokenEvent(Object source, UserDomain userDomain, TokenType tokenType) {
        super(source);
        this.userDomain = userDomain;
        this.tokenType = tokenType;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDomain getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
    }
}
