package example.domain.model.user;

/**
 * 利用者リポジトリ
 */
public interface UserRepository {
    User findBy(UserIdentifier id);

    UserSummaries list();

    UserCandidate prototype();

    User register(UserCandidate user);

    void updateName(UserIdentifier identifier, Name name);

    void updateMailAddress(UserIdentifier identifier, MailAddress mailAddress);

    void updateDateOfBirth(UserIdentifier identifier, DateOfBirth dateOfBirth);

    void updateGender(UserIdentifier identifier, Gender gender);

    void updatePhoneNumber(UserIdentifier identifier, PhoneNumber phoneNumber);

    void delete(User user);
}
