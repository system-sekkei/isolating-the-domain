package example.application.service;

import example.application.coordinator.employee.EmployeeRecordCoordinator;
import example.application.service.contract.ContractQueryService;
import example.application.service.contract.ContractRecordService;
import example.application.service.employee.EmployeeQueryService;
import example.domain.model.contract.ContractWages;
import example.domain.model.contract.OverLegalTimeExtraRate;
import example.domain.model.contract.OverTimeExtraRate;
import example.domain.model.employee.*;
import example.domain.model.contract.NightExtraRate;
import example.domain.model.contract.BaseHourlyWage;
import example.domain.model.contract.WageCondition;
import example.domain.type.date.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContractWageRecordServiceTest {
    @Autowired
    EmployeeRecordCoordinator employeeRecordCoordinator;
    @Autowired
    EmployeeQueryService employeeQueryService;
    @Autowired
    ContractRecordService sutRecord;
    @Autowired
    ContractQueryService sutQuery;

    @Test
    void test() {
        EmployeeNumber employeeNumber = employeeRecordCoordinator.register(
                new EmployeeToRegister(new Name("any"), new MailAddress("any"), new PhoneNumber("any")));

        登録直後の従業員は時給を持たない(employeeNumber);
        時給が登録できる(employeeNumber);
        指定日以降の時給を登録できる(employeeNumber);
        指定日以降次の指定があるまでの時給を登録できる(employeeNumber);
        同じ指定日の時給を上書きできる(employeeNumber);
    }

    void 登録直後の従業員は時給を持たない(EmployeeNumber employeeNumber) {
        Employee employee = employeeQueryService.choose(employeeNumber);
        ContractWages history = sutQuery.getContractWages(employee);
        assertTrue(history.list().isEmpty());
    }

    void 時給が登録できる(EmployeeNumber employeeNumber) {
        Employee employee = employeeQueryService.choose(employeeNumber);

        Date effectiveDate1 = Date.from("2018-12-12");
        updateHourlyWageContract(employee, effectiveDate1, new BaseHourlyWage(800));

        ContractWages history1 = sutQuery.getContractWages(employee);
        assertEquals(1, history1.list().size());
        assertAll(
                () -> assertEquals(effectiveDate1.value(), history1.list().get(0).effectiveDate().value().value()),
                () -> assertEquals(800, history1.list().get(0).baseHourlyWage().toBigDecimal().intValue())
        );
    }

    void 指定日以降の時給を登録できる(EmployeeNumber employeeNumber) {
        Employee employee = employeeQueryService.choose(employeeNumber);

        Date effectiveDate2 = Date.from("2018-12-22");
        updateHourlyWageContract(employee, effectiveDate2, new BaseHourlyWage(850));
        ContractWages history2 = sutQuery.getContractWages(employee);
        assertEquals(2, history2.list().size());
        assertAll(
                () -> assertEquals(effectiveDate2.value(), history2.list().get(0).effectiveDate().value().value()),
                () -> assertEquals(850, history2.list().get(0).baseHourlyWage().toBigDecimal().intValue()),
                () -> assertEquals(800, history2.list().get(1).baseHourlyWage().toBigDecimal().intValue())
        );
    }

    void 指定日以降次の指定があるまでの時給を登録できる(EmployeeNumber employeeNumber) {
        Employee employee = employeeQueryService.choose(employeeNumber);

        Date effectiveDate3 = Date.from("2018-12-17");
        updateHourlyWageContract(employee, effectiveDate3, new BaseHourlyWage(830));
        ContractWages history3 = sutQuery.getContractWages(employee);
        assertEquals(3, history3.list().size());
        assertAll(
                () -> assertEquals(850, history3.list().get(0).baseHourlyWage().toBigDecimal().intValue()),

                () -> assertEquals(effectiveDate3.value(), history3.list().get(1).effectiveDate().value().value()),
                () -> assertEquals(830, history3.list().get(1).baseHourlyWage().toBigDecimal().intValue()),

                () -> assertEquals(800, history3.list().get(2).baseHourlyWage().toBigDecimal().intValue())
        );
    }

    void 同じ指定日の時給を上書きできる(EmployeeNumber employeeNumber) {
        Employee employee = employeeQueryService.choose(employeeNumber);

        Date effectiveDate1 = Date.from("2018-12-12");
        updateHourlyWageContract(employee, effectiveDate1, new BaseHourlyWage(1000));

        ContractWages history = sutQuery.getContractWages(employee);
        assertEquals(3, history.list().size());
        assertAll(
                () -> assertEquals(850, history.list().get(0).baseHourlyWage().toBigDecimal().intValue()),
                () -> assertEquals(830, history.list().get(1).baseHourlyWage().toBigDecimal().intValue()),
                () -> assertEquals(1000, history.list().get(2).baseHourlyWage().toBigDecimal().intValue())
        );
    }

    private void updateHourlyWageContract(Employee employee, Date effectiveDate, BaseHourlyWage baseHourlyWage) {
        OverTimeExtraRate overTimeExtraRate = new OverTimeExtraRate(null, new OverLegalTimeExtraRate(25), null, null, null, new NightExtraRate(35));
        sutRecord.registerHourlyWage(employee, effectiveDate, new WageCondition(baseHourlyWage, overTimeExtraRate));
    }
}
