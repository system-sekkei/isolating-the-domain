package example.infrastructure.datasource.contract;

import example.domain.model.contract.Contract;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.WageCondition;
import example.domain.model.labour_standards_law.MidnightExtraRate;
import example.domain.model.labour_standards_law.OverTimeExtraRate;
import example.domain.type.date.Date;

import java.time.LocalDate;

public class HourlyWageData {
    LocalDate startDate;
    Integer hourlyWage;
    Integer overTimeExtraRate;
    Integer midnightExtraRate;

    Date startDate() {
        return new Date(startDate);
    }

    HourlyWage hourlyWage() {
        return new HourlyWage(hourlyWage);
    }

    Contract toContract() {
        return new Contract(new Date(startDate), toHourlyWageContract());
    }

    WageCondition toHourlyWageContract() {
        return new WageCondition(
                new HourlyWage(hourlyWage),
                new OverTimeExtraRate(overTimeExtraRate),
                new MidnightExtraRate(midnightExtraRate)
        );
    }
}
