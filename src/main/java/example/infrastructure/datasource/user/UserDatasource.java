package example.infrastructure.datasource.user;

import org.springframework.stereotype.Repository;

import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
import example.domain.model.user.UserRepository;
import example.domain.model.user.UserSummaries;
import example.infrastructure.datasource.sequencer.SequencerMapper;

@Repository
public class UserDatasource implements UserRepository {
    UserMapper mapper;
    SequencerMapper sequencer;
    
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
        mapper.registerUser(user);
        Long mailAddressId = sequencer.nextVal();
        mapper.registerMailAddress(mailAddressId, user);
        mapper.registerMailAddressMapper(user.identifier(), mailAddressId);
    }

    @Override
    public void update(User user) {
        mapper.delete(user);
        mapper.registerUser(user);
    }

    @Override
    public void delete(User user) {
        mapper.delete(user);
    }

    public UserDatasource(UserMapper mapper, SequencerMapper sequencer) {
        this.mapper = mapper;
        this.sequencer = sequencer;
    }
}
