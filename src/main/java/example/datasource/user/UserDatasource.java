package example.datasource.user;

import example.model.user.User;
import example.model.user.UserId;
import example.model.user.UserRepository;
import example.model.user.Users;
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
    public Users list() {
        return new Users(mapper.list());
    }
}
