package example.domain.model.contract;

import example.domain.model.labour_standards_law.ExtraPay;
import example.domain.type.date.Date;

import java.math.BigDecimal;
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

    public BigDecimal overTimeExtraPyRate() {
        //TODO
        return ExtraPay.時間外.leastExtraPayRate().value();
    }

    public BigDecimal midnightExtraPayRate() {
        //TODO
        return ExtraPay.深夜.leastExtraPayRate().value();
    }

    public BigDecimal holidayExtraPayRate() {
        //TODO
        return ExtraPay.休日.leastExtraPayRate().value();
    }
}
