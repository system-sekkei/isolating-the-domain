package example.infrastructure.datasource.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.ContractWages;
import example.domain.model.contract.Contract;
import example.domain.model.contract.Contracts;
import example.domain.model.wage.WageCondition;
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
    public ContractWages getContractWages(EmployeeNumber employeeNumber) {
        List<HourlyWageData> list = mapper.selectContracts(employeeNumber);
        return new ContractWages(list.stream()
                .map(HourlyWageData::toContract)
                .collect(Collectors.toList()));
    }

    @Override
    public Contracts findContracts(ContractingEmployees contractingEmployees) {
        List<Contract> list = new ArrayList<>();
        for (Employee employee : contractingEmployees.list()) {
            list.add(new Contract(employee, getContractWages(employee.employeeNumber())));
        }
        return new Contracts(list);
    }

    ContractDataSource(ContractMapper payrollMapper) {
        this.mapper = payrollMapper;
    }
}
