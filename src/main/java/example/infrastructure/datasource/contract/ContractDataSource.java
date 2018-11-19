package example.infrastructure.datasource.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.*;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.date.DateRange;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ContractDataSource implements ContractRepository {
    ContractMapper mapper;

    @Override
    public void registerHourlyWage(WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage) {
        Integer hourlyWageId = mapper.newHourlyWageIdentifier();
        mapper.registerHourlyWage(workerNumber, hourlyWageId, applyDate, hourlyWage);
    }

    @Override
    public HourlyWage getHourlyWage(WorkerNumber workerNumber, Date workDay) {
        return new HourlyWage(getContract(workerNumber, workDay).value);
    }

    @Override
    public MonthlyHourlyWages getMonthlyHourlyWage(WorkerNumber workerNumber, YearMonth yearMonth) {
        List<Date> days = yearMonth.days();
        List<DailyHourlyWage> wages = days.stream().map(day -> new DailyHourlyWage(day, getHourlyWage(workerNumber, day))).collect(Collectors.toList());
        return new MonthlyHourlyWages(wages);
    }

    @Override
    public Contracts getContracts(WorkerNumber workerNumber, Date startDate, Date endDate) {
        DateRange range = new DateRange(startDate, endDate);
        List<Date> days = range.days();
        SortedMap<LocalDate, ContractData> map = new TreeMap<>();
        for(Date date : days) {
            map.put(date.value(), getContract(workerNumber, date));
        }
        List<Contract> ret = new ArrayList<>();
        LocalDate s = startDate.value();
        Integer lastId = map.get(s).id;
        for(Map.Entry<LocalDate, ContractData> entry : map.entrySet()) {
            if(entry.getValue().id.equals(lastId)) {
               continue;
            }
            ret.add(new Contract(new Date(s), new Date(entry.getKey().minusDays(1L)), new HourlyWage(map.get(s).value)));
            s = entry.getKey();
            lastId = entry.getValue().id;
        }
        ret.add(new Contract(new Date(s), endDate, new HourlyWage(map.get(s).value)));
        return new Contracts(ret);
    }

    private ContractData getContract(WorkerNumber workerNumber, Date workDay) {
        List<ContractData> contracts = mapper.getContractData(workerNumber, workDay);
        return contracts.stream().findFirst().orElseThrow(() -> new HourlyWageNotFoundException());
    }

    ContractDataSource(ContractMapper payrollMapper) {
        this.mapper = payrollMapper;
    }
}
