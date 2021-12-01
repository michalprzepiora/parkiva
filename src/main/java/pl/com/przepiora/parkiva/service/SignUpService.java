package pl.com.przepiora.parkiva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.com.przepiora.parkiva.model.Role;
import pl.com.przepiora.parkiva.model.User;
import pl.com.przepiora.parkiva.model.dto.UserDTO;
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

    public void signUp(UserDTO newUser, String password1, String password2, String token) {
        Optional<User> userOptional = userRepository.findByUsername(newUser.getUsername());
        Assert.isTrue(userOptional.isEmpty(), "The username is already used.");
        Assert.notNull(password1, "Password 1 can not be a null.");
        Assert.notNull(password2, "Password 2 can not be a null.");
        Assert.isTrue(password1.equals(password2), "Both passwords must be the same.");
        String password = passwordEncoder.encode(password1);

        User user = User.builder()
                .username(newUser.getUsername())
                .name(newUser.getName())
                .surname(newUser.getSurname())
                .phone(newUser.getPhone())
                .password(password)
                .activateToken(token)
                .tokenExpiredTime(LocalDateTime.now().plusMinutes(15L))
                .build();

        Optional<Role> userRoleOptional = roleRepository.findByValue(Role.USER);
        userRoleOptional.ifPresent(role -> user.setRoles(new HashSet<>(Collections.singleton(role))));
        User savedUser = userRepository.save(user);
        mailService.sendActivationLink(user.getUsername(), token);
        RemoveNonActiveAccount task = new RemoveNonActiveAccount(userRepository, savedUser.getId());
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

    public boolean isUsernameIsAlreadyUsed(String userName) {
        return userRepository.findByUsername(userName).isPresent();
    }
}
