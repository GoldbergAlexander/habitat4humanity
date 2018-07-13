package com.agoldberg.hercules.service;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

/**
 * This class overrides the standard JavaMailSender so that the application does not attempt to create a mail object when email is disabled.
 */

@Service
@Profile("emailless")
public class JavaMailSenderWithoutEmail implements JavaMailSender{
    @Override
    public MimeMessage createMimeMessage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MimeMessage createMimeMessage(InputStream inputStream) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(SimpleMailMessage... simpleMailMessages) throws MailException {
        throw new UnsupportedOperationException();
    }
}
