package com.agoldberg.hercules.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TokenDomain extends Auditable<String>{
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private UserDomain user;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private Date created;
    

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDomain getUser() {
        return user;
    }

    public void setUser(UserDomain user) {
        this.user = user;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
