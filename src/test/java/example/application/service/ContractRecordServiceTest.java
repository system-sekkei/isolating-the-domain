package example.application.service;

import example.Application;
import example.application.service.contract.ContractQueryService;
import example.application.service.contract.ContractRecordService;
import example.application.service.worker.WorkerRecordService;
import example.domain.model.contract.Contract;
import example.domain.model.contract.HourlyWage;
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
        //一発目
        WorkerNumber number = workerRecordService.prepareNewContract();
        LocalDate now = LocalDate.now();
        Date applyDate1 = new Date(now);
        HourlyWage wage1 = new HourlyWage(800);
        updateHourlyWageContract(number, applyDate1, wage1);
        Contract contract = sutQuery.getContract(number, applyDate1);
        assertAll(
                () -> assertEquals(now, contract.startDate().value()),
                () -> assertEquals(LocalDate.of(9999, 12, 31), contract.endDate().value()),
                () -> assertEquals(800, contract.hourlyWage().value().intValue()));

        //2発目
        Date applyDate2 = new Date(now.plusDays(10));
        HourlyWage wage2 = new HourlyWage(850);
        updateHourlyWageContract(number, applyDate2, wage2);
        Contract contract2 = sutQuery.getContract(number, applyDate1);
        Contract contract3 = sutQuery.getContract(number, applyDate2);
        assertAll(
                () -> assertEquals(now, contract2.startDate().value()),
                () -> assertEquals(applyDate2.value().minusDays(1), contract2.endDate().value()),
                () -> assertEquals(800, contract2.hourlyWage().value().intValue()),
                () -> assertEquals(applyDate2.value(), contract3.startDate().value()),
                () -> assertEquals(LocalDate.of(9999, 12, 31), contract3.endDate().value()),
                () -> assertEquals(850, contract3.hourlyWage().value().intValue())
        );
        //3発目（過去）
        Date applyDate3 = new Date(now.plusDays(5));
        HourlyWage wage3 = new HourlyWage(830);
        updateHourlyWageContract(number, applyDate3, wage3);
        Contract contract4 = sutQuery.getContract(number, applyDate1);
        Contract contract5 = sutQuery.getContract(number, applyDate2);
        Contract contract6 = sutQuery.getContract(number, applyDate3);
        assertAll(
                () -> assertEquals(now, contract4.startDate().value()),
                () -> assertEquals(applyDate3.value().minusDays(1), contract4.endDate().value()),
                () -> assertEquals(800, contract4.hourlyWage().value().intValue()),
                () -> assertEquals(applyDate3.value(), contract5.startDate().value()),
                () -> assertEquals(LocalDate.of(9999, 12, 31), contract5.endDate().value()),
                () -> assertEquals(830, contract5.hourlyWage().value().intValue()),
                () -> assertEquals(applyDate3.value(), contract6.startDate().value()),
                () -> assertEquals(LocalDate.of(9999, 12, 31), contract6.endDate().value()),
                () -> assertEquals(830, contract6.hourlyWage().value().intValue())
        );
    }

    private void updateHourlyWageContract(WorkerNumber workerNumber, Date applyDate, HourlyWage hourlyWage) {
        sutRecord.stopHourlyWageContract(workerNumber, new Date(applyDate.value().minusDays(1)));
        sutRecord.registerHourlyWage(workerNumber, applyDate, hourlyWage);
    }}
