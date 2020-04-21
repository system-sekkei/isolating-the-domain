package example.domain.model.contract;

import example.domain.model.contract.daysoff.ContractDaysOff;
import example.domain.model.contract.hours.ContractBreakTime;
import example.domain.model.contract.wage.ContractWages;

/**
 * 労働条件
 */
public class ContractCondition {
    ContractWages contractWages;
    ContractBreakTime contractBreakTime;
    ContractDaysOff contractDaysOff;

    public ContractCondition(ContractWages contractWages, ContractBreakTime contractBreakTime, ContractDaysOff contractDaysOff) {
        this.contractWages = contractWages;
        this.contractBreakTime = contractBreakTime;
        this.contractDaysOff = contractDaysOff;
    }
}
