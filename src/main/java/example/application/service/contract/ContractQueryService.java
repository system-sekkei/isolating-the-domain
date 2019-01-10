package example.application.service.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.EmploymentContracts;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Service;

/**
 * 契約参照サービス
 */
@Service
public class ContractQueryService {
    ContractRepository contractRepository;

    /**
     * 契約取得
     */
    public Contracts getContracts(EmployeeNumber employeeNumber) {
        return contractRepository.getContracts(employeeNumber);
    }

    /**
     * 従業員契約一覧
     */
    public EmploymentContracts findEmployeeContracts(ContractingEmployees contractingEmployees) {
        return contractRepository.findEmploymentContracts(contractingEmployees);
    }

    ContractQueryService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
}
