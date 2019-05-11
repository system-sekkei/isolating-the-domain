package example.infrastructure.datasource.contract;

import example.domain.model.contract.ContractWage;
import example.domain.model.contract.ContractStartingDate;
import example.domain.model.wage.HourlyWage;
import example.domain.model.wage.WageCondition;
import example.domain.model.legislation.MidnightExtraRate;
import example.domain.model.legislation.OverTimeExtraRate;
import example.domain.type.date.Date;

import java.time.LocalDate;

public class HourlyWageData {
    LocalDate startDate;
    Integer hourlyWage;
    Integer overTimeExtraRate;
    Integer midnightExtraRate;

    ContractWage toContract() {
        return new ContractWage(new ContractStartingDate(new Date(startDate)), toHourlyWageContract());
    }

    WageCondition toHourlyWageContract() {
        return new WageCondition(
                new HourlyWage(hourlyWage),
                new OverTimeExtraRate(overTimeExtraRate),
                new MidnightExtraRate(midnightExtraRate)
        );
    }
}
