package example.domain.model.contract;

import example.domain.type.date.Date;
import example.domain.type.date.DateRange;

import java.util.Optional;

/**
 * 雇用契約
 */
public class Contract {
    HourlyWage hourlyWage;
    Date startDate;
    Optional<Date> endDate;

    public Contract(Date startDate, Date endDate, HourlyWage hourlyWage) {
        this(startDate, Optional.of(endDate), hourlyWage);
    }

    public Contract(Date startDate, HourlyWage hourlyWage) {
        this(startDate, Optional.empty(), hourlyWage);
    }

    private Contract(Date startDate, Optional<Date> endDate, HourlyWage hourlyWage) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.hourlyWage = hourlyWage;
    }

    public HourlyWage hourlyWage() { return hourlyWage; }
    public Date startDate() { return startDate; }
    public Optional<Date> endDate() { return endDate; }

    public DateRange period() {
        return new DateRange(startDate(), endDate().get()); //TODO 今のところend無しは無し
    }

    public HourlyWage overTimeHourlyWage() {
        //TODO
        return hourlyWage().withExtraRate(new ExtraPayRate("0.25"));
    }

    public HourlyWage midnightExtraPayRate() {
        //TODO
        return hourlyWage().withExtraRate(new ExtraPayRate("0.35"));
    }

    public HourlyWage holidayExtraPayRate() {
        //TODO
        return hourlyWage().withExtraRate(new ExtraPayRate("0.25"));
    }
}
