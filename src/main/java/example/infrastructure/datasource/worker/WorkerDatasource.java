package example.infrastructure.datasource.worker;

import example.application.repository.WorkerRepository;
import example.domain.model.worker.*;
import org.springframework.stereotype.Repository;

@Repository
public class WorkerDatasource implements WorkerRepository {
    WorkerMapper mapper;

    @Override
    public Worker choose(WorkerNumber workerNumber) {
        Worker worker = mapper.selectByWorkerNumber(workerNumber);
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
    public WorkerNumber registerNew() {
        WorkerNumber workerNumber = new WorkerNumber(mapper.newWorkerNumber());
        mapper.insertWorker(workerNumber);
        return workerNumber;
    }

    @Override
    public void registerName(WorkerNumber workerNumber, Name name) {
        Long nameId = mapper.newWorkerNameIdentifier();
        mapper.insertName(nameId, workerNumber, name);
        mapper.deleteNameMapper(workerNumber);
        mapper.insertNameMapper(workerNumber, nameId);
    }

    @Override
    public void registerMailAddress(WorkerNumber workerNumber, MailAddress mailAddress) {
        Long mailAddressId = mapper.newWorkerMailAddressIdentifier();
        mapper.insertMailAddress(mailAddressId, workerNumber, mailAddress);
        mapper.deleteMailAddressMapper(workerNumber);
        mapper.insertMailAddressMapper(workerNumber, mailAddressId);
    }

    @Override
    public void registerPhoneNumber(WorkerNumber workerNumber, PhoneNumber phoneNumber) {
        Long phoneNumberId = mapper.newWorkerPhoneNumberIdentifier();
        mapper.insertPhoneNumber(phoneNumberId, workerNumber, phoneNumber);
        mapper.deletePhoneNumberMapper(workerNumber);
        mapper.insertPhoneNumberMapper(workerNumber, phoneNumberId);
    }

    @Override
    public void registerExpireContract(Worker worker) {
        mapper.insertExpireContract(worker);
    }

    public WorkerDatasource(WorkerMapper mapper) {
        this.mapper = mapper;
    }
}
