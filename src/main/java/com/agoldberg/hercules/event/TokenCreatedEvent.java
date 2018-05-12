package com.agoldberg.hercules.event;

import com.agoldberg.hercules.domain.TokenDomain;
import org.springframework.context.ApplicationEvent;

public class TokenCreatedEvent extends ApplicationEvent{
    private String message;
    private TokenDomain tokenDomain;

    public TokenCreatedEvent(Object source, TokenDomain tokenDomain) {
        super(source);
        this.tokenDomain = tokenDomain;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TokenDomain getTokenDomain() {
        return tokenDomain;
    }

    public void setTokenDomain(TokenDomain tokenDomain) {
        this.tokenDomain = tokenDomain;
    }
}
