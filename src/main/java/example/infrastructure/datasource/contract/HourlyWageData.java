package example.infrastructure.datasource.contract;

import example.domain.model.contract.Contract;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.WageCondition;
import example.domain.model.labour_standards_law.MidnightExtraRate;
import example.domain.model.labour_standards_law.OverTimeExtraRate;
import example.domain.type.date.Date;
import example.domain.type.date.DateRange;

import java.time.LocalDate;

public class HourlyWageData {
    LocalDate startDate;
    LocalDate endDate;
    Integer hourlyWage;
    Integer overTimeExtraRate;
    Integer midnightExtraRate;

    Date startDate() {
        return new Date(startDate);
    }

    Date endDate() {
        return new Date(endDate);
    }

    HourlyWage hourlyWage() {
        return new HourlyWage(hourlyWage);
    }

    Contract toContract() {
        return new Contract(
                new DateRange(new Date(startDate), new Date(endDate)),
                toHourlyWageContract()
        );
    }

    WageCondition toHourlyWageContract() {
        return new WageCondition(
                new HourlyWage(hourlyWage),
                new OverTimeExtraRate(overTimeExtraRate),
                new MidnightExtraRate(midnightExtraRate)
        );
    }
}
