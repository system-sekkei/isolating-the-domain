package example.infrastructure.datasource.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.*;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContractDataSource implements ContractRepository {
    ContractMapper mapper;

    @Override
    public void registerHourlyWage(Employee employee, ContractEffectiveDate effectiveDate, WageCondition wageCondition) {
        EmployeeNumber employeeNumber = employee.employeeNumber();
        mapper.deleteContractData(employeeNumber, effectiveDate);

        Integer hourlyWageId = mapper.newHourlyWageIdentifier();
        mapper.insertContractHistory(employeeNumber, hourlyWageId, effectiveDate, wageCondition);
        mapper.insertContract(employeeNumber, effectiveDate, wageCondition);
    }

    @Override
    public ContractWages getContractWages(Employee employee) {
        List<ContractWage> list = mapper.selectContracts(employee.employeeNumber());
        return new ContractWages(list);
    }

    @Override
    public Contracts findContracts(ContractingEmployees contractingEmployees) {
        List<Contract> list = new ArrayList<>();
        for (Employee employee : contractingEmployees.list()) {
            list.add(new Contract(employee, getContractWages(employee)));
        }
        return new Contracts(list);
    }

    ContractDataSource(ContractMapper payrollMapper) {
        this.mapper = payrollMapper;
    }
}
