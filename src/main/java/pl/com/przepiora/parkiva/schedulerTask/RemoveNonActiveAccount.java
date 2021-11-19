package pl.com.przepiora.parkiva.schedulerTask;

import lombok.extern.slf4j.Slf4j;
import pl.com.przepiora.parkiva.model.User;
import pl.com.przepiora.parkiva.repository.UserRepository;

import java.util.Optional;

@Slf4j
public class RemoveNonActiveAccount implements Runnable {

    private UserRepository userRepository;
    private Long userId;

    public RemoveNonActiveAccount(UserRepository userRepository, Long userId) {
        this.userRepository = userRepository;
        this.userId = userId;
    }

    @Override
    public void run() {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.isEnabled()) {
                userRepository.deleteById(userId);
                log.info("User: " + user.getUsername() + " was deleted because was not activated.");
                return;
            }
            log.info("User: " + user.getUsername() + " is activated. Removing skipped.");
            return;
        }
        log.info("User with id: " + userId + " not exist");
    }
}
