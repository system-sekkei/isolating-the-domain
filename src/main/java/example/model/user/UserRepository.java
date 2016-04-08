package example.model.user;

import java.util.Optional;

/**
 * Created by haljik on 15/06/04.
 */
public interface UserRepository {
    User findBy(UserId id);

    UserSummaries list();

    User prototype();

    void register(User user);

    void update(User user);

    void delete(User user);
}
