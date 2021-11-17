package pl.com.przepiora.parkiva.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Transient
    public static final String USER = "USER";
    @Transient
    public static final String ADMIN = "ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String value;

    @Column(name = "users")
    @ManyToMany(mappedBy = "roles")
    Set<User> users;
}
