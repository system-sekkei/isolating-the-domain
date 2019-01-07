package example.infrastructure.datasource.contract;

import example.domain.model.contract.Contract;
import example.domain.model.contract.ContractStartingDate;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.WageCondition;
import example.domain.model.legislation.MidnightExtraRate;
import example.domain.model.legislation.OverTimeExtraRate;
import example.domain.type.date.Date;

import java.time.LocalDate;

public class HourlyWageData {
    LocalDate startDate;
    Integer hourlyWage;
    Integer overTimeExtraRate;
    Integer midnightExtraRate;

    Contract toContract() {
        return new Contract(new ContractStartingDate(new Date(startDate)), toHourlyWageContract());
    }

    WageCondition toHourlyWageContract() {
        return new WageCondition(
                new HourlyWage(hourlyWage),
                new OverTimeExtraRate(overTimeExtraRate),
                new MidnightExtraRate(midnightExtraRate)
        );
    }
}
