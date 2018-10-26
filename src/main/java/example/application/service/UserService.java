package example.application.service;

import example.domain.model.user.DateOfBirth;
import example.domain.model.user.Gender;
import example.domain.model.user.MailAddress;
import example.domain.model.user.Name;
import example.domain.model.user.PhoneNumber;
import example.domain.model.user.User;
import example.domain.model.user.UserCandidate;
import example.domain.model.user.UserIdentifier;
import example.domain.model.user.UserRepository;
import example.domain.model.user.UserSummaries;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public User findById(UserIdentifier id) {
        return userRepository.findBy(id);
    }

    public UserSummaries list() {
        return userRepository.list();
    }

    public UserCandidate prototype() {
        return userRepository.prototype();
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
    
    public void updateDateOfBirth(UserIdentifier identifier, DateOfBirth dateOfBirth) {
    	userRepository.updateDateOfBirth(identifier, dateOfBirth);
    }

    public void updateGender(UserIdentifier identifier, Gender gender) {
    	userRepository.updateGender(identifier, gender);
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
