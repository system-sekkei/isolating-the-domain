package example.application.service;

import example.domain.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by haljik on 15/06/04.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findById(UserIdentifier id) {
        return userRepository.findBy(id);
    }
    public Boolean isExist(UserIdentifier id) {return userRepository.isExist(id);}

    public UserSummaries list() {
        return userRepository.list();
    }

    public User prototype() { return userRepository.prototype();}

    public void register(User user) {
        userRepository.register(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
