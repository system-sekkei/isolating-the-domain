package example.application.repository;


import example.domain.model.contract.ContractHistory;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.HourlyWageContract;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

public interface ContractRepository {
    Contracts getContracts(WorkerNumber workerNumber, Date startDate, Date endDate);

    void stopHourlyWageContract(WorkerNumber workerNumber, Date lastDate);

    ContractHistory getContractHistory(WorkerNumber workerNumber);

    void registerHourlyWage(WorkerNumber workerNumber, Date startDate, HourlyWageContract hourlyWageContract);
}
