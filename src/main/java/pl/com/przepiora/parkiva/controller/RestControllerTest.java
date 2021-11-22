package pl.com.przepiora.parkiva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.przepiora.parkiva.mailer.TokenGenerator;
import pl.com.przepiora.parkiva.repository.UserRepository;
import pl.com.przepiora.parkiva.service.MailService;

@RestController
public class RestControllerTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;
    private TokenGenerator tokenGenerator;
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    public RestControllerTest(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService, TokenGenerator tokenGenerator,
                              ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.tokenGenerator = tokenGenerator;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;

    }

    @GetMapping("/test")
    public void test() {
        System.out.println("Start: q ");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        System.out.println(authentication.getAuthorities());


    }

    @GetMapping("/mail")
    void mail() {
        mailService.sendActivationLink("michal.przepiora@gmail.com", "https://wiadomosci.wp.pl/kryzys-na-granicy-to-nagranie-wywolalo-burze-w-sieci-6702647639505760a");

    }


}
