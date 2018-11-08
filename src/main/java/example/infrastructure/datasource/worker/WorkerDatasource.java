package example.infrastructure.datasource.worker;

import example.domain.model.worker.*;
import org.springframework.stereotype.Repository;

@Repository
public class WorkerDatasource implements WorkerRepository {
    WorkerMapper mapper;

    @Override
    public Worker choose(WorkerIdentifier workerIdentifier) {
        Worker worker = mapper.selectByIdentifier(workerIdentifier);
        if (worker == null) {
            throw new WorkerNotFoundException();
        }
        return worker;
    }

    @Override
    public ContractingWorkers findUnderContracts() {
        return new ContractingWorkers(mapper.selectContracts());
    }

    @Override
    public WorkerIdentifier registerNew() {
        WorkerIdentifier workerIdentifier = new WorkerIdentifier(mapper.newWorkerIdentifier());
        mapper.insertWorker(workerIdentifier);
        return workerIdentifier;
    }

    @Override
    public void registerName(WorkerIdentifier workerIdentifier, Name name) {
        Long nameId = mapper.newWorkerNameIdentifier();
        mapper.insertName(nameId, workerIdentifier, name);
        mapper.deleteNameMapper(workerIdentifier);
        mapper.insertNameMapper(workerIdentifier, nameId);
    }

    @Override
    public void registerMailAddress(WorkerIdentifier workerIdentifier, MailAddress mailAddress) {
        Long mailAddressId = mapper.newWorkerMailAddressIdentifier();
        mapper.insertMailAddress(mailAddressId, workerIdentifier, mailAddress);
        mapper.deleteMailAddressMapper(workerIdentifier);
        mapper.insertMailAddressMapper(workerIdentifier, mailAddressId);
    }

    @Override
    public void registerPhoneNumber(WorkerIdentifier workerIdentifier, PhoneNumber phoneNumber) {
        Long phoneNumberId = mapper.newWorkerPhoneNumberIdentifier();
        mapper.insertPhoneNumber(phoneNumberId, workerIdentifier, phoneNumber);
        mapper.deletePhoneNumberMapper(workerIdentifier);
        mapper.insertPhoneNumberMapper(workerIdentifier, phoneNumberId);
    }

    @Override
    public void registerExpireContract(Worker worker) {
        mapper.insertExpireContract(worker);
    }

    public WorkerDatasource(WorkerMapper mapper) {
        this.mapper = mapper;
    }
}
