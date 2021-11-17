package pl.com.przepiora.parkiva.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pl.com.przepiora.parkiva.model.User;

import java.util.Optional;

@Component
public class JpaAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public JpaAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userNameFromPage = authentication.getName();
        Object credentialFromPage = authentication.getCredentials();
        if (!(credentialFromPage instanceof String)) {
            return null;
        }
        Assert.notNull(userNameFromPage, "User can not be null.");
        Assert.notNull(credentialFromPage, "Password can not be null");
        Optional<UserDetails> userDetailsOptional = Optional.ofNullable(userDetailsService.loadUserByUsername(userNameFromPage));
        if (userDetailsOptional.isPresent()) {
            var userDetails = userDetailsOptional.get();
            boolean machPassword = passwordEncoder.matches((CharSequence) credentialFromPage, userDetails.getPassword());
            if (machPassword) {
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername()
                        , userDetails.getPassword(), userDetails.getAuthorities());
            }
        }
        throw new BadCredentialsException("Wrong username and/or password.");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
