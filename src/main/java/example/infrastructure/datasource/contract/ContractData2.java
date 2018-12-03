package example.infrastructure.datasource.contract;

import example.domain.model.contract.HourlyWage;
import example.domain.type.date.Date;

import java.time.LocalDate;

public class ContractData2 {
    LocalDate startDate;
    LocalDate endDate;
    Integer hourlyWage;

    Date startDate() { return new Date(startDate);}
    Date endDate() { return new Date(endDate);}
    HourlyWage hourlyWage() { return new HourlyWage(hourlyWage); }
}
