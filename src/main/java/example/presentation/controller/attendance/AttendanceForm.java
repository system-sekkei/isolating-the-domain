package example.presentation.controller.attendance;

import example.domain.model.attendance.Break;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

import java.time.LocalDate;

public class AttendanceForm {
    WorkerNumber workerNumber;
    Date date;
    Integer startHour;
    Integer startMinute;
    Integer endHour;
    Integer endMinute;
    Break breaks;

    public AttendanceForm() {
        this.date = new Date(LocalDate.now());
        this.startHour = 9;
        this.startMinute = 0;
        this.endHour = 17;
        this.endMinute = 30;
        this.breaks = new Break("60");
    }
}
