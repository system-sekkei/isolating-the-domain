package example.infrastructure.datasource.user;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import example.domain.model.user.User;
import example.domain.model.user.UserCandidate;
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
    public UserCandidate prototype() {
        return new UserCandidate();
    }

    @Override
    public User register(UserCandidate userCandidate) {
    	UserIdentifier userId = new UserIdentifier(UUID.randomUUID().toString());
    	User user = userCandidate.toUser(userId);
        mapper.registerUser(user);
        update(user);
        return user;
    }

	void updateGender(User user) {
		Long genderId = sequencer.nextVal();
        mapper.registerGender(genderId, user);
        mapper.deleteGenderMapper(user.identifier());
        mapper.registerGenderMapper(user.identifier(), genderId);
	}

	void updateDateOfBirth(User user) {
		Long dateOfBirthId = sequencer.nextVal();
        mapper.registerDateOfBirth(dateOfBirthId, user);
        mapper.deleteDateOfBirthMapper(user.identifier());
        mapper.registerDateOfBirthMapper(user.identifier(), dateOfBirthId);
	}

	void updatePhoneNumber(User user) {
		Long phoneNumberId = sequencer.nextVal();
        mapper.registerPhoneNumber(phoneNumberId, user);
        mapper.deletePhoneNumberMapper(user.identifier());
        mapper.registerPhoneNumberMapper(user.identifier(), phoneNumberId);
	}

	void updateName(User user) {
		Long nameId = sequencer.nextVal();
        mapper.registerName(nameId, user);
        mapper.deleteNameMapper(user.identifier());
        mapper.registerNameMapper(user.identifier(), nameId);
	}

	void updateMailAddress(User user) {
		Long mailAddressId = sequencer.nextVal();
        mapper.registerMailAddress(mailAddressId, user);
        mapper.deleteMailAddressMapper(user.identifier());
        mapper.registerMailAddressMapper(user.identifier(), mailAddressId);
	}

    @Override
    public void update(User user) {
        updateMailAddress(user);
        updateName(user);
        updatePhoneNumber(user);
        updateDateOfBirth(user);
        updateGender(user);
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
