package example.datasource.user;

import example.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by haljik on 15/06/04.
 */
@Repository
public class UserDatasource implements UserRepository {
    @Autowired
    UserMapper mapper;

    @Override
    public Optional<User> findBy(UserId id) {
        return Optional.ofNullable(mapper.findBy(id));
    }

    @Override
    public UserSummaries list() {
        return new UserSummaries(mapper.list());
    }

    @Override
    public void register(User user) {
        mapper.register(user);
    }

    @Override
    public void update(User user) {
        mapper.update(user);
    }

    @Override
    public void delete(User user) {
        mapper.delete(user);
    }
}
