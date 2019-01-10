package example.infrastructure.datasource.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.EmploymentContract;
import example.domain.model.contract.EmploymentContracts;
import example.domain.model.contract.WageCondition;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContractDataSource implements ContractRepository {
    ContractMapper mapper;

    @Override
    public void registerHourlyWage(EmployeeNumber employeeNumber, Date applyDate, WageCondition wageCondition) {
        mapper.deleteContractData(employeeNumber, applyDate);

        Integer hourlyWageId = mapper.newHourlyWageIdentifier();
        mapper.insertContractHistory(employeeNumber, hourlyWageId, applyDate, wageCondition);
        mapper.insertContract(employeeNumber, applyDate, wageCondition);
    }

    @Override
    public Contracts getContracts(EmployeeNumber employeeNumber) {
        List<HourlyWageData> list = mapper.selectContracts(employeeNumber);
        return new Contracts(list.stream()
                .map(HourlyWageData::toContract)
                .collect(Collectors.toList()));
    }

    @Override
    public EmploymentContracts findEmploymentContracts(ContractingEmployees contractingEmployees) {
        List<EmploymentContract> list = new ArrayList<>();
        for (Employee employee : contractingEmployees.list()) {
            list.add(new EmploymentContract(employee, getContracts(employee.employeeNumber())));
        }
        return new EmploymentContracts(list);
    }

    ContractDataSource(ContractMapper payrollMapper) {
        this.mapper = payrollMapper;
    }
}
