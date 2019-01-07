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
        Integer nameId = mapper.newWorkerNameIdentifier();
        mapper.insertWorkerNameHistory(nameId, workerNumber, name);
        mapper.deleteWorkerName(workerNumber);
        mapper.insertWorkerName(workerNumber, nameId, name);
    }

    @Override
    public void registerMailAddress(WorkerNumber workerNumber, MailAddress mailAddress) {
        Integer mailAddressId = mapper.newWorkerMailAddressIdentifier();
        mapper.insertWorkerMailAddressHistory(mailAddressId, workerNumber, mailAddress);
        mapper.deleteWorkerMailAddress(workerNumber);
        mapper.insertWorkerMailAddress(workerNumber, mailAddressId, mailAddress);
    }

    @Override
    public void registerPhoneNumber(WorkerNumber workerNumber, PhoneNumber phoneNumber) {
        Integer phoneNumberId = mapper.newWorkerPhoneNumberIdentifier();
        mapper.insertWorkerPhoneNumberHistory(phoneNumberId, workerNumber, phoneNumber);
        mapper.deleteWorkerPhoneNumber(workerNumber);
        mapper.insertWorkerPhoneNumber(workerNumber, phoneNumberId, phoneNumber);
    }

    @Override
    public void registerInspireContract(WorkerNumber workerNumber) {
        mapper.insertInspireContract(workerNumber);
    }

    @Override
    public void registerExpireContract(Worker worker) {
        mapper.deleteInspireContract(worker.workerNumber());
        mapper.insertExpireContract(worker.workerNumber());
    }

    public WorkerDatasource(WorkerMapper mapper) {
        this.mapper = mapper;
    }
}
