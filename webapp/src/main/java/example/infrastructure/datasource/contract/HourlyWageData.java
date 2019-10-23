package example.infrastructure.datasource.contract;

import example.domain.model.contract.ContractWage;
import example.domain.model.contract.ContractEffectiveDate;
import example.domain.model.legislation.NightExtraRate;
import example.domain.model.wage.HourlyWage;
import example.domain.model.wage.WageCondition;
import example.domain.model.legislation.OverTimeExtraRate;
import example.domain.type.date.Date;

import java.time.LocalDate;

public class HourlyWageData {
    LocalDate startDate;
    Integer hourlyWage;
    Integer overTimeExtraRate;
    Integer nightExtraRate;

    ContractWage toContract() {
        return new ContractWage(new ContractEffectiveDate(new Date(startDate)), toHourlyWageContract());
    }

    WageCondition toHourlyWageContract() {
        return new WageCondition(
                new HourlyWage(hourlyWage),
                new OverTimeExtraRate(overTimeExtraRate),
                new NightExtraRate(nightExtraRate)
        );
    }
}
