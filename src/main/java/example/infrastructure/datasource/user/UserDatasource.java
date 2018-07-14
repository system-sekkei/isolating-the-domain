package example.infrastructure.datasource.user;

import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
import example.domain.model.user.UserRepository;
import example.domain.model.user.UserSummaries;
import org.springframework.stereotype.Repository;

@Repository
public class UserDatasource implements UserRepository {
    final UserMapper mapper;

    @Override
    public User findBy(UserIdentifier id) {
        return mapper.findBy(id);
    }

    @Override
    public Boolean isExist(User user) {
        if (findBy(user.identifier()) == null) return false;
        return true;
    }

    @Override
    public UserSummaries list() {
        return new UserSummaries(mapper.list());
    }

    @Override
    public User prototype() {
        return new User();
    }

    @Override
    public void register(User user) {
        mapper.register(user);
    }

    @Override
    public void update(User user) {
        mapper.delete(user);
        mapper.register(user);
    }

    @Override
    public void delete(User user) {
        mapper.delete(user);
    }

    public UserDatasource(UserMapper mapper) {
        this.mapper = mapper;
    }
}
