package pl.com.przepiora.parkiva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.przepiora.parkiva.model.Car;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByRegistrationNumber(String registrationNumber);
}
