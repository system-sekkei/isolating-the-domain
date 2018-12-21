package example.infrastructure.datasource.contract;

import example.domain.model.contract.*;
import example.domain.type.date.Date;

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
                new Date(startDate),
                new Date(endDate),
                new HourlyWageContract(
                        new HourlyWage(hourlyWage),
                        new OverTimeExtraRate(overTimeExtraRate),
                        new MidnightExtraRate(midnightExtraRate)
                )
        );
    }
}
