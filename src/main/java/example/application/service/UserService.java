package example.application.service;

import example.domain.model.user.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public User findById(UserIdentifier id) {
        return userRepository.findBy(id);
    }

    public Users list() {
        return userRepository.list();
    }

    public User register(UserCandidate user) {
        return userRepository.register(user);
    }

    public void updateName(UserIdentifier identifier, Name name) {
        userRepository.updateName(identifier, name);
    }

    public void updateMailAddress(UserIdentifier identifier, MailAddress mailAddress) {
        userRepository.updateMailAddress(identifier, mailAddress);
    }

    public void updatePhoneNumber(UserIdentifier identifier, PhoneNumber phoneNumber) {
        userRepository.updatePhoneNumber(identifier, phoneNumber);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
