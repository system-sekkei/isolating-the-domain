package example.application.service.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.ContractWages;
import example.domain.model.contract.Contracts;
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
    public ContractWages getContractWages(EmployeeNumber employeeNumber) {
        return contractRepository.getContractWages(employeeNumber);
    }

    /**
     * 従業員契約一覧
     */
    public Contracts findContracts(ContractingEmployees contractingEmployees) {
        return contractRepository.findContracts(contractingEmployees);
    }

    ContractQueryService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
}
