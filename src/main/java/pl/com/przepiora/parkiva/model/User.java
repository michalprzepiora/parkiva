package pl.com.przepiora.parkiva.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @Email
    private String username;
    @Size(min = 3, max = 50, message = "Password must be between 3 and 50 characters long.")
    private String password;
    private boolean accountExpired;
    private boolean accountLock;
    private boolean credentialsExpired;
    private boolean enabled;
    private String activateToken;
    private LocalDateTime tokenExpiredTime;

    @NotBlank(message = "Name must be not empty.")
    private String name;
    @NotBlank(message = "Surname must be not empty.")
    private String surname;
    @NotBlank(message = "Phone number must be not empty.")
    private String phone;
    private String address;

    @Column(name = "roles")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        accountExpired = false;
        accountLock = false;
        credentialsExpired = false;
        enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public boolean isAccountLock() {
        return accountLock;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public void setAccountLock(boolean accountLock) {
        this.accountLock = accountLock;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
