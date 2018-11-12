package example.infrastructure.datasource.payroll;

import example.application.repository.PayrollRepository;
import example.domain.model.payroll.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PayrollDataSource implements PayrollRepository {
    PayrollMapper mapper;

    @Override
    public void registerHourlyWage(WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage) {
        Integer hourlyWageId = mapper.newHourlyWageIdentifier();
        mapper.registerHourlyWage(workerNumber, hourlyWageId, applyDate, hourlyWage);
    }

    @Override
    public HourlyWage getHourlyWage(WorkerNumber workerNumber, Date workDay) {
        List<HourlyWage> hourlyWages = mapper.getHourlyWage(workerNumber, workDay);
        return hourlyWages.stream().findFirst().orElseThrow(() -> new HourlyWageNotFoundException());
    }

    PayrollDataSource(PayrollMapper payrollMapper) {
        this.mapper = payrollMapper;
    }
}
