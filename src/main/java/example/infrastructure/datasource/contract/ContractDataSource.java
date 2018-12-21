package example.infrastructure.datasource.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.WageCondition;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContractDataSource implements ContractRepository {
    ContractMapper mapper;

    @Override
    public void registerHourlyWage(WorkerNumber workerNumber, Date applyDate, WageCondition wageCondition) {
        stopHourlyWageContract(workerNumber, applyDate.previousDay());
        mapper.deleteFeatureContract(workerNumber, applyDate);
        Integer hourlyWageId = mapper.newHourlyWageIdentifier();
        mapper.registerHourlyWage(workerNumber, hourlyWageId, applyDate, wageCondition);
        mapper.insertContract(workerNumber, applyDate, Date.distantFuture(), wageCondition);
    }

    public void stopHourlyWageContract(WorkerNumber workerNumber, Date stopDate) {
        HourlyWageData hourlyWageData = mapper.selectHourlyWageData(workerNumber, stopDate);
        if (hourlyWageData == null) return;
        mapper.deleteContractData(workerNumber, hourlyWageData.startDate(), hourlyWageData.endDate());
        mapper.insertContract(workerNumber, hourlyWageData.startDate(), stopDate, hourlyWageData.toHourlyWageContract());
    }

    @Override
    public Contracts getContracts(WorkerNumber workerNumber) {
        List<HourlyWageData> list = mapper.selectContracts(workerNumber);
        return new Contracts(list.stream()
                .map(HourlyWageData::toContract)
                .collect(Collectors.toList()));
    }

    ContractDataSource(ContractMapper payrollMapper) {
        this.mapper = payrollMapper;
    }
}
