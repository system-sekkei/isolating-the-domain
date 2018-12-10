package example.presentation.controller.attendance;

import example.domain.model.attendance.MidnightBreakTime;
import example.domain.model.attendance.NormalBreakTime;
import example.domain.model.attendance.WorkDay;
import example.domain.model.worker.WorkerNumber;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AttendanceForm {
    WorkerNumber workerNumber;
    WorkDay date;
    Integer startHour;
    Integer startMinute;
    Integer endHour;
    Integer endMinute;
    NormalBreakTime normalBreakTime;
    MidnightBreakTime midnightBreakTime;

    public AttendanceForm() {
        this.date = new WorkDay(LocalDate.now());
        this.startHour = 9;
        this.startMinute = 0;
        this.endHour = 17;
        this.endMinute = 30;
        this.normalBreakTime = new NormalBreakTime("60");
        this.midnightBreakTime = new MidnightBreakTime("0");
    }



}
