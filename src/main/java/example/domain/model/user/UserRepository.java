package example.domain.model.user;

/**
 * 利用者リポジトリ
 */
public interface UserRepository {
    User findBy(UserIdentifier id);

    Users list();

    User register(UserCandidate user);

    void updateName(UserIdentifier identifier, Name name);

    void updateMailAddress(UserIdentifier identifier, MailAddress mailAddress);

    void updatePhoneNumber(UserIdentifier identifier, PhoneNumber phoneNumber);

    void delete(User user);
}
