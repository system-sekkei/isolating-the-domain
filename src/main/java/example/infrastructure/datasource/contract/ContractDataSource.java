package example.infrastructure.datasource.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.Contract;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.date.DateRange;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class ContractDataSource implements ContractRepository {
    ContractMapper mapper;

    @Override
    public void registerHourlyWage(WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage) {
        Integer hourlyWageId = mapper.newHourlyWageIdentifier();
        mapper.registerHourlyWage(workerNumber, hourlyWageId, applyDate, hourlyWage);
    }

    @Override
    public Contracts getContracts(WorkerNumber workerNumber, Date startDate, Date endDate) {
        DateRange range = new DateRange(startDate, endDate);
        List<Date> days = range.days();
        SortedMap<LocalDate, ContractHistoryData> map = new TreeMap<>();
        //TODO 時給無いときどうしよう
        ContractHistoryData noContract = new ContractHistoryData() {{
            id = -1;
            hourlyWage = 0;
        }};
        for (Date date : days) {
            try {
                map.put(date.value(), getContractData(workerNumber, date));
            } catch (HourlyWageNotFoundException e) {
                map.put(date.value(), noContract);
            }
        }
        List<Contract> ret = new ArrayList<>();
        LocalDate s = startDate.value();
        Integer lastId = map.get(s).id;
        for (Map.Entry<LocalDate, ContractHistoryData> entry : map.entrySet()) {
            if (entry.getValue().id.equals(lastId)) {
                continue;
            }
            ret.add(new Contract(new Date(s), new Date(entry.getKey().minusDays(1L)), new HourlyWage(map.get(s).hourlyWage)));
            s = entry.getKey();
            lastId = entry.getValue().id;
        }
        ret.add(new Contract(new Date(s), endDate, new HourlyWage(map.get(s).hourlyWage)));
        return new Contracts(ret);
    }

    @Override
    public void stopHourlyWageContract(WorkerNumber workerNumber, Date stopDate) {
        HourlyWageData hourlyWageData = mapper.selectHourlyWageData(workerNumber, stopDate);
        if (hourlyWageData == null) return;
        mapper.deleteContractData(workerNumber, hourlyWageData.startDate(), hourlyWageData.endDate());
        mapper.insertContract(workerNumber, hourlyWageData.startDate(), stopDate, hourlyWageData.hourlyWage());
    }

    private ContractHistoryData getContractData(WorkerNumber workerNumber, Date workDay) {
        List<ContractHistoryData> contracts = mapper.getContractHistoryData(workerNumber, workDay);
        return contracts.stream().findFirst().orElseThrow(HourlyWageNotFoundException::new);
    }

    ContractDataSource(ContractMapper payrollMapper) {
        this.mapper = payrollMapper;
    }
}
