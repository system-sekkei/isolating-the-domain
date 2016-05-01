package example.infrastructure.datasource.user;

import example.domain.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by haljik on 15/06/04.
 */
@Repository
public class UserDatasource implements UserRepository {
    @Autowired
    UserMapper mapper;

    @Override
    public User findBy(UserIdentifier id) {
        return mapper.findBy(id);
    }

    @Override
    public Boolean isExist(UserIdentifier id) {
        if( findBy(id) == null ) return false;
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
        mapper.update(user);
    }

    @Override
    public void delete(User user) {
        mapper.delete(user);
    }
}
