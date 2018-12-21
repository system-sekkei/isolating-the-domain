package example.application.repository;

import example.domain.model.contract.Contracts;
import example.domain.model.contract.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

public interface ContractRepository {
    void registerHourlyWage(WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage);

    Contracts getContracts(WorkerNumber workerNumber, Date startDate, Date endDate);

    void stopHourlyWageContract(WorkerNumber workerNumber, Date lastDate);
}
