package example.infrastructure.datasource.worker;

import example.domain.model.worker.*;
import org.springframework.stereotype.Repository;

@Repository
public class WorkerDatasource implements WorkerRepository {
    WorkerMapper mapper;

    @Override
    public Worker findBy(WorkerIdentifier id) {
        Worker worker = mapper.findBy(id);
        if (worker == null) {
            throw new UserNotFoundException();
        }
        return worker;
    }

    @Override
    public Workers list() {
        return new Workers(mapper.list());
    }

    @Override
    public Worker register(UserCandidate userCandidate) {
        WorkerIdentifier workerIdentifier = new WorkerIdentifier(mapper.newWorkerIdentifier());
        mapper.registerWorker(workerIdentifier);
        updateName(workerIdentifier, userCandidate.name());
        updateMailAddress(workerIdentifier, userCandidate.mailAddress());
        updatePhoneNumber(workerIdentifier, userCandidate.phoneNumber());
        return findBy(workerIdentifier);
    }

    @Override
    public void updateName(WorkerIdentifier identifier, Name name) {
        Long nameId = mapper.newWorkerNameIdentifier();
        mapper.registerName(nameId, identifier, name);
        mapper.deleteNameMapper(identifier);
        mapper.registerNameMapper(identifier, nameId);
    }

    @Override
    public void updateMailAddress(WorkerIdentifier identifier, MailAddress mailAddress) {
        Long mailAddressId = mapper.newWorkerMailAddressIdentifier();
        mapper.registerMailAddress(mailAddressId, identifier, mailAddress);
        mapper.deleteMailAddressMapper(identifier);
        mapper.registerMailAddressMapper(identifier, mailAddressId);
    }

    @Override
    public void updatePhoneNumber(WorkerIdentifier identifier, PhoneNumber phoneNumber) {
        Long phoneNumberId = mapper.newWorkerPhoneNumberIdentifier();
        mapper.registerPhoneNumber(phoneNumberId, identifier, phoneNumber);
        mapper.deletePhoneNumberMapper(identifier);
        mapper.registerPhoneNumberMapper(identifier, phoneNumberId);
    }

    @Override
    public void delete(Worker worker) {
        mapper.delete(worker);
    }

    public WorkerDatasource(WorkerMapper mapper) {
        this.mapper = mapper;
    }
}
