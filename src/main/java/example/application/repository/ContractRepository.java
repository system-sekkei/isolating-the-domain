package example.application.repository;

import example.domain.model.contract.Contract;
import example.domain.model.contract.ContractHistory;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

public interface ContractRepository {
    void registerHourlyWage(WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage);

    HourlyWage getHourlyWage(WorkerNumber workerNumber, Date workDay);

    Contracts getContracts(WorkerNumber workerNumber, Date startDate, Date endDate);

    void registerHourlyWage2(WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage);

    void stopHourlyWageContract(WorkerNumber workerNumber, Date lastDate);

    Contracts getContracts2(WorkerNumber workerNumber, Date startDate, Date endDate);

    Contract getContract(WorkerNumber workerNumber, Date date);

    ContractHistory getContractHistory(WorkerNumber workerNumber);
}
