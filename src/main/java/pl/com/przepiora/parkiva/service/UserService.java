package pl.com.przepiora.parkiva.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.com.przepiora.parkiva.model.Car;
import pl.com.przepiora.parkiva.model.Role;
import pl.com.przepiora.parkiva.model.User;
import pl.com.przepiora.parkiva.model.dto.CarDTO;
import pl.com.przepiora.parkiva.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, String> findUserDataByUsername(String userName) {
        Optional<User> userOptional = userRepository.findByUsername(userName);
        Assert.isTrue(userOptional.isPresent(), "Cant find User: " + userName + " in database");
        User user = userOptional.get();
        Map<String, String> result = new HashMap<>();
        result.put("email", user.getUsername());
        result.put("name", user.getName());
        result.put("surname", user.getSurname());
        result.put("phone", user.getPhone());
        result.put("address", user.getAddress());
        StringBuilder rolesBuilder = new StringBuilder();
        for (Role role : user.getRoles()) {
            rolesBuilder.append(role.getValue());
            rolesBuilder.append(", ");
        }
        result.put("rolesString", removeTwoLastChars(rolesBuilder.toString()));
        return result;
    }

    public void addCarToUser(String username, CarDTO carDTO) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User: " + username + " is not exist.");
        }
        Car car = Car.builder().mark(carDTO.getMark())
                .model(carDTO.getModel())
                .color(carDTO.getColor())
                .registrationNumber(carDTO.getRegistrationNumber())
                .build();
        User user = userOptional.get();
        user.addCar(car);
        userRepository.save(user);

    }

    private String removeTwoLastChars(String words) {
        if (words.length() > 2) {
            return words.substring(0, words.length() - 2);
        }
        return words;
    }
}
