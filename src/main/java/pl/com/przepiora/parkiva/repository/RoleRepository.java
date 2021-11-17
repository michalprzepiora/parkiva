package pl.com.przepiora.parkiva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.przepiora.parkiva.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByValue(String value);
}
