package example.application.service;

import example.Application;
import example.application.service.attendance.AttendanceQueryService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.attendance.Attendances;
import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
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
public class AttendanceQueryServiceTest {

    @Autowired
    AttendanceQueryService attendanceQueryService;

    @Autowired
    WorkerQueryService workerQueryService;

    @Test
    void getAttendances() {

        Date startDate = new Date(LocalDate.of(2099,10,1));
        Date endDate = new Date(LocalDate.of(2099,10,31));

        ContractingWorkers workers = workerQueryService.contractingWorkers();
        Worker worker = workers.list().get(0);
        WorkerNumber workerNumber = worker.workerNumber();
        Attendances attendances = attendanceQueryService.getAttendances(workerNumber, startDate, endDate);

        assertAll(
                () -> assertEquals(attendances.list().size(),31),
                () -> assertEquals(attendances.workTime().toString(),"00:00")
        );
    }


}
