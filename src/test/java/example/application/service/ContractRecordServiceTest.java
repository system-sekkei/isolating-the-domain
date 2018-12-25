package example.application.service;

import example.Application;
import example.application.service.contract.ContractQueryService;
import example.application.service.contract.ContractRecordService;
import example.application.service.worker.WorkerRecordService;
import example.domain.model.contract.*;
import example.domain.model.labour_standards_law.MidnightExtraRate;
import example.domain.model.labour_standards_law.OverTimeExtraRate;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class ContractRecordServiceTest {
    @Autowired
    WorkerRecordService workerRecordService;
    @Autowired
    ContractRecordService sutRecord;
    @Autowired
    ContractQueryService sutQuery;

    @DisplayName("時給の登録参照が正しく出来ること")
    @Test
    void hourlyWage_io() {
        WorkerNumber number = workerRecordService.prepareNewContract();

        LocalDate infinite = Date.distantFuture().value();
        //一発目
        LocalDate now = LocalDate.now();
        Date applyDate1 = new Date(now);
        HourlyWage wage1 = new HourlyWage(800);
        updateHourlyWageContract(number, applyDate1, wage1);
        ContractHistory history1 = sutQuery.getContractHistory(number);
        assertEquals(1, history1.list().size());
        assertAll(
                () -> assertEquals(now, history1.list().get(0).startDate().value()),
                () -> assertEquals(infinite, history1.list().get(0).endDate().value()),
                () -> assertEquals(800, history1.list().get(0).hourlyWage().value().intValue())
        );

        //2発目
        Date applyDate2 = new Date(now.plusDays(10));
        HourlyWage wage2 = new HourlyWage(850);
        updateHourlyWageContract(number, applyDate2, wage2);
        ContractHistory history2 = sutQuery.getContractHistory(number);
        assertEquals(2, history2.list().size());
        assertAll(
                () -> assertEquals(applyDate2.value(), history2.list().get(0).startDate().value()),
                () -> assertEquals(infinite, history2.list().get(0).endDate().value()),
                () -> assertEquals(850, history2.list().get(0).hourlyWage().value().intValue()),
                () -> assertEquals(now, history2.list().get(1).startDate().value()),
                () -> assertEquals(applyDate2.value().minusDays(1), history2.list().get(1).endDate().value()),
                () -> assertEquals(800, history2.list().get(1).hourlyWage().value().intValue())
        );

        //3発目（過去）
        Date applyDate3 = new Date(now.plusDays(5));
        HourlyWage wage3 = new HourlyWage(830);
        updateHourlyWageContract(number, applyDate3, wage3);
        ContractHistory history3 = sutQuery.getContractHistory(number);
        assertEquals(2, history3.list().size());
        assertAll(
                () -> assertEquals(applyDate3.value(), history3.list().get(0).startDate().value()),
                () -> assertEquals(infinite, history3.list().get(0).endDate().value()),
                () -> assertEquals(830, history3.list().get(0).hourlyWage().value().intValue()),
                () -> assertEquals(now, history3.list().get(1).startDate().value()),
                () -> assertEquals(applyDate3.value().minusDays(1), history3.list().get(1).endDate().value()),
                () -> assertEquals(800, history3.list().get(1).hourlyWage().value().intValue())
        );
    }

    private void updateHourlyWageContract(WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage) {
        sutRecord.registerHourlyWage(workerNumber, applyDate, new WageCondition(hourlyWage, new OverTimeExtraRate(25), new MidnightExtraRate(35)));
    }
}
