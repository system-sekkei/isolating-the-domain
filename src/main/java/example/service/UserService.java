package example.service;

import example.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by haljik on 15/06/04.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<UserSummary> findById(UserId id) {
        return userRepository.findBy(id);
    }

    public UserSummaries list() {
        return userRepository.list();
    }

    public void register(User user) {
        userRepository.register(user);
    }
}
