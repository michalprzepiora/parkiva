package pl.com.przepiora.parkiva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.przepiora.parkiva.mailer.TokenGenerator;
import pl.com.przepiora.parkiva.model.User;
import pl.com.przepiora.parkiva.repository.UserRepository;
import pl.com.przepiora.parkiva.service.MailService;

@RestController
public class RestControllerTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;
    private TokenGenerator tokenGenerator;

    @Autowired
    public RestControllerTest(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService, TokenGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.tokenGenerator = tokenGenerator;


    }

    @GetMapping("/test")
    public void test() {
        System.out.println(tokenGenerator.generate());
    }

    @GetMapping("/mail")
    void mail() {
        mailService.sendActivationLink("michal.przepiora@gmail.com", "https://wiadomosci.wp.pl/kryzys-na-granicy-to-nagranie-wywolalo-burze-w-sieci-6702647639505760a");

    }


}
