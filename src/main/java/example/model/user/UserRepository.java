package example.model.user;

import java.util.Optional;

/**
 * Created by haljik on 15/06/04.
 */
public interface UserRepository {
    Optional<UserSummary> findBy(UserId id);

    UserSummaries list();

    void register(User user);
}
