package example.application.repository;


import example.domain.model.contract.Contracts;
import example.domain.model.contract.WageCondition;
import example.domain.model.contract.WorkerContracts;
import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

public interface ContractRepository {
    Contracts getContracts(WorkerNumber workerNumber);

    void registerHourlyWage(WorkerNumber workerNumber, Date startDate, WageCondition wageCondition);

    WorkerContracts findWorkerContracts(ContractingWorkers contractingWorkers);
}
