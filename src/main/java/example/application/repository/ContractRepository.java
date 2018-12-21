package example.application.repository;


import example.domain.model.contract.Contract;
import example.domain.model.contract.ContractHistory;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

public interface ContractRepository {
    Contracts getContracts(WorkerNumber workerNumber, Date startDate, Date endDate);

    void stopHourlyWageContract(WorkerNumber workerNumber, Date lastDate);

    Contract getContract(WorkerNumber workerNumber, Date date);

    ContractHistory getContractHistory(WorkerNumber workerNumber);

    void registerHourlyWage(WorkerNumber workerNumber, Date startDate, HourlyWage hourlyWage);
}
