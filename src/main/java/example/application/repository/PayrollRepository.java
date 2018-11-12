package example.application.repository;

import example.domain.model.payroll.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

public interface PayrollRepository {
    void registerHourlyWage(WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage);

    HourlyWage getHourlyWage(WorkerNumber workerNumber, Date workDay);
}
