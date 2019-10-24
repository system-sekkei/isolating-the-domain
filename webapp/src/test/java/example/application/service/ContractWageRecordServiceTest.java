package example.application.service;

import example.application.service.contract.ContractQueryService;
import example.application.service.contract.ContractRecordService;
import example.application.service.employee.EmployeeRecordService;
import example.domain.model.contract.ContractWages;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.legislation.NightExtraRate;
import example.domain.model.legislation.OverTimeExtraRate;
import example.domain.model.wage.HourlyWage;
import example.domain.model.wage.WageCondition;
import example.domain.type.date.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ContractWageRecordServiceTest {
    @Autowired
    EmployeeRecordService employeeRecordService;
    @Autowired
    ContractRecordService sutRecord;
    @Autowired
    ContractQueryService sutQuery;

    @DisplayName("時給の登録参照が正しく出来ること")
    @Test
    void hourlyWage_io() {
        EmployeeNumber number = employeeRecordService.prepareNewContract();

        //一発目
        String baseDate = "2018-12-12";
        Date effectiveDate1 = new Date(baseDate);
        HourlyWage wage1 = new HourlyWage(800);
        updateHourlyWageContract(number, effectiveDate1, wage1);
        ContractWages history1 = sutQuery.getContractWages(number);
        assertEquals(1, history1.list().size());
        assertAll(
                () -> assertEquals(effectiveDate1.value(), history1.list().get(0).effectiveDate().value().value()),
                () -> assertEquals(800, history1.list().get(0).hourlyWage().value().intValue())
        );

        //2発目
        Date effectiveDate2 = new Date("2018-12-22");
        HourlyWage wage2 = new HourlyWage(850);
        updateHourlyWageContract(number, effectiveDate2, wage2);
        ContractWages history2 = sutQuery.getContractWages(number);
        assertEquals(2, history2.list().size());
        assertAll(
                () -> assertEquals(effectiveDate2.value(), history2.list().get(0).effectiveDate().value().value()),
                () -> assertEquals(850, history2.list().get(0).hourlyWage().value().intValue()),
                () -> assertEquals(effectiveDate1.value(), history2.list().get(1).effectiveDate().value().value()),
                () -> assertEquals(800, history2.list().get(1).hourlyWage().value().intValue())
        );

        //3発目（2件目よりも過去）
        Date effectiveDate3 = new Date("2018-12-17");
        HourlyWage wage3 = new HourlyWage(830);
        updateHourlyWageContract(number, effectiveDate3, wage3);
        ContractWages history3 = sutQuery.getContractWages(number);
        assertEquals(3, history3.list().size());
        assertAll(
                () -> assertEquals(effectiveDate2.value(), history3.list().get(0).effectiveDate().value().value()),
                () -> assertEquals(850, history3.list().get(0).hourlyWage().value().intValue()),

                () -> assertEquals(effectiveDate3.value(), history3.list().get(1).effectiveDate().value().value()),
                () -> assertEquals(830, history3.list().get(1).hourlyWage().value().intValue()),

                () -> assertEquals(effectiveDate1.value(), history3.list().get(2).effectiveDate().value().value()),
                () -> assertEquals(800, history3.list().get(2).hourlyWage().value().intValue())
        );
    }

    private void updateHourlyWageContract(EmployeeNumber employeeNumber, Date effectiveDate, HourlyWage hourlyWage) {
        sutRecord.registerHourlyWage(employeeNumber, effectiveDate, new WageCondition(hourlyWage, new OverTimeExtraRate(25), new NightExtraRate(35)));
    }
}
