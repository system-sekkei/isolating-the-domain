package example.infrastructure.datasource.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.ContractHistory;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.HourlyWageContract;
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
    public void registerHourlyWage(WorkerNumber workerNumber, Date applyDate, HourlyWageContract hourlyWageContract) {
        stopHourlyWageContract(workerNumber, applyDate.previousDay());
        mapper.deleteFeatureContract(workerNumber, applyDate);
        Integer hourlyWageId = mapper.newHourlyWageIdentifier();
        mapper.registerHourlyWage(workerNumber, hourlyWageId, applyDate, hourlyWageContract);
        mapper.insertContract(workerNumber, applyDate, Date.distantFuture(), hourlyWageContract);
    }

    public void stopHourlyWageContract(WorkerNumber workerNumber, Date stopDate) {
        HourlyWageData hourlyWageData = mapper.selectHourlyWageData(workerNumber, stopDate);
        if (hourlyWageData == null) return;
        mapper.deleteContractData(workerNumber, hourlyWageData.startDate(), hourlyWageData.endDate());
        mapper.insertContract(workerNumber, hourlyWageData.startDate(), stopDate, hourlyWageData.toHourlyWageContract());
    }

    @Override
    public ContractHistory getContractHistory(WorkerNumber workerNumber) {
        Contracts contracts = getContracts(workerNumber, new Date(LocalDate.of(1, 1, 1)), Date.distantFuture());
        return new ContractHistory(contracts.value());
    }

    @Override
    public Contracts getContracts(WorkerNumber workerNumber, Date startDate, Date endDate) {
        List<HourlyWageData> list = mapper.getContracts(workerNumber, startDate, endDate);
        return new Contracts(list.stream()
                .map(HourlyWageData::toContract)
                .collect(Collectors.toList()));
    }

    ContractDataSource(ContractMapper payrollMapper) {
        this.mapper = payrollMapper;
    }
}
