package example.domain.model.attendance;

import example.domain.type.date.DayOfWeek;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 勤務日
 */
public class WorkDay {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate value;

    public WorkDay() {
        this(LocalDate.now());
    }

    public WorkDay(LocalDate value){
        this.value = value;
    }

    public LocalDate value(){
        return value;
    }

    public int dayOfMonth() {
        return value.getDayOfMonth();
    }

    public DayOfWeek dayOfWeek() {
        return DayOfWeek.of(value.getDayOfWeek());
    }
    
    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ISO_DATE);
    }
}
