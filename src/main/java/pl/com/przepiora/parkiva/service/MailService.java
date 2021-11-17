package pl.com.przepiora.parkiva.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.com.przepiora.parkiva.mailer.SimpleMessageFactory;

@Service
@Slf4j
public class MailService {

    private JavaMailSender javaMailSender;
    private SimpleMessageFactory simpleMessageFactory;

    @Autowired
    public MailService(JavaMailSender javaMailSender, SimpleMessageFactory simpleMessageFactory) {
        this.javaMailSender = javaMailSender;
        this.simpleMessageFactory = simpleMessageFactory;
    }

    public boolean sendActivationLink(String email, String token) {
        try {
            javaMailSender.send(simpleMessageFactory.getActivationLinkMessage(email, token));
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            log.debug("Mail was not send, because: {}", e.getLocalizedMessage());
        }
        return false;
    }
}
