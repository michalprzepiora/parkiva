package pl.com.przepiora.parkiva.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.com.przepiora.parkiva.model.User;
import pl.com.przepiora.parkiva.repository.UserRepository;

import java.util.Optional;

@Service
public class JpaUserDetailService implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private UserRepository userRepository;

    @Autowired
    public JpaUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user;
        Optional<User> usernameOptional = userRepository.findByUsername(s);
        if (usernameOptional.isPresent()) {
            user = usernameOptional.get();
            Assert.isTrue(user.isEnabled(), "User account is not activate. Please click an activation link on e-mail.");
            log.info("Logged user: {}, with roles: {}", user.getUsername(), user.getAuthorities());
            return user;
        }
        return null;
    }
}
