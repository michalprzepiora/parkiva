package pl.com.przepiora.parkiva.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    AuthenticationProvider authenticationProvider;
//
//    @Autowired
//    public SecurityConfig(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/confirm_mail/**").permitAll()
                .antMatchers("/menu/**").permitAll()
                .antMatchers("/user/panel/home").hasAnyAuthority("USER")
                //todo h2 1 row below to delete
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/test").permitAll()
                .anyRequest().authenticated()
                //todo h2 2 rows below to delete
                .and().headers().frameOptions().sameOrigin()
                .and().csrf().ignoringAntMatchers("h2-console/**")
                .and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/user/panel/home")
//                .successForwardUrl("/user/panel/home")
                .usernameParameter("login")
                .passwordParameter("password")
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
