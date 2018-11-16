package example.infrastructure.datasource.payroll;

import example.application.repository.PayrollRepository;
import example.domain.model.contract.DailyHourlyWage;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.MonthlyHourlyWages;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public MonthlyHourlyWages getMonthlyHourlyWage(WorkerNumber workerNumber, YearMonth yearMonth) {
        List<Date> days = yearMonth.days();
        List<DailyHourlyWage> wages = days.stream().map(day -> new DailyHourlyWage(day, getHourlyWage(workerNumber, day))).collect(Collectors.toList());
        return new MonthlyHourlyWages(wages);
    }

    PayrollDataSource(PayrollMapper payrollMapper) {
        this.mapper = payrollMapper;
    }
}
