package example.datasource.user;

import example.model.user.User;
import example.model.user.UserId;
import example.model.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by haljik on 15/06/04.
 */
@Repository
public class UserDatasource implements UserRepository {
    UserMapper mapper;

    @Override
    public Optional<User> findBy(UserId id) {
        return Optional.ofNullable(mapper.findBy(id));
    }
}
