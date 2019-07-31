package example.application.service;

import example.Application;
import example.application.service.contract.ContractQueryService;
import example.application.service.contract.ContractRecordService;
import example.application.service.employee.EmployeeRecordService;
import example.domain.model.contract.ContractWages;
import example.domain.model.wage.HourlyWage;
import example.domain.model.wage.WageCondition;
import example.domain.model.legislation.MidnightExtraRate;
import example.domain.model.legislation.OverTimeExtraRate;
import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
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
        Date applyDate1 = new Date(baseDate);
        HourlyWage wage1 = new HourlyWage(800);
        updateHourlyWageContract(number, applyDate1, wage1);
        ContractWages history1 = sutQuery.getContractWages(number);
        assertEquals(1, history1.list().size());
        assertAll(
                () -> assertEquals(applyDate1.value(), history1.list().get(0).startDate().value().value()),
                () -> assertEquals(800, history1.list().get(0).hourlyWage().value().intValue())
        );

        //2発目
        Date applyDate2 = new Date("2018-12-22");
        HourlyWage wage2 = new HourlyWage(850);
        updateHourlyWageContract(number, applyDate2, wage2);
        ContractWages history2 = sutQuery.getContractWages(number);
        assertEquals(2, history2.list().size());
        assertAll(
                () -> assertEquals(applyDate2.value(), history2.list().get(0).startDate().value().value()),
                () -> assertEquals(850, history2.list().get(0).hourlyWage().value().intValue()),
                () -> assertEquals(applyDate1.value(), history2.list().get(1).startDate().value().value()),
                () -> assertEquals(800, history2.list().get(1).hourlyWage().value().intValue())
        );

        //3発目（2件目よりも過去）
        Date applyDate3 = new Date("2018-12-17");
        HourlyWage wage3 = new HourlyWage(830);
        updateHourlyWageContract(number, applyDate3, wage3);
        ContractWages history3 = sutQuery.getContractWages(number);
        assertEquals(3, history3.list().size());
        assertAll(
                () -> assertEquals(applyDate2.value(), history3.list().get(0).startDate().value().value()),
                () -> assertEquals(850, history3.list().get(0).hourlyWage().value().intValue()),

                () -> assertEquals(applyDate3.value(), history3.list().get(1).startDate().value().value()),
                () -> assertEquals(830, history3.list().get(1).hourlyWage().value().intValue()),

                () -> assertEquals(applyDate1.value(), history3.list().get(2).startDate().value().value()),
                () -> assertEquals(800, history3.list().get(2).hourlyWage().value().intValue())
        );
    }

    private void updateHourlyWageContract(EmployeeNumber employeeNumber, Date applyDate, HourlyWage hourlyWage) {
        sutRecord.registerHourlyWage(employeeNumber, applyDate, new WageCondition(hourlyWage, new OverTimeExtraRate(25), new MidnightExtraRate(35)));
    }
}
