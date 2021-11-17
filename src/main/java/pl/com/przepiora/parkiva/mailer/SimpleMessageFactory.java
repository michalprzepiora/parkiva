package pl.com.przepiora.parkiva.mailer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class SimpleMessageFactory {

    @Value("${parkiva.host.url}")
    private String hostUrl;

    public SimpleMailMessage getActivationLinkMessage(String email, String token) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("parkiva.app@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Parkiva - activation link");
        simpleMailMessage.setText("Please click link to activate: " + hostUrl + "/confirm_mail?token=" + token);
        return simpleMailMessage;
    }
}
