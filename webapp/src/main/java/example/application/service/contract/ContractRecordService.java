package example.application.service.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.ContractEffectiveDate;
import example.domain.model.contract.WageCondition;
import example.domain.model.employee.Employee;
import org.springframework.stereotype.Service;

/**
 * 契約登録サービス
 */
@Service
public class ContractRecordService {
    ContractRepository contractRepository;

    ContractRecordService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * 時給登録
     */
    public void registerHourlyWage(Employee employee, ContractEffectiveDate effectiveDate, WageCondition wageCondition) {
        contractRepository.registerHourlyWage(employee, effectiveDate, wageCondition);
    }
}
