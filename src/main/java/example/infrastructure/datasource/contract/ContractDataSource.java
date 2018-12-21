package example.infrastructure.datasource.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.Contract;
import example.domain.model.contract.ContractHistory;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContractDataSource implements ContractRepository {
    ContractMapper mapper;

    @Override
    public void registerHourlyWage(WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage) {
        mapper.deleteFeatureContract(workerNumber, applyDate);
        Integer hourlyWageId = mapper.newHourlyWageIdentifier();
        mapper.registerHourlyWage(workerNumber, hourlyWageId, applyDate, hourlyWage, 25, 35);
        mapper.insertContract(workerNumber, applyDate, getEndDate(workerNumber, applyDate), hourlyWage, 25, 35);
    }

    public void stopHourlyWageContract(WorkerNumber workerNumber, Date stopDate) {
        HourlyWageData hourlyWageData = mapper.selectHourlyWageData(workerNumber, stopDate);
        if (hourlyWageData == null) return;
        mapper.deleteContractData(workerNumber, hourlyWageData.startDate(), hourlyWageData.endDate());
        mapper.insertContract(workerNumber, hourlyWageData.startDate(), stopDate, hourlyWageData.hourlyWage(), 25, 35);
    }

    @Override
    public ContractHistory getContractHistory(WorkerNumber workerNumber) {
        Contracts contracts = getContracts(workerNumber, new Date(LocalDate.of(1, 1, 1)),
                new Date(LocalDate.of(9999, 12, 31)));
        return new ContractHistory(contracts.value());
    }

    @Override
    public Contracts getContracts(WorkerNumber workerNumber, Date startDate, Date endDate) {
        List<HourlyWageData> list = mapper.getContracts(workerNumber, startDate, endDate);
        return new Contracts(list.stream().map(cd2 -> new Contract(cd2.startDate(), cd2.endDate(), cd2.hourlyWage())).collect(Collectors.toList()));
    }

    private Date getEndDate(WorkerNumber workerNumber, Date date) {
        return new Date(LocalDate.of(9999, 12, 31));
    }

    ContractDataSource(ContractMapper payrollMapper) {
        this.mapper = payrollMapper;
    }
}
