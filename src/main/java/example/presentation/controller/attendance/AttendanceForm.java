package example.presentation.controller.attendance;

import example.domain.model.attendance.MidnightBreakTime;
import example.domain.model.attendance.NormalBreakTime;
import example.domain.model.attendance.WorkDay;
import example.domain.model.worker.WorkerNumber;

public class AttendanceForm {
    WorkerNumber workerNumber;
    WorkDay workDay;
    Integer startHour;
    Integer startMinute;
    Integer endHour;
    Integer endMinute;
    NormalBreakTime normalBreakTime;
    MidnightBreakTime midnightBreakTime;

    public AttendanceForm() {
        this.workDay = new WorkDay();
        this.startHour = 9;
        this.startMinute = 0;
        this.endHour = 17;
        this.endMinute = 30;
        this.normalBreakTime = new NormalBreakTime("60");
        this.midnightBreakTime = new MidnightBreakTime("0");
    }

}
