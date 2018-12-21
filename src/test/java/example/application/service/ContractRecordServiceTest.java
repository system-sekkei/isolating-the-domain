package example.application.service;

import example.Application;
import example.application.service.contract.ContractQueryService;
import example.application.service.contract.ContractRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.contract.Contracts;
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
    WorkerQueryService workerQueryService;
    @Autowired
    ContractRecordService sutRecord;
    @Autowired
    ContractQueryService sutQuery;

    @DisplayName("時給の登録参照が正しく行われていること")
    @Test
    void hourlyWage_io() {
        WorkerNumber workerNumber = workerQueryService.contractingWorkers().list().get(0).workerNumber();

        Date applyDate1 = new Date(LocalDate.of(2099, 11, 12));
        HourlyWage wage1 = new HourlyWage(800);
        sutRecord.registerHourlyWage(workerNumber, applyDate1, wage1);

        HourlyWage wage3 = new HourlyWage(820);
        Date applyDate3 = new Date(LocalDate.of(2099, 11, 15));
        sutRecord.registerHourlyWage(workerNumber, applyDate3, wage3);

        Contracts contracts = sutQuery.getContracts(workerNumber, new Date(LocalDate.of(2099, 11, 12)), new Date(LocalDate.of(2099, 11, 30)));
        assertAll(
                () -> assertEquals(2, contracts.value().size()),
                () -> assertEquals(800, contracts.value().get(0).hourlyWage().value().intValue()),
                () -> assertEquals(LocalDate.of(2099, 11, 12), contracts.value().get(0).startDate().value()),
                () -> assertEquals(LocalDate.of(2099, 11, 14), contracts.value().get(0).endDate().value()),
                () -> assertEquals(820, contracts.value().get(1).hourlyWage().value().intValue()),
                () -> assertEquals(LocalDate.of(2099, 11, 15), contracts.value().get(1).startDate().value()),
                () -> assertEquals(LocalDate.of(2099, 11, 30), contracts.value().get(1).endDate().value())
        );
    }
}
