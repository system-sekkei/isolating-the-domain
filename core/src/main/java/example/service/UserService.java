package example.service;

import example.model.user.User;
import example.model.user.UserId;
import example.model.user.UserRepository;
import example.model.user.Users;
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

    public Optional<User> findById(UserId id) {
        return userRepository.findBy(id);
    }

    public Users list() {
        return userRepository.list();
    }
}
