package pl.com.przepiora.parkiva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.com.przepiora.parkiva.model.Role;
import pl.com.przepiora.parkiva.model.User;
import pl.com.przepiora.parkiva.repository.RoleRepository;
import pl.com.przepiora.parkiva.repository.UserRepository;
import pl.com.przepiora.parkiva.schedulerTask.RemoveNonActiveAccount;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SignUpService {

    private static final long TIME_MS_TO_ACTIVATE = 900_000; //15 min to activate. Otherwise account will be deleted.
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;
    private RoleRepository roleRepository;
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    public SignUpService(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService,
                         RoleRepository roleRepository, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.roleRepository = roleRepository;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    public void signUp(String email, String password1, String password2, String token, String name, String surname, String phone, String address) {
        Optional<User> userOptional = userRepository.findByUsername(email);
        Assert.isTrue(userOptional.isEmpty(), "The username is already used.");
        Assert.notNull(password1, "Password 1 can not be a null.");
        Assert.notNull(password2, "Password 2 can not be a null.");
        Assert.isTrue(password1.equals(password2), "Both passwords must be the same.");
        String password = passwordEncoder.encode(password1);
        User user = User.builder()
                .username(email)
                .password(password)
                .activateToken(token)
                .name(name)
                .surname(surname)
                .phone(phone)
                .address(address)
                .tokenExpiredTime(LocalDateTime.now().plusMinutes(15L))
                .build();
        Optional<Role> userRoleOptional = roleRepository.findByValue(Role.USER);
        userRoleOptional.ifPresent(role -> user.setRoles(new HashSet<>(Collections.singleton(role))));
        userRepository.save(user);
        mailService.sendActivationLink(email, token);
        RemoveNonActiveAccount task = new RemoveNonActiveAccount(userRepository, user.getId());
        threadPoolTaskScheduler.schedule(task, new Date(System.currentTimeMillis() + TIME_MS_TO_ACTIVATE));
    }

    public void confirmMail(String token) {
        Optional<User> userOptional = userRepository.findByActivateToken(token);
        Assert.isTrue(userOptional.isPresent(), "Could not find token to activate.");
        User user = userOptional.get();
        Assert.isTrue(!user.isEnabled(), "User is already activated");
        user.setEnabled(true);
        user.setActivateToken(null);
        user.setTokenExpiredTime(null);
        userRepository.save(user);
    }
}
