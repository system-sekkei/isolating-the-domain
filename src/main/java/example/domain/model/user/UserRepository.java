package example.domain.model.user;

import example.domain.type.age.DateOfBirth;
import example.domain.type.gender.Gender;

/**
 * 利用者リポジトリ
 */
public interface UserRepository {
    User findBy(UserIdentifier id);

    Users list();

    User register(UserCandidate user);

    void updateName(UserIdentifier identifier, Name name);

    void updateMailAddress(UserIdentifier identifier, MailAddress mailAddress);

    void updateDateOfBirth(UserIdentifier identifier, DateOfBirth dateOfBirth);

    void updateGender(UserIdentifier identifier, Gender gender);

    void updatePhoneNumber(UserIdentifier identifier, PhoneNumber phoneNumber);

    void delete(User user);
}
