package com.agoldberg.hercules.service;

import com.agoldberg.hercules.domain.TokenDomain;
import com.agoldberg.hercules.domain.UserDomain;
import com.agoldberg.hercules.event.TokenCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService implements ApplicationListener<TokenCreatedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    //Base for application
    @Value("${base.url:http://localhost:8080}")
    private String applicationURL;

    @Value("${confirm.url:/confirm?token=}")
    private String confirmURL;

    @Value("${reset.url:/reset?token=}")
    private String resetURL;

    @Value("${confirm.message:Account Created}")
    private String confirmMessage;

    @Value("${reset.message:Password Reset}")
    private String resetMessage;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(TokenCreatedEvent tokenCreatedEvent) {
        LOGGER.debug("Got Token Created Event");
        switch (tokenCreatedEvent.getTokenDomain().getTokenType()){
            case CONFRIM:sendConfirmationEmail(tokenCreatedEvent.getTokenDomain());
            break;
            case RESET:sendResetEmail(tokenCreatedEvent.getTokenDomain());
            break;
        }
    }

    private void sendConfirmationEmail(TokenDomain tokenDomain){
        LOGGER.debug("Building Confirmation Email");
        SimpleMailMessage email = new SimpleMailMessage();
        UserDomain user = tokenDomain.getUser();
        String userEmail = user.getUsername();
        email.setTo(userEmail);
        email.setSubject(confirmMessage);
        email.setText(applicationURL+confirmURL+tokenDomain.getToken());
        mailSender.send(email);
        LOGGER.info("Sent Confirmation Email: " + email.toString());
    }

    private void sendResetEmail(TokenDomain tokenDomain){
        LOGGER.debug("Building Reset Email");
        SimpleMailMessage email = new SimpleMailMessage();
        UserDomain user = tokenDomain.getUser();
        String userEmail = user.getUsername();
        email.setTo(userEmail);
        email.setSubject(resetMessage);
        email.setText(applicationURL+resetURL+tokenDomain.getToken());
        mailSender.send(email);
        LOGGER.info("Sent Reset Email: " + email.toString());
    }
}
