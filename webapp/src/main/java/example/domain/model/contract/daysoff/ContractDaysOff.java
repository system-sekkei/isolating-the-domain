package example.domain.model.contract.daysoff;

import example.domain.type.date.Date;

/**
 * 契約上の休日
 */
public class ContractDaysOff {
    Date value;

    public ContractDaysOff(Date value) {
        this.value = value;
    }
}
