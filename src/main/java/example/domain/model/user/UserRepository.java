package example.domain.model.user;

public interface UserRepository {
    User findBy(UserIdentifier id);

    Boolean isExist(User user);

    UserSummaries list();

    UserCandidate prototype();

    User register(UserCandidate user);

    void update(User user);

    void delete(User user);
}
